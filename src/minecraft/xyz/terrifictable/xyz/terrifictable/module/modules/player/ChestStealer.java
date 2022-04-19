package xyz.terrifictable.module.modules.player;

import xyz.terrifictable.module.Module;

public class ChestStealer extends Module {
    public ChestStealer() {
        super("ChestStealer", 0, Category.PLAYER);
        this.toggled = true;
    }
}
