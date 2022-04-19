package xyz.terrifictable.module.modules.player;

import org.lwjgl.input.Keyboard;
import xyz.terrifictable.module.Module;
import xyz.terrifictable.setting.settings.NumberSetting;
import xyz.terrifictable.util.Invoker;

public class FastLadder extends Module {
    public NumberSetting speed = new NumberSetting("Speed", 1.1, 0.1, 5, 0.1);

    private int ticks = 0;

    public FastLadder() {
        super("FastLadder", 0, Category.PLAYER);
        addSettings(speed);
    }

    @Override
    public void onDisable() {
        Invoker.setMotionX(1);
    }

    @Override
    public void onUpdate() {
        if (!this.isToggled()) return;

        ticks++;
        if (Invoker.isOnLadder() && Keyboard.isKeyDown(Invoker.getForwardCode())) {
            Invoker.setMotionY(speed.getValue());
        }
    }
}
