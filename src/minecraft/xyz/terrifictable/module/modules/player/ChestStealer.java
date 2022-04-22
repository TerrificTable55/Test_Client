package xyz.terrifictable.module.modules.player;

import xyz.terrifictable.module.Module;
import xyz.terrifictable.setting.settings.ModeSetting;

public class ChestStealer extends Module {
    public ModeSetting mode = new ModeSetting("Mode", "Side", "Top", "Mid", "Side");

    public ChestStealer() {
        super("ChestStealer", 0, Category.PLAYER);
        addSettings(mode);
        this.toggled = true;
    }
}
