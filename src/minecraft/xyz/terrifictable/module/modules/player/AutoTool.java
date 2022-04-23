package xyz.terrifictable.module.modules.player;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Mouse;
import xyz.terrifictable.module.Module;

public class AutoTool extends Module {
    public Minecraft mc = Minecraft.getMinecraft();
    private int oldSlot = -1;
    private boolean wasBreaking = false;

    public AutoTool() {
        super("AutoTool", 0, Category.PLAYER);
    }

    public void onUpdate() {
        if (this.mc.currentScreen == null && mc.thePlayer != null && mc.theWorld != null
                && this.mc.objectMouseOver != null && this.mc.objectMouseOver.getBlockPos() != null
                && this.mc.objectMouseOver.entityHit == null && Mouse.isButtonDown(0)) {
            float bestSpeed = 1.0F;
            int bestSlot = -1;
            Block block = mc.theWorld.getBlockState(this.mc.objectMouseOver.getBlockPos()).getBlock();
            for (int k = 0; k < 9; k++) {
                ItemStack item = mc.thePlayer.inventory.getStackInSlot(k);
                if (item != null) {
                    float speed = item.getStrVsBlock(block);
                    if (speed > bestSpeed) {
                        bestSpeed = speed;
                        bestSlot = k;
                    }
                }
            }
            if (bestSlot != -1 && mc.thePlayer.inventory.currentItem != bestSlot) {
                mc.thePlayer.inventory.currentItem = bestSlot;
                this.wasBreaking = true;
            } else if (bestSlot == -1) {
                if (this.wasBreaking) {
                    mc.thePlayer.inventory.currentItem = this.oldSlot;
                    this.wasBreaking = false;
                }
                this.oldSlot = mc.thePlayer.inventory.currentItem;
            }
        } else if (mc.thePlayer != null && mc.theWorld != null) {
            if (this.wasBreaking) {
                mc.thePlayer.inventory.currentItem = this.oldSlot;
                this.wasBreaking = false;
            }
            this.oldSlot = mc.thePlayer.inventory.currentItem;
        }
    }
}
