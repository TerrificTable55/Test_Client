package xyz.terrifictable.module.modules.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import xyz.terrifictable.events.Event;
import xyz.terrifictable.events.listeners.EventRenderGui;
import xyz.terrifictable.module.Module;
import xyz.terrifictable.setting.settings.BooleanSetting;
import xyz.terrifictable.setting.settings.NumberSetting;
import xyz.terrifictable.util.ColorUtil;

public class Hud extends Module {
    private ScaledResolution sr = new ScaledResolution(mc);
    private FontRenderer fr = mc.fontRendererObj;

    public BooleanSetting watermark = new BooleanSetting("Watermark", true);
    public BooleanSetting fps = new BooleanSetting("FPS", true);
    public NumberSetting maxFPS = new NumberSetting("Max FPS", 500, 1, 2000, 10);

    public Hud() {
        super("Hud", 0, Category.RENDER);
        this.addSettings(watermark,
                fps, maxFPS);
        this.toggled = true;
    }

    public void onEvent(Event event) {

        if (event instanceof EventRenderGui) {
            if (fps.isEnabled()) {
                // FPS
                fr.drawString("[FPS]", 5, 100, 0xffff9900);
                fr.drawString(String.valueOf(Minecraft.getDebugFPS()), fr.getStringWidth("[FPS] ") + 5, 100, ColorUtil.colorEqTNum((int) maxFPS.getValue(), Minecraft.getDebugFPS()));
            }

            // else if () {
            // }

        }

    }
}
