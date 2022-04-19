package xyz.terrifictable.module.modules.render;

import net.minecraft.block.Block;
import org.lwjgl.input.Keyboard;
import xyz.terrifictable.module.Module;

import java.util.*;

public class Xray extends Module {
    private float old_gamma;
    public static ArrayList<Block> xrayBlocks = new ArrayList<Block>();

    public Xray() {
        super("Xray", Keyboard.KEY_X, Category.RENDER);
    }

    @Override
    public void onEnable() {
        this.toggled = true;
        this.old_gamma = mc.gameSettings.gammaSetting;
        mc.gameSettings.gammaSetting = 10.0f;
        mc.gameSettings.ambientOcclusion = 0;
        mc.renderGlobal.loadRenderers();
    }

    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = old_gamma;
        mc.gameSettings.ambientOcclusion = 0;
        mc.renderGlobal.loadRenderers();
    }

    public static boolean isXrayBlock(Block block) {
        if (xrayBlocks.contains(block)) {
            return true;
        }
        return false;
    }
}
