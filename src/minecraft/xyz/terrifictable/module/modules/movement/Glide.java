package xyz.terrifictable.module.modules.movement;

import net.minecraft.block.material.Material;
import xyz.terrifictable.module.Module;

public class Glide extends Module {
    public Glide() {
        super("Glide", 0, Category.MOVEMENT);
    }

    @Override
    public void onUpdate() {
        if (!this.isToggled()) return;

        double oldY = mc.thePlayer.motionY;
        float oldJ = mc.thePlayer.jumpMovementFactor;

        if (mc.thePlayer.motionY < 0.0d && mc.thePlayer.isAirBorne && !mc.thePlayer.isInWater() && !mc.thePlayer.isOnLadder()) {
            if (!mc.thePlayer.isInsideOfMaterial(Material.lava)) {
                mc.thePlayer.motionY = -.125d;
                mc.thePlayer.jumpMovementFactor *= 1.12337f;
            }
        } else {
            mc.thePlayer.motionY = oldY;
            mc.thePlayer.jumpMovementFactor = oldJ;
        }
    }
}
