package xyz.terrifictable.module;

import xyz.terrifictable.Client;

public class ModuleManager {

    public void onUpdate() {
        for (Module module : Client.modules) {
            module.onUpdate();
        }
    }
}
