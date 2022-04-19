package xyz.terrifictable.module.modules.player;

import net.minecraft.util.MovingObjectPosition;
import xyz.terrifictable.module.Module;
import xyz.terrifictable.util.Invoker;

public class AutoMine extends Module {
    public AutoMine() {
        super("AutoMine", 0, Category.PLAYER);
    }

    @Override
    public void onDisable() {
        Invoker.setKeyBindAttackPressed(false);
        this.toggled = false;
    }

    @Override
    public void onUpdate() {
        if (!this.isToggled()) return;

        MovingObjectPosition hover = Invoker.getObjectMouseOver();

        if (hover.typeOfHit != null) {
            if (hover.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
                Invoker.setKeyBindAttackPressed(true);
        } else
            Invoker.setKeyBindAttackPressed(false);
    }
}
