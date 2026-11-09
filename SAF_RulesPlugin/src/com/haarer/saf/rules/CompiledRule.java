package com.haarer.saf.rules;

import com.nomagic.magicdraw.core.Project;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class CompiledRule {

    private final RuleDefinition definition;
    private final RuleTrigger trigger;
    private final Object instance;
    private final Method action;

    public CompiledRule(RuleDefinition definition, RuleTrigger trigger, Object instance, Method action) {
        this.definition = definition;
        this.trigger = trigger;
        this.instance = instance;
        this.action = action;
    }

    public String name() {
        return definition.name();
    }

    public int order() {
        return definition.order();
    }

    public boolean matches(Element element) {
        if (!trigger.type().isInstance(element)) {
            return false;
        }
        for (String stereotype : trigger.stereotypes()) {
            if (!RuleSupport.hasAppliedStereotype(element, stereotype)) {
                return false;
            }
        }
        for (String stereotype : trigger.skipIfHas()) {
            if (RuleSupport.hasAppliedStereotype(element, stereotype)) {
                return false;
            }
        }
        return true;
    }

    public void apply(Project project, Element element) throws Exception {
        try {
            action.invoke(instance, project, element);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof Exception ex) {
                throw ex;
            }
            throw new RuntimeException("Rule action threw: " + cause, cause);
        }
    }
}
