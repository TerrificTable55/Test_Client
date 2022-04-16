package xyz.terrifictable.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class RotationUtil {
    private static Minecraft mc = Minecraft.getMinecraft();

    public static float[] getRotations(Entity entity) {
        double deltaX = entity.posX + (entity.posX - entity.lastTickPosX) - mc.thePlayer.posX;
        double deltaY = entity.posY - 3.5 + entity.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight();
        double deltaZ = entity.posZ + (entity.posZ - entity.lastTickPosZ) - mc.thePlayer.posZ;
        double distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));
        float yaw = (float) Math.toDegrees(-Math.atan(deltaX / deltaZ));
        float pitch = (float) -Math.toDegrees(Math.atan(deltaY / distance));

        if (deltaX < 0 && deltaZ < 0) {
            yaw = (float) (90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        } else if (deltaX > 0 && deltaZ < 0) {
            yaw = (float) (-90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        }

        return new float[] {yaw, pitch};
    }
}
