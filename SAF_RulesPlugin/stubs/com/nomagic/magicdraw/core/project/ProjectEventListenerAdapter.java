package com.nomagic.magicdraw.core.project;

import com.nomagic.magicdraw.core.Project;

public abstract class ProjectEventListenerAdapter implements ProjectEventListener {
    public void projectOpened(Project project) {}
    public void projectActivated(Project project) {}
    public void projectPreClosed(Project project) {}
    public void projectClosed(Project project) {}
    public void projectDeActivated(Project project) {}
}
