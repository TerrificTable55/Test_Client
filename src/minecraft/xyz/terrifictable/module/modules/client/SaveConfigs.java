package xyz.terrifictable.module.modules.client;

import xyz.terrifictable.Client;
import xyz.terrifictable.module.Module;

public class SaveConfigs extends Module {

    public SaveConfigs() {
        super("save", 0, Category.CLIENT);
        this.toggled = true;
    }

    public void onUpdate() {
        Client.configManager.saveConfig();
    }
}
