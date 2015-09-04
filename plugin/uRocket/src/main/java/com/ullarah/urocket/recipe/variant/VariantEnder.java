package com.ullarah.urocket.recipe.variant;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class VariantEnder {

    public static ItemStack variant() {

        ItemStack variant = new ItemStack(Material.NOTE_BLOCK, 1);

        ItemMeta variantMeta = variant.getItemMeta();
        variantMeta.setDisplayName(ChatColor.AQUA + "Variant Booster");
        variantMeta.setLore(Arrays.asList(
                ChatColor.DARK_AQUA + "Random Teleportation"));

        variantMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        variant.setItemMeta(variantMeta);

        variant.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 1);

        return variant;

    }

    public ShapedRecipe recipe() {

        ShapedRecipe variantRecipe = new ShapedRecipe(variant());
        variantRecipe.shape("E E", "IRI", "STS");

        variantRecipe.setIngredient('E', Material.EYE_OF_ENDER);
        variantRecipe.setIngredient('I', Material.IRON_BLOCK);
        variantRecipe.setIngredient('R', Material.REDSTONE_BLOCK);
        variantRecipe.setIngredient('S', Material.ENDER_STONE);
        variantRecipe.setIngredient('T', Material.TNT);

        return variantRecipe;

    }

}
