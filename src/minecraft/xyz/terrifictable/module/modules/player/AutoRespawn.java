package xyz.terrifictable.module.modules.player;

import xyz.terrifictable.module.Module;

public class AutoRespawn extends Module {
    public AutoRespawn() {
        super("AutoRespawn", 0, Category.PLAYER);
    }

    @Override
    public void onUpdate() {
        if (!this.isToggled()) return;

        if (mc.thePlayer.isDead)
            mc.thePlayer.respawnPlayer();
    }
}
