package xyz.terrifictable.module.modules.render;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityChest;
import xyz.terrifictable.module.Module;
import xyz.terrifictable.setting.settings.BooleanSetting;
import xyz.terrifictable.setting.settings.NumberSetting;
import xyz.terrifictable.util.esp.ChestESPUtil;
import xyz.terrifictable.util.esp.MobESPUtils;

import java.awt.*;

public class ESP extends Module {
    BooleanSetting chestEsp = new BooleanSetting("Chest", true);
    BooleanSetting playerESP = new BooleanSetting("Player", true);
    BooleanSetting mobESP = new BooleanSetting("Mob", true);
    BooleanSetting friendly = new BooleanSetting("Animal", true);
    NumberSetting red = new NumberSetting("Chest Red", 0, 0, 255, 1);
    NumberSetting green = new NumberSetting("Chest Green", 0, 0, 255, 1);
    NumberSetting blue = new NumberSetting("Chest Blue", 255, 0, 255, 1);

    public ESP() {
        super("ESP", 0, Category.RENDER);
        addSettings(chestEsp, playerESP, mobESP, red, green, blue);
    }

    public void onRender() {
        if (!this.isToggled()) return;

        // CHEST ESP
        if (chestEsp.isEnabled()) {
            Color color = new Color((int) red.getValue(), (int) green.getValue(), (int) blue.getValue());

            for (Object o : mc.theWorld.loadedTileEntityList) {
                if (o instanceof TileEntityChest) {
                    ChestESPUtil.blockChestESP(((TileEntityChest)o).getPos(), 2.0f, color, color);
                    // ChestESPUtil.blockChestESP(((TileEntityChest)o).getPos());
                }
            }
        }
        // PLAYER ESP
        if (playerESP.isEnabled()) {
            for (Object p : mc.theWorld.loadedEntityList) {
                if (p instanceof EntityPlayer) {
                    if (p != mc.thePlayer)
                        MobESPUtils.entityESPBox(((EntityPlayer) p), 0);
                }
            }
        }
        // MOB ESP
        if (mobESP.isEnabled()) {
            for (Object p : mc.theWorld.loadedEntityList) {
                if (p instanceof EntityMob) {
                    MobESPUtils.entityESPBox(((EntityMob) p), 0);
                }
            }
        }

        // FRIENDLY ESP
        if (friendly.isEnabled()) {
            for (Object p : mc.theWorld.loadedEntityList) {
                if (p instanceof EntityAnimal) {
                    MobESPUtils.entityESPBox(((Entity) p), 4);
                }
            }
        }
    }
}
