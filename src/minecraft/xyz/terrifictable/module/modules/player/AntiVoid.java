package xyz.terrifictable.module.modules.player;

import net.minecraft.util.BlockPos;
import xyz.terrifictable.Client;
import xyz.terrifictable.events.Event;
import xyz.terrifictable.events.listeners.EventMotion;
import xyz.terrifictable.module.Module;

public class AntiVoid extends Module {
    private boolean hasfallen;

    public AntiVoid() {
        super("AntiVoid", 0, Category.PLAYER);
    }

    public void onEvent(Event e) {
        if (!(e instanceof EventMotion)) return;
        if (e.isPre() && !isBlockUnderneath() && mc.thePlayer.fallDistance > 2.85F
                && !Client.getModuleByName("fly").isToggled()) {
            ((EventMotion) e).setY(EventMotion.getY() + 8.0D);
            this.hasfallen = true;
        }
    }

    private boolean isBlockUnderneath() {
        boolean blockUnderneath = false;
        for (int i = 0; i < mc.thePlayer.posY + 2.0D; i++) {
            BlockPos pos = new BlockPos(mc.thePlayer.posX, i, mc.thePlayer.posZ);
            if (!(mc.theWorld.getBlockState(pos).getBlock() instanceof net.minecraft.block.BlockAir))
                blockUnderneath = true;
        }
        return blockUnderneath;
    }

    public void onEnable() {
        super.onEnable();
    }

    public void onDisable() {
        super.onDisable();
    }

}
