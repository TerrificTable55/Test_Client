package xyz.terrifictable.module.modules.movement;

import xyz.terrifictable.module.Module;

public class AutoWalk extends Module {
    public AutoWalk() {
        super("AutoWalk", 0, Category.MOVEMENT);
    }

    public void onDisable() {
        mc.gameSettings.keyBindForward.pressed = false;
    }

    public void onUpdate() {
        if (!this.isToggled()) return;

        mc.gameSettings.keyBindForward.pressed = true;
    }
}
