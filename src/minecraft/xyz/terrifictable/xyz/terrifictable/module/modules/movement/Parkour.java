package xyz.terrifictable.module.modules.movement;

import net.minecraft.entity.Entity;
import xyz.terrifictable.module.Module;

public class Parkour extends Module {
    public Parkour() {
        super("Parkour", 0, Category.MOVEMENT);
    }

    @Override
    public void onUpdate() {
        if (!this.isToggled()) return;

        if (mc.thePlayer.onGround && !mc.thePlayer.isSneaking() && !this.mc.gameSettings.keyBindSneak.pressed && this.mc.theWorld.getCollidingBoundingBoxes((Entity) mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0d, -0.5d, 0.0d).expand(-0.001d, 0.0d, -0.001d)).isEmpty()) {
            mc.thePlayer.jump();
        }
    }
}
