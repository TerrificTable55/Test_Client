package xyz.terrifictable.module.modules.player;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.input.Keyboard;
import xyz.terrifictable.Client;
import xyz.terrifictable.events.Event;
import xyz.terrifictable.events.listeners.EventMotion;
import xyz.terrifictable.events.listeners.EventPacket;
import xyz.terrifictable.events.listeners.EventSendPacket;
import xyz.terrifictable.events.listeners.EventUpdate;
import xyz.terrifictable.module.Module;
import xyz.terrifictable.setting.settings.ModeSetting;

public class NoFall extends Module {
    ModeSetting mode = new ModeSetting("Mode", "Packet", "Packet", "Spoof");

    public NoFall() {
        super("NoFall", Keyboard.KEY_N, Category.PLAYER);
        addSettings(mode);
    }

    public void onEvent(Event event) {
        if (!(event instanceof EventUpdate) && !event.isPre()) return;

        switch (mode.getMode()) {
            case "Spoof": {
                if (event.isPre() && mc.thePlayer.fallDistance > 2F) {
                    ((EventMotion) event).setOnGround(true);
                }
                break;
            }
            case "Packet": {
                if (mc.thePlayer.fallDistance > 2f) {
                    if (!(event instanceof EventSendPacket)) return;
                    if (((EventSendPacket) event).getPacket() instanceof C03PacketPlayer) {
                        C03PacketPlayer c03 = (C03PacketPlayer) ((EventSendPacket) event).getPacket();
                        c03.onGround = true;
                    }
                }
                break;
            }
        }
    }
}
