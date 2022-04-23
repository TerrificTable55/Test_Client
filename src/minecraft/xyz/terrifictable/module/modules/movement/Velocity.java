package xyz.terrifictable.module.modules.movement;

import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import xyz.terrifictable.events.Event;
import xyz.terrifictable.events.listeners.EventPacket;
import xyz.terrifictable.module.Module;
import xyz.terrifictable.setting.settings.BooleanSetting;
import xyz.terrifictable.setting.settings.NumberSetting;

public class Velocity extends Module {
    BooleanSetting explotion = new BooleanSetting("Explosion", true);
    NumberSetting horizontal = new NumberSetting("Horizontal", 0, 0, 100, 1);
    NumberSetting vertical = new NumberSetting("Horizontal", 0, 0, 100, 1);

    public Velocity() {
        super("Velocity", 0, Category.MOVEMENT);
        addSettings(explotion, horizontal, vertical);
    }

    public void onUpdate() {  }

    public void onEvent(Event event) {
        if (!(event instanceof EventPacket)) return;

        this.displayName = ("Velocity\2477 " + horizontal.getValue() + "% " + this.vertical.getValue() + "%");
        if (((EventPacket) event).getPacket() instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity packet = (S12PacketEntityVelocity) ((EventPacket) event).getPacket();
            event.setCancelled(mc.theWorld != null && mc.thePlayer != null && mc.theWorld.getEntityByID(packet.getEntityID()) == mc.thePlayer);
        } else if (((EventPacket) event).getPacket() instanceof S27PacketExplosion) {
            if (explotion.isEnabled()) {
                event.setCancelled(true);
            }
        }
    }
}
