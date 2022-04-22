package xyz.terrifictable.util.cosmetics;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;

public class CapeChecker {

    public static boolean ownsCape(AbstractClientPlayer playerIn) {
        if (playerIn.getName().equals(Minecraft.getMinecraft().getSession().getUsername())) {
            // TODO: UUID/NAME SYSTEM
            return true;
        }
        return false;
    }
}
