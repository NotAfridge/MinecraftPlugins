package com.ullarah.urocket.event;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class CraftStandard implements Listener {

    @EventHandler
    public void craftRocketBoots(PrepareItemCraftEvent event) {

        if (event.getInventory().getType() == InventoryType.WORKBENCH) {

            ItemStack[] getSlot = event.getInventory().getMatrix();

            boolean hasVariants = false;
            boolean hasBoosters = false;
            boolean hasControls = false;
            boolean isBoosterX = false;

            String boosterType = null;
            String variantType = null;

            ItemStack rocketVariant = getSlot[7];

            ItemStack[] rocketControls = new ItemStack[]{getSlot[0], getSlot[2]};
            ItemStack[] rocketBoosters = new ItemStack[]{getSlot[6], getSlot[8]};

            for (ItemStack control : rocketControls)
                if (control.hasItemMeta()) if (control.getItemMeta().hasDisplayName())
                    if (control.getItemMeta().getDisplayName().equals(ChatColor.RED + "Rocket Control"))
                        hasControls = true;

            for (ItemStack booster : rocketBoosters)
                if (booster.hasItemMeta()) if (booster.getItemMeta().hasDisplayName())
                    if (booster.getItemMeta().getDisplayName().equals(ChatColor.RED + "Rocket Booster")) {
                        String boosterMeta = booster.getItemMeta().getLore().get(0);
                        if (boosterMeta.matches(ChatColor.YELLOW + "Rocket Level I{0,3}V?X?")) {
                            boosterType = boosterMeta;
                            hasBoosters = true;
                        }
                        if (boosterMeta.matches(ChatColor.YELLOW + "Rocket Level X")) isBoosterX = true;
                    }

            if (rocketVariant.hasItemMeta()) if (rocketVariant.getItemMeta().hasDisplayName())
                if (rocketVariant.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Variant Booster")) {
                    variantType = rocketVariant.getItemMeta().getLore().get(0);
                    hasVariants = true;
                }

            if (hasControls && hasBoosters) {

                ItemStack boots = new ItemStack(Material.IRON_BOOTS, 1);
                ItemMeta bootMeta = boots.getItemMeta();

                bootMeta.setDisplayName(ChatColor.RED + "Rocket Boots");
                bootMeta.setLore(hasVariants ? Arrays.asList(boosterType, variantType) : Arrays.asList(boosterType));
                bootMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

                boots.setItemMeta(bootMeta);
                boots.addEnchantment(Enchantment.PROTECTION_FALL, 3);

                event.getInventory().setResult(hasVariants && isBoosterX ? null : boots);

            }

        }

    }

}
