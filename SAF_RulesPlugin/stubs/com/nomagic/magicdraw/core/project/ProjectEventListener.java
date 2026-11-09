package com.nomagic.magicdraw.core.project;

import com.nomagic.magicdraw.core.Project;

public interface ProjectEventListener {
    void projectOpened(Project project);
    void projectActivated(Project project);
    void projectPreClosed(Project project);
    void projectClosed(Project project);
    void projectDeActivated(Project project);
}
