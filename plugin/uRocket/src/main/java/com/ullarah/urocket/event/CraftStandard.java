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
import java.util.Collections;

public class CraftStandard implements Listener {

    @EventHandler
    public void craftRocketBoots(PrepareItemCraftEvent event) {

        if (event.getInventory().getType() == InventoryType.WORKBENCH) {

            ItemStack[] getSlot = event.getInventory().getMatrix();

            int bootType = 0;

            boolean hasBoosters = false;
            boolean hasControls = false;
            boolean hasMaterial = false;
            boolean isBoosterX = false;

            Material bootMaterial = null;
            boolean materialMatch = false;

            String boosterType = null;
            String variantType = null;

            String rocketRepair = "Self Repair";
            String rocketEfficient = "Fuel Efficient";
            String rocketSolar = "Solar Power";

            ItemStack rocketVariant = getSlot[7];
            ItemStack rocketEnhancement = getSlot[4];

            ItemStack[] rocketControls = new ItemStack[]{getSlot[0], getSlot[2]};
            ItemStack[] rocketMaterial = new ItemStack[]{getSlot[3], getSlot[5]};
            ItemStack[] rocketBoosters = new ItemStack[]{getSlot[6], getSlot[8]};

            for (ItemStack control : rocketControls)
                if (control.hasItemMeta()) if (control.getItemMeta().hasDisplayName())
                    hasControls = control.getItemMeta().getDisplayName().equals(ChatColor.RED + "Rocket Control");
            if (!getSlot[0].equals(getSlot[2])) hasControls = false;

            for (ItemStack booster : rocketBoosters)
                if (booster.hasItemMeta()) if (booster.getItemMeta().hasDisplayName())
                    hasBoosters = booster.getItemMeta().getDisplayName().equals(ChatColor.RED + "Rocket Booster");
            if (!getSlot[6].equals(getSlot[8])) hasBoosters = false;

            if (hasBoosters) {
                String boosterMeta = getSlot[6].getItemMeta().getLore().get(0);
                if (boosterMeta.matches(ChatColor.YELLOW + "Rocket Level I{0,3}V?X?")) boosterType = boosterMeta;
                if (boosterMeta.matches(ChatColor.YELLOW + "Rocket Level X")) isBoosterX = true;
            }

            for (ItemStack material : rocketMaterial)
                switch (material.getType()) {

                    case LEATHER:
                        bootMaterial = Material.LEATHER_BOOTS;
                        hasMaterial = true;
                        break;

                    case IRON_INGOT:
                        bootMaterial = Material.IRON_BOOTS;
                        hasMaterial = true;
                        break;

                    case GOLD_INGOT:
                        bootMaterial = Material.GOLD_BOOTS;
                        hasMaterial = true;
                        break;

                    case DIAMOND:
                        bootMaterial = Material.DIAMOND_BOOTS;
                        hasMaterial = true;
                        break;

                }

            if (getSlot[3].equals(getSlot[5])) materialMatch = true;

            if (rocketVariant.hasItemMeta()) if (rocketVariant.getItemMeta().hasDisplayName())
                if (rocketVariant.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Variant Booster")) {
                    variantType = rocketVariant.getItemMeta().getLore().get(0);
                    bootType += 8;
                }

            if (rocketEnhancement.hasItemMeta()) if (rocketEnhancement.getItemMeta().hasDisplayName()) {

                String enhancementName = rocketEnhancement.getItemMeta().getDisplayName();

                if (enhancementName.equals(ChatColor.RED + rocketRepair)) bootType += 16;
                if (enhancementName.equals(ChatColor.RED + rocketEfficient)) bootType += 32;
                if (enhancementName.equals(ChatColor.RED + rocketSolar)) bootType += 64;

            }

            if (hasControls && hasBoosters && hasMaterial && materialMatch) {

                ItemStack boots = new ItemStack(bootMaterial, 1);
                ItemMeta bootMeta = boots.getItemMeta();

                bootMeta.setDisplayName(ChatColor.RED + "Rocket Boots");

                switch (bootType) {

                    case 0:
                        bootMeta.setLore(Collections.singletonList(boosterType));
                        break;

                    case 8:
                        bootMeta.setLore(Arrays.asList(boosterType, variantType));
                        break;

                    case 16:
                        bootMeta.setLore(Arrays.asList(boosterType, ChatColor.AQUA + rocketRepair));
                        break;

                    case 24:
                        bootMeta.setLore(Arrays.asList(boosterType, variantType, ChatColor.AQUA + rocketRepair));
                        break;

                    case 32:
                        bootMeta.setLore(Arrays.asList(boosterType, ChatColor.AQUA + rocketEfficient));
                        break;

                    case 40:
                        bootMeta.setLore(Arrays.asList(boosterType, variantType, ChatColor.AQUA + rocketEfficient));
                        break;

                    case 64:
                        bootMeta.setLore(Arrays.asList(boosterType, ChatColor.AQUA + rocketSolar));
                        break;

                    case 72:
                        bootMeta.setLore(Arrays.asList(boosterType, variantType, ChatColor.AQUA + rocketSolar));
                        break;


                }

                bootMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

                boots.setItemMeta(bootMeta);
                boots.addEnchantment(Enchantment.PROTECTION_FALL, 4);

                event.getInventory().setResult((bootType == 8 || bootType == 24 || bootType == 40 || bootType == 72)
                        && isBoosterX ? null : boots);

            }

        }

    }

}
