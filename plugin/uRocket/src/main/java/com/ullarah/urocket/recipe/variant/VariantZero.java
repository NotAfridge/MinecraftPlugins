package com.ullarah.urocket.recipe.variant;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class VariantZero {

    public static ItemStack variant() {

        ItemStack variant = new ItemStack(Material.NOTE_BLOCK, 1);
        ItemMeta variantMeta = variant.getItemMeta();

        variantMeta.setDisplayName(ChatColor.AQUA + "Variant Booster");
        variantMeta.setLore(Arrays.asList(
                ChatColor.YELLOW + "Patient Zero"));

        variantMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        variant.setItemMeta(variantMeta);

        variant.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 1);

        return variant;

    }

    public ShapedRecipe recipe() {

        ShapedRecipe variantRecipe = new ShapedRecipe(variant());
        variantRecipe.shape(" C ", "ERE", "BTB");

        variantRecipe.setIngredient('B', Material.BLAZE_POWDER);
        variantRecipe.setIngredient('C', Material.ENCHANTMENT_TABLE);
        variantRecipe.setIngredient('E', Material.EXP_BOTTLE);
        variantRecipe.setIngredient('R', Material.REDSTONE_BLOCK);
        variantRecipe.setIngredient('T', Material.TNT);

        return variantRecipe;

    }

}
