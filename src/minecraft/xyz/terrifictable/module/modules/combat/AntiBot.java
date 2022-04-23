package xyz.terrifictable.module.modules.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.neuroph.core.input.Min;
import xyz.terrifictable.Client;
import xyz.terrifictable.module.Module;
import xyz.terrifictable.setting.settings.NumberSetting;

public class AntiBot extends Module {
    public static NumberSetting ticks = new NumberSetting("Ticks Existed", 8.5, 0, 10, 0.5);

    public AntiBot() {
        super("AntiBot", 0, Category.COMBAT);
    }

    @Override

    public void onUpdate() {
        if (!this.isToggled()) return;

        if (mc.thePlayer != null && mc.theWorld != null) {
            try {
                for (Entity p : mc.theWorld.loadedEntityList) {
                    if (p != null) {
                        if (p.isInvisible() && p != mc.thePlayer) {
                            mc.theWorld.removeEntity(p);
                        }
                    }
                }
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    public static boolean isBot(EntityPlayer player) {
        Minecraft mc = Minecraft.getMinecraft();

        if (!Client.getModuleByName("antibot").isToggled()) {
            return false;
        }
        if (player.getGameProfile() == null) {
            return true;
        }
        NetworkPlayerInfo npi = mc.getNetHandler().getPlayerInfo(player.getGameProfile().getId());
        return (npi == null || npi.getGameProfile() == null || player.ticksExisted < ticks.getValue() || npi.getResponseTime() != 1);
    }
}
