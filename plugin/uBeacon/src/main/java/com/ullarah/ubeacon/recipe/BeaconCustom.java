package com.ullarah.ubeacon.recipe;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class BeaconCustom {

    public static ItemStack custom() {

        ItemStack beacon = new ItemStack(Material.BEACON, 1);

        ItemMeta beaconMeta = beacon.getItemMeta();

        beaconMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Custom Beacon");
        beaconMeta.setLore(Arrays.asList(
                ChatColor.YELLOW + "Doesn't look like much yet...",
                ChatColor.GOLD + "Place it down, open it like a chest!"));

        beaconMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        beacon.setItemMeta(beaconMeta);

        beacon.addUnsafeEnchantment(Enchantment.LUCK, 1);

        return beacon;

    }

    public ShapedRecipe recipe() {

        ShapedRecipe beaconRecipe = new ShapedRecipe(custom());

        beaconRecipe.shape("SCS", " H ", "RBR");

        beaconRecipe.setIngredient('B', Material.BEACON);
        beaconRecipe.setIngredient('C', Material.CHEST);
        beaconRecipe.setIngredient('H', Material.HOPPER);
        beaconRecipe.setIngredient('R', Material.REDSTONE_BLOCK);
        beaconRecipe.setIngredient('S', Material.STAINED_GLASS);

        return beaconRecipe;

    }

}
