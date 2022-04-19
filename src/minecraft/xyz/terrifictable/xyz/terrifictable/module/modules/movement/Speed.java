package xyz.terrifictable.module.modules.movement;

import xyz.terrifictable.module.Module;
import xyz.terrifictable.setting.settings.NumberSetting;

public class Speed extends Module {
    public NumberSetting speed = new NumberSetting("Speed", 2, 1, 10, 1);

    public Speed() {
        super("Speed", 0, Category.MOVEMENT);
        addSettings(speed);
    }

    @Override
    public void onUpdate() {
        if (!this.isToggled()) return;

        if (mc.thePlayer.onGround) {
            mc.thePlayer.motionX *= (float) speed.getValue();
            mc.thePlayer.motionZ *= (float) speed.getValue();
        }
    }
}
