package xyz.terrifictable.module.modules.render;

import org.lwjgl.input.Keyboard;
import xyz.terrifictable.module.Module;
import xyz.terrifictable.setting.settings.BooleanSetting;
import xyz.terrifictable.setting.settings.ModeSetting;
import xyz.terrifictable.setting.settings.NumberSetting;

public class ArrayList extends Module {

    public NumberSetting rainbowDelay = new NumberSetting("RainbowSpeed", 4, 1, 15, 1);
    public NumberSetting rainbowSaturation = new NumberSetting("Rainbow Sat", 1, 0, 1, 0.1);
    public NumberSetting rainbowBrightness = new NumberSetting("Rainbow Bright", 1, 0, 1, 0.1);
    public NumberSetting rainbowAmplifier = new NumberSetting("Rainbow Amplifier", 150, 0, 10000, 10);
    public BooleanSetting rainbow = new BooleanSetting("Rainbow", true);
    public ModeSetting rainbowMode = new ModeSetting("Rainbow Mode", "Normal", "Normal", "Wave");

    public ArrayList() {
        super("ArrayList", Keyboard.KEY_NONE, Module.Category.RENDER);
        this.addSettings(rainbow, rainbowMode, rainbowAmplifier, rainbowSaturation, rainbowBrightness, rainbowDelay); // hue, saturation, brightness,
        toggled = true;
    }
}
