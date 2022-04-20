package xyz.terrifictable.module.modules.player;

import xyz.terrifictable.module.Module;

public class FastPlace extends Module {
    public FastPlace() {
        super("FastPlace", 0, Category.PLAYER);
    }

    @Override
    public void onDisable() {
        mc.rightClickDelayTimer = 4;
    }

    @Override
    public void onUpdate() {
        if (!this.isToggled()) return;

        mc.rightClickDelayTimer = 0;
    }
}
