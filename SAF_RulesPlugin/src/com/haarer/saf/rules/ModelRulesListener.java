package com.haarer.saf.rules;

import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.uml.DiagramType;
import com.nomagic.uml2.ext.jmi.UML2MetamodelConstants;
import com.nomagic.uml2.ext.magicdraw.activities.mdfundamentalactivities.Activity;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Diagram;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.NamedElement;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;
import com.nomagic.uml2.transaction.TransactionCommitListener;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ModelRulesListener implements TransactionCommitListener {

    private static final boolean DEBUG = Boolean.getBoolean("cameo.rules.debug");

    private final Project project;
    private final AtomicBoolean applying = new AtomicBoolean(false);

    public ModelRulesListener(Project project) {
        this.project = project;
    }

    @Override
    public Runnable transactionCommited(Collection<PropertyChangeEvent> events) {
        List<Element> created = new ArrayList<>();
        for (PropertyChangeEvent e : events) {
            if (UML2MetamodelConstants.INSTANCE_CREATED.equals(e.getPropertyName())) {
                Object source = e.getSource();
                if (source instanceof Element el) {
                    created.add(el);
                    logCreated(el);
                } else if (DEBUG) {
                    SafRulesPlugin.debug("created (non-Element): " + source);
                }
            }
        }
        if (created.isEmpty()) {
            return () -> {};
        }
        return () -> applyRules(created);
    }

    private void applyRules(List<Element> created) {
        if (applying.get()) {
            return;
        }
        if (project.isProjectClosed()) {
            return;
        }
        applying.set(true);
        try {
            for (CompiledRule rule : RuleRegistry.rules()) {
                for (Element element : created) {
                    if (!rule.matches(element)) {
                        continue;
                    }
                    try {
                        rule.apply(project, element);
                    } catch (Exception e) {
                        SafRulesPlugin.logError("Rule '" + rule.name() + "' failed: " + e.getMessage());
                        e.printStackTrace(System.err);
                    }
                }
            }
        } finally {
            applying.set(false);
        }
    }

    private static void logCreated(Element el) {
        String name = el instanceof NamedElement ne ? ne.getName() : "(unnamed)";
        String desc = "created: " + el.getClass().getSimpleName()
                + " name='" + name + "' id=" + el.getID()
                + (el instanceof Diagram d
                    ? " type=" + DiagramType.getDiagramType(d).getType()
                        + " context=" + describe(d.getContext())
                        + " stereotypes=" + describeStereotypes(d)
                    : "");
        if (el instanceof Diagram || el instanceof Activity) {
            SafRulesPlugin.log(desc);
        } else if (DEBUG) {
            SafRulesPlugin.debug(desc);
        }
    }

    @Override
    public void allTransactionsCommitted() {
    }

    private static String describeStereotypes(Element el) {
        StringBuilder sb = new StringBuilder("[");
        for (Stereotype s : el.getAppliedStereotype()) {
            if (sb.length() > 1) {
                sb.append(", ");
            }
            sb.append(s.getName());
        }
        return sb.append("]").toString();
    }

    private static String describe(Object o) {
        if (o == null) {
            return "null";
        }
        if (o instanceof NamedElement ne) {
            return ne.getClass().getSimpleName() + " '" + ne.getName() + "'";
        }
        String s = o.toString();
        return s.length() > 120 ? s.substring(0, 120) + "..." : s;
    }
}
