package com.haarer.saf.rules;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class RuleRegistry {

    private static final AtomicReference<List<CompiledRule>> RULES = new AtomicReference<>(List.of());

    private RuleRegistry() {
    }

    public static List<CompiledRule> rules() {
        return RULES.get();
    }

    public static void set(List<CompiledRule> rules) {
        RULES.set(List.copyOf(rules));
    }
}
