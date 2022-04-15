package xyz.terrifictable.module.modules.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import org.lwjgl.input.Keyboard;
import xyz.terrifictable.events.Event;
import xyz.terrifictable.events.listeners.EventMotion;
import xyz.terrifictable.module.Module;
import xyz.terrifictable.setting.settings.BooleanSetting;
import xyz.terrifictable.setting.settings.ModeSetting;
import xyz.terrifictable.setting.settings.NumberSetting;
import xyz.terrifictable.util.Timer;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Killaura extends Module {
    public Timer timer = new Timer();
    public NumberSetting range = new NumberSetting("Range", 4, 1, 6, .1);
    public NumberSetting cps = new NumberSetting("CPS", 10, 1, 20, 1);
    public BooleanSetting swing = new BooleanSetting("Swing", true);

    public Killaura() {
        super("Killaura", Keyboard.KEY_K, Category.COMBAT);
        this.addSettings(range, cps, swing);
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void onEvent(Event event) {
        if (!(event instanceof EventMotion)) return;

        if (event.isPre()) {
            EventMotion eventMotion = (EventMotion)event;

            List<Entity> targets = mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
            targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < range.getValue() && entity != mc.thePlayer && !entity.isDead).collect(Collectors.toList()); // && entity.getHealth() > 0
            targets.sort(Comparator.comparingDouble(entity -> ((Entity)entity).getDistanceToEntity(mc.thePlayer)));

            // targets = targets.stream().filter(EntityPlayer.class::isInstance).collect(Collectors.toList());      // PLAYERS
            // targets = targets.stream().filter(EntityAnimal.class::isInstance).collect(Collectors.toList());      // ANIMALS
            // targets = targets.stream().filter(EntityMob.class::isInstance).collect(Collectors.toList());         // MOBS
            // targets = targets.stream().filter(EntityVillager.class::isInstance).collect(Collectors.toList());    // VILLAGER

            if (!targets.isEmpty()) {
                Entity target = targets.get(0);

                // ClientSide
                // mc.thePlayer.rotationYaw = (getRotations(target)[0]);
                // mc.thePlayer.rotationPitch = (getRotations(target)[1]);

                // ServerSide
                eventMotion.setYaw(getRotations(target)[0]);
                eventMotion.setPitch(getRotations(target)[1]);

                if (timer.hasTimeElapsed((long) (1000 / cps.getValue()), true)) {
                    if (swing.isEnabled())
                        mc.thePlayer.swingItem();
                    else
                        mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                    mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
                }
            }
        }
    }

    public float[] getRotations(Entity entity) {
        double deltaX = entity.posX + (entity.posX - entity.lastTickPosX) - mc.thePlayer.posX;
        double deltaY = entity.posY - 3.5 + entity.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight();
        double deltaZ = entity.posZ + (entity.posZ - entity.lastTickPosZ) - mc.thePlayer.posZ;
        double distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));
        float yaw = (float) Math.toDegrees(-Math.atan(deltaX / deltaZ));
        float pitch = (float) -Math.toDegrees(Math.atan(deltaY / distance));

        if (deltaX < 0 && deltaZ < 0) {
            yaw = (float) (90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        } else if (deltaX > 0 && deltaZ < 0) {
            yaw = (float) (-90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        }

        return new float[] {yaw, pitch};
    }
}
