package xyz.terrifictable.module.modules.player;

import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;
import xyz.terrifictable.events.Event;
import xyz.terrifictable.events.listeners.EventUpdate;
import xyz.terrifictable.module.Module;

public class NoFall extends Module {
    public NoFall() {
        super("NoFall", Keyboard.KEY_N, Category.PLAYER);
    }

    public void onEvent(Event event) {
        if (!(event instanceof EventUpdate) && !event.isPre()) return;

        if (mc.thePlayer.fallDistance > 2F)
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
    }
}
