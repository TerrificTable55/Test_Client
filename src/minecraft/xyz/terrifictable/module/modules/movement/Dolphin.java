package xyz.terrifictable.module.modules.movement;

import xyz.terrifictable.module.Module;
import xyz.terrifictable.setting.settings.NumberSetting;

public class Dolphin extends Module {
    public NumberSetting speed = new NumberSetting("Speed", 0.4, 0, 1, 0.1);

    public Dolphin() {
        super("Dolphin", 0, Category.MOVEMENT);
        addSettings(speed);
    }

    @Override
    public void onUpdate() {
        if (!this.isToggled()) return;

        if (mc.thePlayer.isInWater()) {
            mc.thePlayer.motionY += speed.getValue() / 10f;
        }
    }
}
