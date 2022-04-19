package xyz.terrifictable.module.modules.player;

import xyz.terrifictable.module.Module;
import xyz.terrifictable.util.Timer;

public class AntiAfk extends Module {
    private int tickCount = 1;
    private int afkCount = 1;
    Timer timer = new Timer();

    public AntiAfk() {
        super("AntiAfk", 0, Category.PLAYER);
    }

    @Override
    public void onDisable() {
        mc.gameSettings.keyBindForward.pressed = false;
    }

    @Override
    public void onUpdate() {
        if (!this.isToggled()) return;

        if (timer.hasTimeElapsed(5000, false)) {
            mc.gameSettings.keyBindForward.pressed = true;
            mc.thePlayer.rotationYaw = mc.thePlayer.prevRotationYaw -= 90;
            timer.reset();
            mc.gameSettings.keyBindForward.pressed = false;
        }
    }
}
