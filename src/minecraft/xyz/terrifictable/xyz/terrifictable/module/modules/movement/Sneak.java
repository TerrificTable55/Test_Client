package xyz.terrifictable.module.modules.movement;

import xyz.terrifictable.module.Module;

public class Sneak extends Module {
    public Sneak() {
        super("Sneak", 0, Category.MOVEMENT);
    }

    @Override
    public void onDisable() {
        mc.gameSettings.keyBindSneak.pressed = false;
        super.onDisable();
    }

    @Override
    public void onUpdate() {
        if (!this.isToggled()) return;

        mc.gameSettings.keyBindSneak.pressed = true;
    }
}
