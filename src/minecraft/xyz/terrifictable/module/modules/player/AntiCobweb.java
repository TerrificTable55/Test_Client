package xyz.terrifictable.module.modules.player;

import xyz.terrifictable.module.Module;

public class AntiCobweb extends Module {
    public AntiCobweb() {
        super("AntiCobweb", 0, Category.PLAYER);
    }

    @Override
    public void onDisable() {
        mc.thePlayer.isInWeb = true;
    }

    @Override
    public void onUpdate() {
        if (!this.isToggled()) return;

        mc.thePlayer.isInWeb = false;
    }
}
