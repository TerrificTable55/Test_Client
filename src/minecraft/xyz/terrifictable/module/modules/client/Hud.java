package xyz.terrifictable.module.modules.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import xyz.terrifictable.Client;
import xyz.terrifictable.events.Event;
import xyz.terrifictable.events.listeners.EventRenderGui;
import xyz.terrifictable.module.Module;
import xyz.terrifictable.setting.settings.BooleanSetting;
import xyz.terrifictable.setting.settings.NumberSetting;
import xyz.terrifictable.util.ColorUtil;
import xyz.terrifictable.util.font.MinecraftFontRenderer;

public class Hud extends Module {
    private MinecraftFontRenderer fr;
    private ScaledResolution sr;

    public BooleanSetting watermark = new BooleanSetting("Watermark", true);
    public BooleanSetting fps = new BooleanSetting("FPS", true);
    public NumberSetting maxFPS = new NumberSetting("Max FPS", 500, 1, 2000, 10);
    public BooleanSetting coords = new BooleanSetting("Coords", true);

    public Hud() {
        super("Hud", 0, Category.CLIENT);
        this.addSettings(watermark,
                fps, maxFPS,
                coords);
        this.toggled = true;
    }

    public void onEvent(Event event) {
        if (!this.isToggled()) return;
        sr = new ScaledResolution(mc);
        fr = Client.fr;

        if (event instanceof EventRenderGui) {
            // Watermark
            if (watermark.isEnabled()) {
                int watermark_y = 2;
                GlStateManager.pushMatrix();
                GlStateManager.translate(2, 2, 0);
                GlStateManager.scale(2, 2, 1);
                GlStateManager.translate(-2, -2, 0);
                mc.fontRendererObj.drawString(Client.name, 2, watermark_y, 0xffffff);
                mc.fontRendererObj.drawString(" v" + Client.version, fr.getStringWidth(Client.name) + 2, watermark_y, 0xedf5a2);
                GlStateManager.popMatrix();
            }

            if (fps.isEnabled()) {
                // FPS
                fr.drawString("[FPS]", 5, 110, 0xffff9900);
                fr.drawString(String.valueOf(Minecraft.getDebugFPS()), fr.getStringWidth("[FPS] ") + 8, 110, ColorUtil.colorEqTNum((int) maxFPS.getValue(), Minecraft.getDebugFPS()));
            }

            if (coords.isEnabled()) {
                // Coords
                float y = sr.getScaledHeight() - 11.5f;

                fr.drawString("[X]", 5.0, y, 0xffff9900);
                fr.drawString(String.valueOf(Math.round(mc.thePlayer.posX)), 5 + fr.getStringWidth("[X]") + 8, y, -1);
                fr.drawString("[Y]", 10 + fr.getStringWidth(Math.round(mc.thePlayer.posX) + "[X] ") + 10, y, 0xffff9900);
                fr.drawString(String.valueOf(Math.round(mc.thePlayer.posY)), 5 + fr.getStringWidth("[X]" + "[Y] " + Math.round(mc.thePlayer.posX)) + 20, y, -1);
                fr.drawString("[Z]", 15 + fr.getStringWidth(Math.round(mc.thePlayer.posX) + Math.round(mc.thePlayer.posY) + "[X] " + "[Y] ") + 30, y, 0xffff9900);
                fr.drawString(String.valueOf(Math.round(mc.thePlayer.posZ)), 5 + fr.getStringWidth(Math.round(mc.thePlayer.posX) + Math.round(mc.thePlayer.posY) + "[X] " + "[Y] ") + 60, y, -1);
            }

        }

    }
}
