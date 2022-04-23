package xyz.terrifictable.module.modules.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import xyz.terrifictable.Client;
import xyz.terrifictable.events.Event;
import xyz.terrifictable.module.Module;
import xyz.terrifictable.module.modules.combat.Killaura;
import xyz.terrifictable.setting.settings.BooleanSetting;
import xyz.terrifictable.setting.settings.NumberSetting;
import xyz.terrifictable.util.Timer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class InventoryManager extends Module {
    public Minecraft mc = Minecraft.getMinecraft();
    boolean invcleaner;
    boolean hasSet = false;
    private double delay;
    private Timer timer;
    private Timer updateTimer;
    boolean dropping = false;
    public int weaponSlot = 1;

    private NumberSetting minDelay = new NumberSetting("MinDelay", 50, 0, 250, 5);
    private NumberSetting maxDelay = new NumberSetting("MaxDelay", 100, 0, 250, 5);
    private NumberSetting blockCap = new NumberSetting("BlockCap", 128, 0, 256, 1);
    private BooleanSetting archeryItems = new BooleanSetting("Archery", true);
    private BooleanSetting food = new BooleanSetting("Food", true);
    private BooleanSetting invCleaner = new BooleanSetting("InvCleaner", true);
    private BooleanSetting openInv = new BooleanSetting("OpenInv", false);
    private NumberSetting swordSlot = new NumberSetting("SwordSlot", 1, 0, 9, 1);
    private BooleanSetting keepSword = new BooleanSetting("KeepSword", true);
    private BooleanSetting keepPickaxe = new BooleanSetting("KeepPickaxe", true);
    private BooleanSetting keepAxe = new BooleanSetting("KeepAxe", true);
    private BooleanSetting keepShovel = new BooleanSetting("KeepShovel", true);

    public InventoryManager() {
        super("InvManager", 0, Category.PLAYER);
        addSettings(minDelay, maxDelay, blockCap, archeryItems, food, invCleaner, openInv, swordSlot, keepSword, keepPickaxe, keepAxe, keepShovel);
        this.timer = new Timer();
        updateTimer = new Timer();
    }

    public void onEvent(Event event) {
        double min = minDelay.getValue();
        double max = maxDelay.getValue();
        if (updateTimer.hasTimeElapsed((long) Math.min(min, max), true)) {
            if (min > max || min == max) {
                max = min * 1.1;
            }
            delay = ThreadLocalRandom.current().nextDouble(min, max);
            hasSet = true;
        }
        if (!hasSet) {
            return;
        }
        int cap = (int) blockCap.getValue();
        boolean openinv = openInv.isEnabled();
        boolean foodOption = food.isEnabled();
        invcleaner = invCleaner.isEnabled();
        boolean archery = archeryItems.isEnabled();
        if (openinv && !(mc.currentScreen instanceof GuiInventory)) {
            return;
        }
        weaponSlot = (int) swordSlot.getValue();
        if (weaponSlot == 0 || weaponSlot > 9) {
            weaponSlot = 69;
        }
        weaponSlot--;
        if (event.isPre()) {
            Module ka = Client.getModuleByName("killaura");
            if (Killaura.target != null && ka.isToggled())
                return;
            if (mc.thePlayer != null && !dropping
                    && (this.mc.currentScreen == null || this.mc.currentScreen instanceof GuiInventory)
                    && this.timer.hasTimeElapsed((long) delay, true)) {
                for (int i = 9; i < 45; ++i) {
                    if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                        final ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                        if (this.isBad(is, i) && !(is.getItem() instanceof ItemArmor)
                                && is != mc.thePlayer.getCurrentEquippedItem()) {
                            if (invcleaner) {
                                this.mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, i,
                                        0, 0, mc.thePlayer);
                                this.dropping = true;
                                this.timer.reset();
                            }
                            break;
                        } else {
                            if (weaponSlot < 10 && is.getItem() instanceof ItemSword && isBestWeapon(is)
                                    && 45 - i - 9 != weaponSlot && !dropping) {
                                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, i, weaponSlot,
                                        2, mc.thePlayer);
                                timer.reset();
                            }
                        }
                    }
                }
            } else if (dropping && this.timer.hasTimeElapsed((long) (delay / 2), true)) {
                this.mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, -999, 0, 0,
                        mc.thePlayer);
                timer.reset();
                dropping = false;
            }
        }
        if (!invcleaner) {
            dropping = false;
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.dropping = false;
    }

    private ItemStack bestSword() {
        ItemStack best = null;
        float swordDamage = 0.0f;
        for (int i = 9; i < 45; ++i) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (is.getItem() instanceof ItemSword) {
                    final float swordD = this.getItemDamage(is);
                    if (swordD > swordDamage) {
                        swordDamage = swordD;
                        best = is;
                    }
                }
            }
        }
        return best;
    }

    private boolean isBad(final ItemStack stack, int slot) {
        boolean swords = keepSword.isEnabled();
        boolean axe = keepAxe.isEnabled();
        boolean pickaxe = keepPickaxe.isEnabled();
        boolean shovel = keepShovel.isEnabled();
        if (stack.getDisplayName().toLowerCase().contains("(right click)")) {
            return false;
        }
        if (stack.getDisplayName().toLowerCase().contains("\u00A7k||")) {
            return false;
        }
        if (stack.getItem() instanceof ItemBlock
                && (getBlockCount() > blockCap.getValue()/*
         * || Scaffold.getBlacklistedBlocks().contains(((ItemBlock)stack.getItem()).
         * getBlock())
         */)) {
            return true;
        }
        if ((slot == weaponSlot && isBestWeapon(mc.thePlayer.inventoryContainer.getSlot(weaponSlot).getStack()))) {
            return false;
        }
        if (stack.getItem() instanceof ItemSword && !swords) {
            return true;
        } else if (swords && stack.getItem() instanceof ItemSword && !isBestWeapon(stack)) {
            return true;
        }
        if (stack.getItem() instanceof ItemPickaxe && !pickaxe) {
            return true;
        } else if (pickaxe && stack.getItem() instanceof ItemPickaxe && !isBestPickaxe(stack)) {
            return true;
        }
        if (stack.getItem() instanceof ItemAxe && !axe) {
            return true;
        } else if (axe && stack.getItem() instanceof ItemAxe && !isBestAxe(stack)) {
            return true;
        }
        if (stack.getItem().getUnlocalizedName().contains("shovel") && !shovel) {
            return true;
        } else if (shovel && stack.getItem().getUnlocalizedName().contains("shovel") && !isBestShovel(stack)) {
            return true;
        }
        if (stack.getItem() instanceof ItemSword && weaponSlot > 10) {
            return true;
        }
        if (stack.getItem() instanceof ItemPotion) {
            if (isBadPotion(stack)) {
                return true;
            }
        }
        if (stack.getItem() instanceof ItemFood && food.isEnabled()
                && !(stack.getItem() instanceof ItemAppleGold)) {
            return true;
        }
        if (stack.getItem() instanceof ItemHoe || stack.getItem() instanceof ItemArmor) {
            return true;
        }
        if ((stack.getItem() instanceof ItemBow || stack.getItem().getUnlocalizedName().contains("arrow"))
                && archeryItems.isEnabled()) {
            return true;
        }
        if (((stack.getItem().getUnlocalizedName().contains("tnt"))
                || (stack.getItem().getUnlocalizedName().contains("stick"))
                || (stack.getItem().getUnlocalizedName().contains("egg"))
                || (stack.getItem().getUnlocalizedName().contains("string"))
                || (stack.getItem().getUnlocalizedName().contains("cake"))
                || (stack.getItem().getUnlocalizedName().contains("mushroom"))
                || (stack.getItem().getUnlocalizedName().contains("flint"))
                || (stack.getItem().getUnlocalizedName().contains("compass"))
                || (stack.getItem().getUnlocalizedName().contains("dyePowder"))
                || (stack.getItem().getUnlocalizedName().contains("feather"))
                || (stack.getItem().getUnlocalizedName().contains("bucket"))
                || (stack.getItem().getUnlocalizedName().contains("chest")
                && !stack.getDisplayName().toLowerCase().contains("collect"))
                || (stack.getItem().getUnlocalizedName().contains("snow"))
                || (stack.getItem().getUnlocalizedName().contains("fish"))
                || (stack.getItem().getUnlocalizedName().contains("enchant"))
                || (stack.getItem().getUnlocalizedName().contains("exp"))
                || (stack.getItem().getUnlocalizedName().contains("shears"))
                || (stack.getItem().getUnlocalizedName().contains("anvil"))
                || (stack.getItem().getUnlocalizedName().contains("torch"))
                || (stack.getItem().getUnlocalizedName().contains("seeds"))
                || (stack.getItem().getUnlocalizedName().contains("leather"))
                || (stack.getItem().getUnlocalizedName().contains("reeds"))
                || (stack.getItem().getUnlocalizedName().contains("skull"))
                || (stack.getItem().getUnlocalizedName().contains("record"))
                || (stack.getItem().getUnlocalizedName().contains("snowball"))
                || (stack.getItem() instanceof ItemGlassBottle)
                || (stack.getItem().getUnlocalizedName().contains("piston")))) {
            return true;
        }
        if (isDuplicate(stack, slot)) {
            return true;
        }
        return false;
    }

    private List<ItemStack> getBest() {
        final List<ItemStack> best = new ArrayList<ItemStack>();
        for (int i = 0; i < 4; ++i) {
            ItemStack armorStack = null;
            for (final ItemStack itemStack : mc.thePlayer.inventory.armorInventory) {
                if (itemStack != null) {
                    if (itemStack.getItem() instanceof ItemArmor) {
                        final ItemArmor stackArmor = (ItemArmor) itemStack.getItem();
                        if (stackArmor.armorType == i) {
                            armorStack = itemStack;
                        }
                    }
                }
            }
            final double reduction = (armorStack == null) ? -1.0 : this.getArmorStrength(armorStack);
            ItemStack slotStack = this.findBestArmor(i);
            if (slotStack != null && this.getArmorStrength(slotStack) <= reduction) {
                slotStack = armorStack;
            }
            if (slotStack != null) {
                best.add(slotStack);
            }
        }
        return best;
    }

    public boolean isDuplicate(ItemStack stack, int slot) {
        for (int i = 9; i < 45; ++i) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (is != stack && slot != i && is.getUnlocalizedName().equalsIgnoreCase(stack.getUnlocalizedName())
                        && !(is.getItem() instanceof ItemPotion) && !(is.getItem() instanceof ItemBlock)) {
                    if (is.getItem() instanceof ItemSword) {
                        if (this.getDamage(is) != this.getDamage(stack)) {
                        } else {
                            return true;
                        }
                    } else if (is.getItem() instanceof ItemTool) {
                        if (this.getToolEffect(is) != this.getToolEffect(stack)) {
                        } else {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private int getBlockCount() {
        int blockCount = 0;
        for (int i = 0; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                Item item = is.getItem();
                if (is.getItem() instanceof ItemBlock) {
                    blockCount += is.stackSize;
                }
            }
        }
        return blockCount;
    }

    private ItemStack findBestArmor(final int itemSlot) {
        ItemStack i = null;
        double maxReduction = 0.0;
        for (int slot = 0; slot < 36; ++slot) {
            final ItemStack itemStack = mc.thePlayer.inventory.mainInventory[slot];
            if (itemStack != null) {
                final double reduction = this.getArmorStrength(itemStack);
                if (reduction != -1.0) {
                    final ItemArmor itemArmor = (ItemArmor) itemStack.getItem();
                    if (itemArmor.armorType == itemSlot) {
                        if (reduction >= maxReduction) {
                            maxReduction = reduction;
                            i = itemStack;
                        }
                    }
                }
            }
        }
        return i;
    }

    private double getArmorStrength(final ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof ItemArmor)) {
            return -1.0;
        }
        float damageReduction = (float) ((ItemArmor) itemStack.getItem()).damageReduceAmount;
        final Map enchantments = EnchantmentHelper.getEnchantments(itemStack);
        if (enchantments.containsKey(Enchantment.protection.effectId)) {
            final int level = (int) enchantments.get(Enchantment.protection.effectId);
            damageReduction += Enchantment.protection.calcModifierDamage(level, DamageSource.generic);
        }
        return damageReduction;
    }

    private boolean isBadPotion(final ItemStack stack) {
        if (stack != null && stack.getItem() instanceof ItemPotion) {
            final ItemPotion potion = (ItemPotion) stack.getItem();
            if (ItemPotion.isSplash(stack.getItemDamage())) {
                for (final Object o : potion.getEffects(stack)) {
                    final PotionEffect effect = (PotionEffect) o;
                    if (effect.getPotionID() == Potion.poison.getId() || effect.getPotionID() == Potion.harm.getId()
                            || effect.getPotionID() == Potion.moveSlowdown.getId()
                            || effect.getPotionID() == Potion.weakness.getId()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private float getItemDamage(final ItemStack itemStack) {
        float damage = ((ItemSword) itemStack.getItem()).getDamageVsEntity();
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack) * 1.25f;
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemStack) * 0.01f;
        return damage;
    }

    private boolean isBestPickaxe(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof ItemPickaxe))
            return false;
        float value = getToolEffect(stack);
        for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (getToolEffect(is) > value && is.getItem() instanceof ItemPickaxe) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isBestShovel(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof ItemSpade))
            return false;
        float value = getToolEffect(stack);
        for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (getToolEffect(is) > value && is.getItem() instanceof ItemSpade) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isBestAxe(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof ItemAxe))
            return false;
        float value = getToolEffect(stack);
        for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (getToolEffect(is) > value && is.getItem() instanceof ItemAxe && !isBestWeapon(stack)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isBestWeapon(ItemStack stack) {
        float damage = getDamage(stack);
        for (int i = 0; i < 36; i++) {
            if (mc.thePlayer.inventory.mainInventory[i] != null) {
                ItemStack is = mc.thePlayer.inventory.mainInventory[i];
                if (getDamage(is) > damage && (is.getItem() instanceof ItemSword))
                    return false;
            }
        }
        return true;
    }

    private float getDamage(ItemStack stack) {
        float damage = 0;
        Item item = stack.getItem();
        if (item instanceof ItemTool) {
            ItemTool tool = (ItemTool) item;
            damage += tool.getMaxDamage();
        }
        if (item instanceof ItemSword) {
            ItemSword sword = (ItemSword) item;
            damage += sword.getMaxDamage();
        }
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25f
                + EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) * 0.01f;
        return damage;
    }

    private float getToolEffect(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof ItemTool))
            return 0;
        String name = item.getUnlocalizedName();
        ItemTool tool = (ItemTool) item;
        float value = 1;
        if (item instanceof ItemPickaxe) {
            value = tool.getStrVsBlock(stack, Blocks.stone);
            if (name.toLowerCase().contains("gold")) {
                value -= 5;
            }
        } else if (item instanceof ItemSpade) {
            value = tool.getStrVsBlock(stack, Blocks.dirt);
            if (name.toLowerCase().contains("gold")) {
                value -= 5;
            }
        } else if (item instanceof ItemAxe) {
            value = tool.getStrVsBlock(stack, Blocks.log);
            if (name.toLowerCase().contains("gold")) {
                value -= 5;
            }
        } else
            return 1f;
        value += EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack) * 0.0075D;
        value += EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 100d;
        return value;
    }
}
