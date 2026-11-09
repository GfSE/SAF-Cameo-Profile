package com.haarer.saf.rules;

import com.nomagic.magicdraw.plugins.Plugin;
import com.nomagic.magicdraw.core.Application;

import java.io.File;
import java.util.logging.Logger;

public class SafRulesPlugin extends Plugin {

    private static final Logger LOG = Logger.getLogger(SafRulesPlugin.class.getName());

    private ProjectListener projectListener;
    private RuleScanner ruleScanner;

    static void log(String msg) {
        System.err.println("[SafRules] " + msg);
        LOG.info(msg);
        try {
            Application.getInstance().getGUILog().log(msg);
        } catch (Throwable ignored) {}
    }

    static void logError(String msg) {
        System.err.println("[SafRules] ERROR: " + msg);
        LOG.severe(msg);
        try {
            Application.getInstance().getGUILog().showError(msg);
        } catch (Throwable ignored) {}
    }

    static void debug(String msg) {
        System.err.println("[SafRules] DEBUG: " + msg);
        LOG.fine(msg);
    }

    @Override
    public void init() {
        try {
            log("SAF Model Rules: Starting ...");
            File rulesDir = determineRulesDir();
            long pollMs = Long.getLong("cameo.rules.pollMs", 2000L);
            ruleScanner = new RuleScanner(rulesDir, pollMs);
            ruleScanner.start();
            projectListener = new ProjectListener();
            Application.getInstance().getProjectsManager().addProjectListener(projectListener);
            log("SAF Model Rules: Started (rules dir: " + rulesDir.getAbsolutePath() + ")");
        } catch (Exception e) {
            logError("Failed to start: " + e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    @Override
    public boolean close() {
        try {
            if (ruleScanner != null) {
                ruleScanner.stop();
                ruleScanner = null;
            }
            if (projectListener != null) {
                projectListener.detachAll();
                Application.getInstance().getProjectsManager().removeProjectListener(projectListener);
                projectListener = null;
            }
        } catch (Exception e) {
            LOG.severe("Failed to stop cleanly: " + e.getMessage());
        }
        LOG.info("SAF Model Rules: Stopped");
        return true;
    }

    @Override
    public boolean isSupported() {
        return true;
    }

    private static File determineRulesDir() {
        String propertyDir = System.getProperty("cameo.rules.dir");
        if (propertyDir != null) {
            return new File(propertyDir);
        }
        try {
            String jarPath = SafRulesPlugin.class.getProtectionDomain()
                    .getCodeSource().getLocation().getPath();
            int lastSlash = jarPath.lastIndexOf(File.separatorChar);
            if (lastSlash != -1) {
                return new File(jarPath.substring(0, lastSlash) + File.separator + "rules");
            }
        } catch (Exception ignored) {}
        return new File("rules");
    }
}
