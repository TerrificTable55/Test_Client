package xyz.terrifictable.events.listeners;

import net.minecraft.network.Packet;
import xyz.terrifictable.events.Event;

public class EventRecievePacket extends Event<EventRecievePacket> {

    private Packet packet;

    public EventRecievePacket(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }
    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}
