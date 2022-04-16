package xyz.terrifictable.module.modules.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import xyz.terrifictable.events.Event;
import xyz.terrifictable.events.listeners.EventMotion;
import xyz.terrifictable.module.Module;
import xyz.terrifictable.setting.settings.BooleanSetting;
import xyz.terrifictable.setting.settings.NumberSetting;
import xyz.terrifictable.util.RotationUtil;
import xyz.terrifictable.util.Timer;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AimAssist extends Module {
    public Timer timer = new Timer();
    public NumberSetting range = new NumberSetting("Range", 4, 1, 6, .1);
    public BooleanSetting targetPlayer = new BooleanSetting("Players", true);
    public BooleanSetting targetMobs = new BooleanSetting("Mobs", true);
    public BooleanSetting targetAnimals = new BooleanSetting("Animals", true);

    public AimAssist() {
        super("AimAssist", 0, Category.COMBAT);
        this.addSettings(range, targetPlayer, targetMobs, targetAnimals);
    }

    public void onEvent(Event event) {
        if (!(event instanceof EventMotion)) return;

        if (event.isPre()) {
            EventMotion eventMotion = (EventMotion)event;

            List<Entity> targets = mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
            targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < range.getValue() && entity != mc.thePlayer && !entity.isDead).collect(Collectors.toList()); // && entity.getHealth() > 0
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
                Entity target = newtargets.get(0);

                // ClientSide
                mc.thePlayer.rotationYaw = (RotationUtil.getRotations(target)[0]);
                mc.thePlayer.rotationPitch = (RotationUtil.getRotations(target)[1]);

                // ServerSide
                eventMotion.setYaw(RotationUtil.getRotations(target)[0]);
                eventMotion.setPitch(RotationUtil.getRotations(target)[1]);
            }
        }

    }
}
