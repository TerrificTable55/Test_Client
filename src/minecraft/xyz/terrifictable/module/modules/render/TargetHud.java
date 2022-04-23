package xyz.terrifictable.module.modules.render;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import xyz.terrifictable.Client;
import xyz.terrifictable.events.Event;
import xyz.terrifictable.events.listeners.EventRenderGui;
import xyz.terrifictable.module.Module;
import xyz.terrifictable.module.modules.combat.Killaura;
import xyz.terrifictable.setting.Setting;
import xyz.terrifictable.setting.settings.ModeSetting;
import xyz.terrifictable.setting.settings.NumberSetting;

public class TargetHud extends Module {

    private EntityLivingBase target;
    public ScaledResolution sr;

    public ModeSetting mode = new ModeSetting("Targets", "Pointed", "Pointed", "KillAura", "KillAura > Pointed");


    public TargetHud() {
        super("TargetHud", 0, Category.RENDER);
        addSettings(mode);
    }


    public void onEvent(Event event) {
        if (!this.isToggled()) return;
        if (!(event instanceof EventRenderGui)) return;

        sr = new ScaledResolution(mc);

        if (Killaura.target instanceof EntityItemFrame || mc.pointedEntity instanceof EntityItemFrame) return;

        if (mode.getMode().equalsIgnoreCase("pointed")) {
            target = (EntityLivingBase) mc.pointedEntity;
        } else if (mode.getMode().equalsIgnoreCase("killaura")) {
            target = (EntityLivingBase) Killaura.target;
        } else if (mode.getMode().equalsIgnoreCase("KillAura > Pointed")) {
            target = (EntityLivingBase) mc.pointedEntity;
            if (Client.getModuleByName("killaura").isToggled()) {
                target = (EntityLivingBase) Killaura.target;
            }
        }

        if (target == null || mode.getMode().equalsIgnoreCase("killaura") && !Client.getModuleByName("killaura").isToggled() || target.isDead || !isEntityInRange(target)) return;

        double x = sr.getScaledWidth() / 2f + 12 + 5;
        double y = (sr.getScaledHeight() / 2f) + 5;

        int color_1 = -1;
        if (target instanceof EntityMob) color_1 = 0xffff0000;
        if (target instanceof EntityAnimal || target instanceof EntityVillager) color_1 = 0xff00ff00;
        if (target instanceof EntityPlayer) color_1 = 0xff006eff;
        // if (target instanceof EntityPlayer && target in friends) ...

        // Gui.drawRect(x - 5, y - 27, x + mc.fontRendererObj.getStringWidth(target.getName()) + 25, y + mc.fontRendererObj.FONT_HEIGHT + 8, 0x90000000);
        Gui.drawRect(x - 5, y - 17, x + mc.fontRendererObj.getStringWidth(target.getName()) + 5, y + mc.fontRendererObj.FONT_HEIGHT + 2, 0x90000000);
        GuiInventory.drawEntityOnScreen((int) (x + mc.fontRendererObj.getStringWidth(target.getName()) + 12), (int) (y + 11), 15, 50, 0, this.target);
        Client.fr.drawString(target.getName(), x, y - 12, color_1);
        Client.fr.drawString(String.valueOf(Math.round(target.getHealth())), x, y - 12 + (mc.fontRendererObj.FONT_HEIGHT + 2), -1);
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
