package xyz.terrifictable.module.modules.movement;

import org.lwjgl.input.Keyboard;
import xyz.terrifictable.events.Event;
import xyz.terrifictable.events.listeners.EventUpdate;
import xyz.terrifictable.module.Module;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", Keyboard.KEY_C, Category.MOVEMENT);
    }

    public void onEnable() {}

    public void onEvent(Event event) {
        if (!this.isToggled() || mc.thePlayer == null || mc.theWorld == null) return;
        if (!(event instanceof EventUpdate) && !event.isPre()) return;

        if (mc.thePlayer.moveForward > 0 && !mc.thePlayer.isUsingItem() && !mc.thePlayer.isSneaking() && !mc.thePlayer.isCollidedHorizontally)
            mc.thePlayer.setSprinting(true);
    }

    public void onDisable() {
        mc.thePlayer.setSprinting(mc.gameSettings.keyBindSprint.isPressed());
    }
}
