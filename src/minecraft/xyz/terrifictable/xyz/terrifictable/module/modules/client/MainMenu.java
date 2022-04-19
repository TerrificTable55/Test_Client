package xyz.terrifictable.module.modules.client;

import xyz.terrifictable.module.Module;
import xyz.terrifictable.setting.settings.ModeSetting;

public class MainMenu extends Module {
    public ModeSetting image = new ModeSetting("Image", "Galaxy", "Galaxy", "Mountain", "Snowy Mountain");

    public MainMenu() {
        super("MainMenu", 0, Category.CLIENT);
        this.toggled = true;
    }
}
