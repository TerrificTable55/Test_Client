package xyz.terrifictable.events.listeners;

import net.minecraft.network.Packet;
import xyz.terrifictable.events.Event;

public class EventSendPacket extends Event<EventSendPacket> {

    private Packet packet;

    public EventSendPacket(Packet packet) {
        this.packet = null;
        setPacket(packet);
    }

    public Packet getPacket() {
        return packet;
    }
    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}
