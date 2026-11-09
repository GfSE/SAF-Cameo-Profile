package com.haarer.saf.rules;

import com.nomagic.magicdraw.core.Project;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import groovy.lang.GroovyClassLoader;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class RuleScanner {

    private final File rulesDir;
    private final long pollMs;
    private final GroovyClassLoader groovyClassLoader = new GroovyClassLoader(getClass().getClassLoader());
    private final Map<String, Long> fileCache = new ConcurrentHashMap<>();
    private final Map<String, CompiledRule> lastGood = new ConcurrentHashMap<>();

    private final AtomicBoolean running = new AtomicBoolean(false);
    private volatile Thread thread;

    public RuleScanner(File rulesDir, long pollMs) {
        this.rulesDir = rulesDir;
        this.pollMs = pollMs;
    }

    public void start() {
        if (!running.compareAndSet(false, true)) {
            return;
        }
        try {
            scan();
        } catch (Exception e) {
            SafRulesPlugin.logError("Failed to load rules: " + e.getMessage());
        }
        thread = new Thread(this::loop, "saf-rules-scanner");
        thread.setDaemon(true);
        thread.start();
    }

    public void stop() {
        running.set(false);
        Thread t = thread;
        if (t != null && t.isAlive()) {
            t.interrupt();
            try {
                t.join(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void loop() {
        while (running.get()) {
            try {
                Thread.sleep(pollMs);
                if (hasChanges()) {
                    scan();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                SafRulesPlugin.logError("Rules hot-reload error: " + e.getMessage());
            }
        }
    }

    private boolean hasChanges() {
        Map<String, Long> current = currentFiles();
        if (!current.keySet().equals(fileCache.keySet())) {
            return true;
        }
        for (Map.Entry<String, Long> e : current.entrySet()) {
            Long cached = fileCache.get(e.getKey());
            if (cached == null || !cached.equals(e.getValue())) {
                return true;
            }
        }
        return false;
    }

    private Map<String, Long> currentFiles() {
        Map<String, Long> current = new HashMap<>();
        File[] files = rulesDir.listFiles((d, n) -> n.endsWith(".groovy"));
        if (files != null) {
            for (File f : files) {
                current.put(f.getAbsolutePath(), f.lastModified());
            }
        }
        return current;
    }

    private synchronized void scan() {
        Map<String, Long> newCache = currentFiles();
        List<CompiledRule> rules = new ArrayList<>();
        for (String path : newCache.keySet().stream().sorted().toList()) {
            File file = new File(path);
            try {
                CompiledRule rule = compile(file);
                rules.add(rule);
                lastGood.put(path, rule);
            } catch (Exception e) {
                CompiledRule prev = lastGood.get(path);
                if (prev != null) {
                    rules.add(prev);
                }
                SafRulesPlugin.logError("Failed to load rule '" + file.getName() + "': " + e.getMessage()
                        + (prev != null ? " (keeping previous version)" : ""));
            }
        }
        lastGood.keySet().removeIf(p -> !newCache.containsKey(p));
        fileCache.clear();
        fileCache.putAll(newCache);

        rules.sort(Comparator.comparingInt(CompiledRule::order));
        RuleRegistry.set(rules);
        SafRulesPlugin.log("SAF Model Rules: " + rules.size() + " rule(s) active"
                + (rules.isEmpty() ? "" : ": " + rules.stream().map(CompiledRule::name).collect(Collectors.joining(" | "))));
    }

    private CompiledRule compile(File file) throws Exception {
        Class<?> clazz = groovyClassLoader.parseClass(file);
        RuleDefinition definition = clazz.getAnnotation(RuleDefinition.class);
        if (definition == null) {
            throw new IllegalArgumentException("no @RuleDefinition on " + clazz.getName());
        }
        Method action = null;
        for (Method m : clazz.getMethods()) {
            RuleTrigger trigger = m.getAnnotation(RuleTrigger.class);
            if (trigger == null) {
                continue;
            }
            if (action != null) {
                throw new IllegalArgumentException("multiple @RuleTrigger methods in " + clazz.getName());
            }
            action = m;
        }
        if (action == null) {
            throw new IllegalArgumentException("no @RuleTrigger method in " + clazz.getName());
        }
        Class<?>[] params = action.getParameterTypes();
        if (params.length != 2 || !params[0].isAssignableFrom(Project.class)
                || !params[1].isAssignableFrom(Element.class)) {
            throw new IllegalArgumentException("action must be 'void apply(Project, Element)' in " + clazz.getName());
        }
        Object instance = clazz.getDeclaredConstructor().newInstance();
        return new CompiledRule(definition, action.getAnnotation(RuleTrigger.class), instance, action);
    }
}
