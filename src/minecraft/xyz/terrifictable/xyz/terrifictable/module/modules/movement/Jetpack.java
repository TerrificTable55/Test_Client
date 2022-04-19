package xyz.terrifictable.module.modules.movement;

import xyz.terrifictable.module.Module;

public class Jetpack extends Module {
    public Jetpack() {
        super("Jetpack", 0, Category.MOVEMENT);
    }

    @Override
    public void onUpdate() {
        if (!this.isToggled()) return;

        if (mc.gameSettings.keyBindJump.pressed) {
            mc.thePlayer.jump();
        }
    }
}
