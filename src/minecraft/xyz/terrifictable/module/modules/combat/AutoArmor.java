package xyz.terrifictable.module.modules.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import xyz.terrifictable.events.Event;
import xyz.terrifictable.module.Module;
import xyz.terrifictable.setting.settings.BooleanSetting;
import xyz.terrifictable.setting.settings.NumberSetting;
import xyz.terrifictable.util.Timer;

public class AutoArmor extends Module {
    public Minecraft mc = Minecraft.getMinecraft();
    private Timer timer;
    private Timer dropTimer;
    private boolean dropping;
    public NumberSetting delay = new NumberSetting("Delay", 3.0D, 0.0D, 10.0D, 0.5D);
    public BooleanSetting drop = new BooleanSetting("Drop", true);
    public BooleanSetting openInv = new BooleanSetting("Open Inventory", false);

    public AutoArmor() {
        super("AutoArmor", 0, Category.COMBAT);
        addSettings(openInv, drop, delay);
        this.timer = new Timer();
        this.dropTimer = new Timer();
    }

    public void onEvent(Event e) {
        String mode;
        if (!isToggled())
            return;
        if (openInv.isEnabled()) {
            mode = "OpenInv";
        } else {
            mode = "Basic";
        }
        long delayValue = (long) (delay.getValue() * 50L);
        if (mode.equalsIgnoreCase("OpenInv")
                && !(this.mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory))
            return;
        if (this.mc.currentScreen == null
                || this.mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory
                || this.mc.currentScreen instanceof net.minecraft.client.gui.GuiChat) {
            if (this.timer.hasTimeElapsed(delayValue, true))
                getBestArmor();
            if (!this.dropping) {
                if ((this.mc.currentScreen == null
                        || this.mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory
                        || this.mc.currentScreen instanceof net.minecraft.client.gui.GuiChat)
                        && this.timer.hasTimeElapsed(delayValue, true))
                    getBestArmor();
            } else if (this.dropTimer.hasTimeElapsed(delayValue, true)) {
                this.mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, -999, 0, 0, (EntityPlayer) mc.thePlayer);
                this.dropTimer.reset();
                this.dropping = false;
            }
        }
    }

    public void getBestArmor() {
        for (int type = 1; type < 5; type++) {
            if (mc.thePlayer.inventoryContainer.getSlot(4 + type).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(4 + type).getStack();
                if (isBestArmor(is, type))
                    continue;
                if (drop.isEnabled()) {
                    drop(4 + type);
                } else {
                    shiftClick(4 + type);
                }
            }
            for (int i = 9; i < 45; i++) {
                if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                    ItemStack is2 = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                    if (isBestArmor(is2, type) && getProtection(is2) > 0.0F) {
                        shiftClick(i);
                        this.timer.reset();
                        if (delay.getValue() > 0L)
                            return;
                    }
                }
            }
        }
        if (drop.isEnabled())
            for (int j = 9; j < 45; j++) {
                if (mc.thePlayer.inventoryContainer.getSlot(j).getHasStack()) {
                    ItemStack is = mc.thePlayer.inventoryContainer.getSlot(j).getStack();
                    if ((getProtection(is) > 0.0F || isDuplicate(is, j)) && !this.dropping && is.getItem() instanceof ItemArmor)
                        drop(j);
                }
            }
    }

    public boolean isDuplicate(ItemStack stack, int slot) {
        for (int i = 0; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (is != stack && slot != i && getProtection(stack) == getProtection(is)
                        && is.getItem() instanceof ItemArmor && !(is.getItem() instanceof net.minecraft.item.ItemPotion)
                        && !(is.getItem() instanceof net.minecraft.item.ItemBlock))
                    return true;
            }
        }
        return false;
    }

    public static boolean isBestArmor(ItemStack stack, int type) {
        float prot = getProtection(stack);
        String strType = "";
        if (type == 1) {
            strType = "helmet";
        } else if (type == 2) {
            strType = "chestplate";
        } else if (type == 3) {
            strType = "leggings";
        } else if (type == 4) {
            strType = "boots";
        }
        if (!stack.getUnlocalizedName().contains(strType))
            return false;
        for (int i = 5; i < 45; i++) {
            Minecraft mc = Minecraft.getMinecraft();
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (getProtection(is) > prot && is.getUnlocalizedName().contains(strType))
                    return false;
            }
        }
        return true;
    }

    public void shiftClick(int slot) {
        this.mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 0, 1,
                (EntityPlayer) mc.thePlayer);
    }

    public void drop(int slot) {
        if (drop.isEnabled()
                && this.dropTimer.hasTimeElapsed((long) (delay.getValue() * 50L), true)
                && !this.dropping) {
            this.mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 0, 0,
                    (EntityPlayer) mc.thePlayer);
            this.dropping = true;
            this.dropTimer.reset();
        }
    }

    public static float getProtection(ItemStack stack) {
        float prot = 0.0F;
        if (stack.getItem() instanceof ItemArmor) {
            ItemArmor armor = (ItemArmor) stack.getItem();
            prot += (float) (armor.damageReduceAmount + ((100 - armor.damageReduceAmount)
                    * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack)) * 0.0075D);
            prot += (float) (EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, stack)
                    / 100.0D);
            prot += (float) (EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, stack)
                    / 100.0D);
            prot += (float) (EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack) / 100.0D);
            prot += (float) (EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 50.0D);
            prot += (float) (EnchantmentHelper.getEnchantmentLevel(Enchantment.projectileProtection.effectId, stack)
                    / 100.0D);
        }
        return prot;
    }
}
