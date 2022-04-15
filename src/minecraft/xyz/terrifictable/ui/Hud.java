package xyz.terrifictable.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import xyz.terrifictable.Client;
import xyz.terrifictable.events.listeners.EventRenderGui;
import xyz.terrifictable.module.Module;
import xyz.terrifictable.module.modules.render.ArrayList;
import xyz.terrifictable.setting.Setting;
import xyz.terrifictable.setting.settings.BooleanSetting;
import xyz.terrifictable.setting.settings.ModeSetting;
import xyz.terrifictable.setting.settings.NumberSetting;
import xyz.terrifictable.util.ColorUtil;

import java.util.Comparator;

public class Hud {
    public Minecraft mc = Minecraft.getMinecraft();
    private boolean rainbow;
    private boolean rainbowWave = false;
    private boolean rainbowNormal = false;
    private double rainbowSpeed;
    private double rainbowBrightness;
    private double rainbowSaturation;
    private long rainbowAmplifier;


    public void draw() {
        ScaledResolution sr = new ScaledResolution(mc);
        FontRenderer fr = mc.fontRendererObj;

        Client.modules.sort(Comparator.comparingInt(m ->
                fr.getStringWidth(((Module)m).name))
                .reversed()
        );

        // Watermark
        int watermark_y = 2;
        GlStateManager.pushMatrix();
        GlStateManager.translate(2, 2, 0);
        GlStateManager.scale(1.6, 1.6, 1);
        GlStateManager.translate(-2, -2, 0);
        fr.drawString(Client.name, 2, watermark_y, 0xffffff);
        fr.drawString(" v" + Client.version, fr.getStringWidth(Client.name) + 2, watermark_y, 0xedf5a2);
        GlStateManager.popMatrix();

        if (Client.getModuleByName("arraylist").isToggled()) {

            // ArrayList
            for (Module module : Client.modules) {
                if (module.name.equalsIgnoreCase("arraylist")) {
                    for (Setting setting : module.settings) {
                        if (setting.name.equalsIgnoreCase("rainbow")){
                            rainbow = ((BooleanSetting)setting).enabled;
                        } else if (setting.name.equalsIgnoreCase("rainbow sat")) {
                            rainbowSaturation = ((NumberSetting) setting).getValue();
                        } else if (setting.name.equalsIgnoreCase("rainbow bright")) {
                            rainbowBrightness = ((NumberSetting) setting).getValue();
                        } else if (setting.name.equalsIgnoreCase("rainbowspeed")) {
                            rainbowSpeed = ((NumberSetting) setting).getValue();
                        } else if (setting.name.equalsIgnoreCase("rainbow amplifier")) {
                            rainbowAmplifier = (long) ((NumberSetting) setting).getValue();
                        } else if (setting.name.equalsIgnoreCase("rainbow mode")) {
                            if (((ModeSetting)setting).getMode().equalsIgnoreCase("wave")) {
                                rainbowWave = true;
                                rainbowNormal = false;
                            }
                            else if (((ModeSetting)setting).getMode().equalsIgnoreCase("normal")) {
                                rainbowNormal = true;
                                rainbowWave = false;
                            }
                        }
                    }
                }
            }

            int count = 0;
            for (Module module : Client.modules) {
                if (!module.isToggled() || module.name.equalsIgnoreCase("tabgui") || module.name.equalsIgnoreCase("arraylist")) continue;

                double offset = count * (fr.FONT_HEIGHT + 6);
                Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(module.name) - 4, offset, sr.getScaledWidth(), 6 + fr.FONT_HEIGHT + offset, 0x90000000);

                if (rainbow) {
                    if (rainbowNormal) { // NORMAL RAINBOW
                        Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(module.name) - 4, offset, sr.getScaledWidth() - fr.getStringWidth(module.name) - 5, 6 + fr.FONT_HEIGHT + offset, ColorUtil.getRainbow((float) rainbowSpeed, (float) rainbowSaturation, (float) rainbowBrightness));
                        // Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(module.name) - 5, 5 + fr.FONT_HEIGHT + offset, sr.getScaledWidth(), 6 + fr.FONT_HEIGHT + offset, ColorUtil.getRainbow((float) rainbowSpeed, (float) rainbowSaturation, (float) rainbowBrightness)); // Lines between modules

                    } else if (rainbowWave) { // RAINBOW WAVE
                        int color = ColorUtil.getRainbowWave((float) rainbowSpeed, (float) rainbowSaturation, (float) rainbowBrightness, count * rainbowAmplifier);
                        // System.out.println(color);
                        Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(module.name) - 4, offset, sr.getScaledWidth() - fr.getStringWidth(module.name) - 5, 6 + fr.FONT_HEIGHT + offset, color);
                        // Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(module.name) - 5, 5 + fr.FONT_HEIGHT + offset, sr.getScaledWidth(), 6 + fr.FONT_HEIGHT + offset, ColorUtil.getRainbowWave((float) rainbowSpeed, (float) rainbowSaturation, (float) rainbowBrightness, count * rainbowAmplifier)); // Lines between modules

                    }
                } else {
                    Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(module.name) - 4, offset, sr.getScaledWidth() - fr.getStringWidth(module.name) - 5, 6 + fr.FONT_HEIGHT + offset, 0xffff0000);
                    // Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(module.name) - 5, 5 + fr.FONT_HEIGHT + offset, sr.getScaledWidth(), 6 + fr.FONT_HEIGHT + offset, 0xffff0000); // Lines between modules
                }

                fr.drawString(module.name, sr.getScaledWidth() - fr.getStringWidth(module.name) - 1, 4 + offset, 0xffffff);
                count++;
            }
        }

        Client.onEvent(new EventRenderGui());
    }
}
