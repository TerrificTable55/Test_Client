package xyz.terrifictable.module.modules.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;
import xyz.terrifictable.Client;
import xyz.terrifictable.events.Event;
import xyz.terrifictable.events.listeners.EventMotion;
import xyz.terrifictable.module.Module;
import xyz.terrifictable.module.ModuleManager;
import xyz.terrifictable.setting.settings.BooleanSetting;
import xyz.terrifictable.setting.settings.NumberSetting;
import xyz.terrifictable.util.MoveUtils;
import xyz.terrifictable.util.RotationUtil;
import xyz.terrifictable.util.Timer;

import java.awt.*;

public class TargetStrafe extends Module {
    private int direction = -1;
    private Timer switchTimer = new Timer();
    public Minecraft mc = Minecraft.getMinecraft();
    private NumberSetting renderHeight = new NumberSetting("Render Height", 0.05D, 0.005D, 1.0D, 0.005D);
    private NumberSetting distance = new NumberSetting("Distance", 2.0, 1.0, 5.0, 0.5);
    private BooleanSetting onSpace = new BooleanSetting("On Space", true);
    private BooleanSetting render = new BooleanSetting("Render", true);
    private NumberSetting hue = new NumberSetting("Hue", 0.8, 0.0, 1.0, 0.1);

    public TargetStrafe() {
        super("TargetStrafe", 0, Category.COMBAT);
        addSettings(onSpace, render, renderHeight, distance, hue);
    }

    public void onUpdate() {
        if (!this.isToggled()) return;

        if (mc.thePlayer.isCollidedHorizontally && this.switchTimer.hasTimeElapsed(200L, false)) {
            this.direction = -this.direction;
            this.switchTimer.reset();
        }
    }

    public void onEvent(Event em) {
        if (!this.isToggled()) return;
        if (!(em instanceof EventMotion)) return;


        if (canStrafe()) {
            strafe((EventMotion) em, MoveUtils.getBaseSpeed());
        }
    }

    public void onRender() {
        if (!this.isToggled()) return;

        if (canStrafe()) {
            if(render.isEnabled()){
                drawRadius((Entity) Killaura.target, ModuleManager.getTicks(), distance.getValue());
            }
        }
    }

    public void strafe(EventMotion e, double moveSpeed) {
        float[] rots = RotationUtil.getRotations((Entity) Killaura.target);
        double dist = mc.thePlayer.getDistanceToEntity((Entity) Killaura.target);
        if (dist >= distance.getValue()) {
            MoveUtils.setMotionWithValues(e, moveSpeed - 0.0, rots[0], 1.0D, this.direction);
        } else {
            MoveUtils.setMotionWithValues(e, moveSpeed, rots[0], 0.0D, this.direction);
        }
    }

    private void drawRadius(Entity entity, float partialTicks, double rad) {
        Color c = Color.getHSBColor((float) hue.getValue(), 1.0F, 1.0F);
        float red = c.getRed() / 255.0F;
        float green = c.getGreen() / 255.0F;
        float blue = c.getBlue() / 255.0F;
        float points = 90.0F;
        GlStateManager.enableDepth();
        int count = 0;
        for (double il = 0.0D; il < ((renderHeight.getValue() <= 0.005D) ? 0.001D : renderHeight.getValue()); il += 0.01D) {
            count++;
            GL11.glPushMatrix();
            GL11.glDisable(3553);
            GL11.glEnable(2848);
            GL11.glEnable(2881);
            GL11.glEnable(2832);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glHint(3154, 4354);
            GL11.glHint(3155, 4354);
            GL11.glHint(3153, 4354);
            GL11.glDisable(2929);
            GL11.glLineWidth(1.3F);
            GL11.glBegin(3);
            double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - RenderManager.viewerPosX;
            double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - RenderManager.viewerPosY;
            double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - RenderManager.viewerPosZ;
            double pix2 = 6.283185307179586D;
            float speed = 5000.0F;
            float baseHue = (float) (System.currentTimeMillis() % (int) speed);
            while (baseHue > speed)
                baseHue -= speed;
            baseHue /= speed;
            for (int i = 0; i <= 90; i++) {
                float max = (i + (float) (il * 8.0D)) / points;
                float hue = max + baseHue;
                while (hue > 1.0F)
                    hue--;
                GL11.glColor3f(red, green, blue);
                GL11.glVertex3d(x + rad * Math.cos(i * 6.283185307179586D / points), y + il,
                        z + rad * Math.sin(i * 6.283185307179586D / points));
            }
            GL11.glEnd();
            GL11.glDepthMask(true);
            GL11.glEnable(2929);
            GL11.glDisable(2848);
            GL11.glDisable(2881);
            GL11.glEnable(2832);
            GL11.glEnable(3553);
            GL11.glPopMatrix();
            GlStateManager.color(255.0F, 255.0F, 255.0F);
        }
    }

    public boolean canStrafe() {
        if (onSpace.isEnabled() && !mc.gameSettings.keyBindJump.isKeyDown())
            return false;
        return (isToggled() && MoveUtils.isMoving() && Killaura.target != null
                && MoveUtils.isBlockUnderneath(Killaura.target.getPosition())
                && (Client.getModuleByName("speed").isToggled()
                || Client.getModuleByName("fly").isToggled()));
    }
}
