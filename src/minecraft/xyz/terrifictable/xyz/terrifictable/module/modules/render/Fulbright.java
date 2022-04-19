package xyz.terrifictable.module.modules.render;

import org.lwjgl.input.Keyboard;
import xyz.terrifictable.module.Module;

public class Fulbright extends Module {
    private float oldGamma;

    public Fulbright() {
        super("Fulbright", Keyboard.KEY_M, Category.RENDER);
    }

    public void onEnable() {
        oldGamma = mc.gameSettings.gammaSetting;
        mc.gameSettings.gammaSetting = 1000F;
    }

    public void onDisable() {
        mc.gameSettings.gammaSetting = oldGamma;
    }
}
