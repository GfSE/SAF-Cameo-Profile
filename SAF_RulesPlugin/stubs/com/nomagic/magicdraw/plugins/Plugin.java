package com.nomagic.magicdraw.plugins;

public abstract class Plugin {
    public void init() {}
    public boolean close() { return true; }
    public boolean isSupported() { return true; }
}
