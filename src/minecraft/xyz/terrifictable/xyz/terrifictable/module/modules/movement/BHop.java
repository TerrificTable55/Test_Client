package xyz.terrifictable.module.modules.movement;

import org.lwjgl.input.Keyboard;
import xyz.terrifictable.module.Module;
import xyz.terrifictable.setting.settings.NumberSetting;

public class BHop extends Module {
    public NumberSetting XYspeed = new NumberSetting("Speed", 0.1, 0.1, 1, 0.1);

    public BHop() {
        super("BHop", Keyboard.KEY_B, Category.MOVEMENT);
        addSettings(XYspeed);
    }

    public void onDisable() {
        mc.gameSettings.keyBindJump.pressed = false;
        super.onDisable();
    }

    public void onUpdate() {
        if (!this.isToggled()) return;

        if (!mc.thePlayer.capabilities.isFlying) {
            if (mc.gameSettings.keyBindForward.isPressed()) {
                mc.gameSettings.keyBindJump.pressed = true;

                if (mc.thePlayer.motionX > 0) {
                    mc.thePlayer.motionX += XYspeed.getValue();
                }
                else if (mc.thePlayer.motionX < 0) {
                    mc.thePlayer.motionX -= XYspeed.getValue();
                }
                else if (mc.thePlayer.motionZ > 0) {
                    mc.thePlayer.motionZ += XYspeed.getValue();
                }
                else if (mc.thePlayer.motionZ < 0) {
                    mc.thePlayer.motionZ -= XYspeed.getValue();
                }
            }
        }
    }
}
