package xyz.terrifictable.module.modules.combat;

import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import xyz.terrifictable.events.Event;
import xyz.terrifictable.events.listeners.EventSendPacket;
import xyz.terrifictable.module.Module;
import xyz.terrifictable.setting.settings.ModeSetting;
import xyz.terrifictable.util.PlayerUtil;
import xyz.terrifictable.util.Timer;

public class Criticals extends Module {
    ModeSetting mode = new ModeSetting("Mode", "Packet", "Packet", "MiniJump");
    private final double[] watchdogOffsets = {0.056f, 0.016f, 0.003f};
    private final Timer timer = new Timer();
    private int groundTicks;

    public Criticals() {
        super("Criticals", 0, Category.COMBAT);
        addSettings(mode);
    }

    @Override
    public void onUpdate() {
        String mode = this.mode.getMode();

        this.displayName = "Criticals \u00A77" + mode;
    }

    public void onEvent(Event event) {
        if (!(event instanceof EventSendPacket)) return;

        String this_mode = mode.getMode();

        if(canCrit()) {
            if (((EventSendPacket) event).getPacket() instanceof C02PacketUseEntity) {
                C02PacketUseEntity packet = (C02PacketUseEntity) ((EventSendPacket) event).getPacket();
                if(packet.getAction() == C02PacketUseEntity.Action.ATTACK) {
                    if(this_mode.equalsIgnoreCase("Packet")) {
                        this.displayName = "Criticals\u00A77 " + "Packet";
                        if (groundTicks > 1 && timer.hasTimeElapsed(800L, false)) {
                            for (double offset : watchdogOffsets) {
                                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + offset, mc.thePlayer.posZ, false));
                            }
                            timer.reset();
                        }
                    }
                }
            }
            if(this_mode.equalsIgnoreCase("MiniJump")) {
                mc.thePlayer.jump();
                mc.thePlayer.motionY -= .30000001192092879;
            }
        }
    }

    private boolean canCrit() {
        if (mc.thePlayer != null)
            return !PlayerUtil.isInLiquid() && mc.thePlayer.onGround;
        return false;
    }
}
