package com.haarer.saf.rules;

import com.nomagic.magicdraw.core.Project;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.NamedElement;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;

public final class RuleSupport {

    private RuleSupport() {
    }

    public static boolean hasAppliedStereotype(Element element, String stereotypeName) {
        for (Stereotype s : element.getAppliedStereotype()) {
            if (stereotypeName.equals(s.getName())) {
                return true;
            }
        }
        return false;
    }

    public static Stereotype findStereotype(Project project, String name) {
        for (Stereotype s : StereotypesHelper.getAllStereotypes(project)) {
            if (name.equals(s.getName())) {
                return s;
            }
        }
        return null;
    }

    public static String describe(Element element) {
        if (element == null) {
            return "null";
        }
        if (element instanceof NamedElement named) {
            return named.getClass().getSimpleName() + " '" + named.getName() + "'";
        }
        return element.getClass().getSimpleName();
    }

    public static void log(String msg) {
        SafRulesPlugin.log(msg);
    }

    public static void logError(String msg) {
        SafRulesPlugin.logError(msg);
    }
}
