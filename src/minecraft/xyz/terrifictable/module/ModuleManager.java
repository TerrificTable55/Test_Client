package xyz.terrifictable.module;

import xyz.terrifictable.Client;

public class ModuleManager {
    public static float ticks;

    public void onRender(float ticks) {
        this.ticks = ticks;
        for (Module module : Client.modules) {
            module.onRender();
        }
    }

    public void onUpdate() {
        for (Module module : Client.modules) {
            module.onUpdate();
        }
    }


    public static float getTicks() {
        return ticks;
    }
    public static void setTicks(float ticks) {
        ModuleManager.ticks = ticks;
    }
}
