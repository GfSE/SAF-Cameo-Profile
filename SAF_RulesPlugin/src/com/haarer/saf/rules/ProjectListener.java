package com.haarer.saf.rules;

import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.core.project.ProjectEventListenerAdapter;
import com.nomagic.uml2.transaction.TransactionManager;

import java.util.IdentityHashMap;
import java.util.Map;

public class ProjectListener extends ProjectEventListenerAdapter {

    private final Map<Project, ModelRulesListener> listeners = new IdentityHashMap<>();

    @Override
    public void projectOpened(Project project) {
        attach(project);
    }

    @Override
    public void projectActivated(Project project) {
        attach(project);
    }

    @Override
    public void projectPreClosed(Project project) {
        detach(project);
    }

    @Override
    public void projectClosed(Project project) {
        detach(project);
    }

    @Override
    public void projectDeActivated(Project project) {
        detach(project);
    }

    private void attach(Project project) {
        if (project == null || listeners.containsKey(project)) {
            return;
        }
        TransactionManager tm = project.getRepository().getTransactionManager();
        ModelRulesListener listener = new ModelRulesListener(project);
        tm.addTransactionCommitListenerIncludingUndoAndRedo(listener);
        listeners.put(project, listener);
        SafRulesPlugin.log("SAF Model Rules: attached to project '" + project.getName() + "'");
    }

    private void detach(Project project) {
        if (project == null) {
            return;
        }
        ModelRulesListener listener = listeners.remove(project);
        if (listener == null) {
            return;
        }
        try {
            TransactionManager tm = project.getRepository().getTransactionManager();
            tm.removeTransactionCommitListener(listener);
        } catch (Exception e) {
            SafRulesPlugin.logError("Failed to detach from project '" + project.getName() + "': " + e.getMessage());
        }
        SafRulesPlugin.log("SAF Model Rules: detached from project '" + project.getName() + "'");
    }

    public void detachAll() {
        for (Map.Entry<Project, ModelRulesListener> e : listeners.entrySet()) {
            try {
                TransactionManager tm = e.getKey().getRepository().getTransactionManager();
                tm.removeTransactionCommitListener(e.getValue());
            } catch (Exception ignored) {}
        }
        listeners.clear();
    }
}
