package xyz.terrifictable.module.modules.movement;

import org.lwjgl.input.Keyboard;
import xyz.terrifictable.events.Event;
import xyz.terrifictable.events.listeners.EventUpdate;
import xyz.terrifictable.module.Module;

public class Fly extends Module {
    public Fly() {
        super("Fly", Keyboard.KEY_F, Category.MOVEMENT);
    }

    public void onDisable() {
        mc.thePlayer.capabilities.isFlying = false;
    }

    public void onEvent(Event event) {
        if (!(event instanceof EventUpdate) && !event.isPre()) return;
        mc.thePlayer.capabilities.isFlying = true;
    }
}
