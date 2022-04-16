package xyz.terrifictable.module.modules.movement;

import org.lwjgl.input.Keyboard;
import xyz.terrifictable.events.Event;
import xyz.terrifictable.events.listeners.EventUpdate;
import xyz.terrifictable.module.Module;
import xyz.terrifictable.setting.settings.NumberSetting;

public class Fly extends Module {
    public NumberSetting speed = new NumberSetting("Speed", 0.1, 0.1, 1, 0.1);

    public Fly() {
        super("Fly", Keyboard.KEY_F, Category.MOVEMENT);
        addSettings(speed);
    }

    public void onDisable() {
        mc.thePlayer.capabilities.isFlying = false;
    }

    public void onEvent(Event event) {
        if (!(event instanceof EventUpdate) && !event.isPre()) return;

        if (mc.gameSettings.keyBindJump.isPressed())
            mc.thePlayer.motionY += speed.getValue();

        if (mc.gameSettings.keyBindSneak.isPressed())
            mc.thePlayer.motionY -= speed.getValue();

        if (mc.gameSettings.keyBindForward.isPressed())
            mc.thePlayer.capabilities.setFlySpeed((float) speed.getValue() / 2F);

        mc.thePlayer.capabilities.isFlying = true;
    }
}
