package xyz.terrifictable.module.modules.movement;

import xyz.terrifictable.module.Module;
import xyz.terrifictable.setting.settings.NumberSetting;
import xyz.terrifictable.util.Invoker;

public class Spider extends Module {
    public NumberSetting speed = new NumberSetting("Speed", 0.2, 0.1, 1, 0.1);

    public Spider() {
        super("Spider", 0, Category.MOVEMENT);
    }

    @Override
    public void onUpdate() {
        if (!this.isToggled()) return;

        if (Invoker.isCollidedHorizontally()) {
            Invoker.setMotionY(speed.getValue());

            float var6 = 0.15f;

            if (Invoker.getMotionX() < (double) - var6) {
                Invoker.setMotionX((double) - var6);
            }
            if (Invoker.getMotionX() > (double) - var6) {
                Invoker.setMotionX((double) - var6);
            }
            if (Invoker.getMotionZ() < (double) - var6) {
                Invoker.setMotionZ((double) - var6);
            }
            if (Invoker.getMotionZ() > (double) - var6) {
                Invoker.setMotionZ((double) - var6);
            }

            Invoker.setFallDistance(0);

            if (Invoker.getMotionY() < 0.15d) {
                Invoker.setMotionY(-0.15d);
            }
        }
    }
}
