package com.nomagic.magicdraw.core;

import com.nomagic.magicdraw.core.project.ProjectsManager;

public class Application {
    private static final Application INSTANCE = new Application();
    public static Application getInstance() { return INSTANCE; }
    public GUILog getGUILog() { return new GUILog(); }
    public Project getProject() { return null; }
    public ProjectsManager getProjectsManager() { return null; }
}
