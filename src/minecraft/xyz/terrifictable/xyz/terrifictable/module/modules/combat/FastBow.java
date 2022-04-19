package xyz.terrifictable.module.modules.combat;

import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.lwjgl.input.Keyboard;
import xyz.terrifictable.Client;
import xyz.terrifictable.module.Module;

public class FastBow extends Module {
    public FastBow() {
        super("FastBow", Keyboard.KEY_R, Category.COMBAT);
    }

    public void onUpdate() {
        if (!this.isToggled()) return;

        if (mc.thePlayer.getHealth() > 0 && (mc.thePlayer.onGround || Client.getModuleByName("fly").isToggled() || mc.thePlayer.capabilities.isCreativeMode) && mc.thePlayer.inventory.getCurrentItem() != null && mc.thePlayer.isUsingItem() && (mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBow)) {
            mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
            mc.thePlayer.inventory.getCurrentItem().getItem().onItemRightClick(mc.thePlayer.inventory.getCurrentItem(), mc.theWorld, mc.thePlayer);

            for (int i=0; i < 20; i++)
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));

            mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
            mc.thePlayer.inventory.getCurrentItem().getItem().onPlayerStoppedUsing(mc.thePlayer.inventory.getCurrentItem(), mc.theWorld, mc.thePlayer, 10);
        }
        super.onUpdate();
    }
}
