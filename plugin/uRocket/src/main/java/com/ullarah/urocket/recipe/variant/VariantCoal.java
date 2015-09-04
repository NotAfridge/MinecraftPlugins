package com.ullarah.urocket.recipe.variant;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class VariantCoal {

    public static ItemStack variant() {

        ItemStack variant = new ItemStack(Material.NOTE_BLOCK, 1);

        ItemMeta variantMeta = variant.getItemMeta();
        variantMeta.setDisplayName(ChatColor.AQUA + "Variant Booster");
        variantMeta.setLore(Arrays.asList(
                ChatColor.GRAY + "Coal Miner"));

        variantMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        variant.setItemMeta(variantMeta);

        variant.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 1);

        return variant;

    }

    public ShapedRecipe recipe() {

        ShapedRecipe variantRecipe = new ShapedRecipe(variant());
        variantRecipe.shape("N N", "CRC", "FTF");

        variantRecipe.setIngredient('C', Material.COAL_BLOCK);
        variantRecipe.setIngredient('F', Material.FURNACE);
        variantRecipe.setIngredient('N', Material.NETHERRACK);
        variantRecipe.setIngredient('R', Material.REDSTONE_BLOCK);
        variantRecipe.setIngredient('T', Material.TNT);

        return variantRecipe;

    }

}
