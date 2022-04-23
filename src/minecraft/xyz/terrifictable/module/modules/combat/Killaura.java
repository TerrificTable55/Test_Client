package xyz.terrifictable.module.modules.combat;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0APacketAnimation;
import org.lwjgl.input.Keyboard;
import xyz.terrifictable.Client;
import xyz.terrifictable.events.Event;
import xyz.terrifictable.events.listeners.EventMotion;
import xyz.terrifictable.module.Module;
import xyz.terrifictable.setting.Setting;
import xyz.terrifictable.setting.settings.BooleanSetting;
import xyz.terrifictable.setting.settings.ModeSetting;
import xyz.terrifictable.setting.settings.NumberSetting;
import xyz.terrifictable.util.RotationUtil;
import xyz.terrifictable.util.Timer;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Killaura extends Module {
    public Timer timer = new Timer();
    public static Entity target;

    public NumberSetting range = new NumberSetting("Range", 4, 1, 6, .1);
    public NumberSetting cps = new NumberSetting("CPS", 15, 1, 30, 1);
    public ModeSetting swing = new ModeSetting("Swing", "Normal", "Normal", "Silent");
    public BooleanSetting targetPlayer = new BooleanSetting("Players", true);
    public BooleanSetting targetMobs = new BooleanSetting("Mobs", true);
    public BooleanSetting targetAnimals = new BooleanSetting("Animals", true);

    public Killaura() {
        super("Killaura", Keyboard.KEY_K, Category.COMBAT);
        this.addSettings(range, cps , targetPlayer, targetMobs, targetAnimals, swing);
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
            targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < range.getValue() && entity != mc.thePlayer && !(((EntityLivingBase) entity).getHealth() <= 0)).collect(Collectors.toList()); // && entity.getHealth() > 0
            targets.sort(Comparator.comparingDouble(entity -> ((Entity)entity).getDistanceToEntity(mc.thePlayer)));
            List<Entity> newtargets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < range.getValue() && entity != mc.thePlayer && !entity.isDead).collect(Collectors.toList());
            newtargets.sort(Comparator.comparingDouble(entity -> ((Entity) entity).getDistanceToEntity(mc.thePlayer)));

            if (targetAnimals.isEnabled() && targetMobs.isEnabled() && targetMobs.isEnabled()) {
            } else {
                if (!targetPlayer.isEnabled()) {
                    newtargets.removeAll(targets.stream().filter(EntityPlayer.class::isInstance).collect(Collectors.toList()));   // PLAYERS
                }
                if (!targetAnimals.isEnabled()) {
                    newtargets.removeAll(targets.stream().filter(EntityAnimal.class::isInstance).collect(Collectors.toList()));      // ANIMALS
                }
                if (!targetMobs.isEnabled()) {
                    newtargets.removeAll(targets.stream().filter(EntityMob.class::isInstance).collect(Collectors.toList()));         // MOBS
                }
            }

            if (!newtargets.isEmpty()) {
                target = newtargets.get(0);

                if (!isEntityInRange((EntityLivingBase) target))
                    target = null;


                this.displayName = "Killaura \u00A77" + target.getName();

                // ClientSide
                // mc.thePlayer.rotationYaw = (getRotations(target)[0]);
                // mc.thePlayer.rotationPitch = (getRotations(target)[1]);

                // ServerSide
                eventMotion.setYaw(RotationUtil.getRotations(target)[0]);
                eventMotion.setPitch(RotationUtil.getRotations(target)[1]);

                if (timer.hasTimeElapsed((long) (1000 / cps.getValue()), true)) {
                    switch (swing.getMode()) {
                        case "Normal":
                            mc.thePlayer.swingItem();
                            break;
                        case "Silent":
                            mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                            break;
                    }
                    mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
                }
            }
        }
    }

    public boolean isEntityInRange(EntityLivingBase target) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        NumberSetting range_setting = null;

        if (!target.isDead) {
            for (Setting setting : Client.getModuleByName("killaura").settings) {
                if (setting.name.equalsIgnoreCase("range")) {
                    range_setting = (NumberSetting) setting;
                    break;
                }
            }

            if (range_setting != null && target.getDistanceToEntity(mc.thePlayer) < range_setting.getValue()) {
                return true;
            }
        }

        return false;
    }
}
