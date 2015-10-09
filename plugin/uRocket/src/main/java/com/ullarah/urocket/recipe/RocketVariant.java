package com.ullarah.urocket.recipe;

import com.ullarah.ulib.function.NewRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class RocketVariant implements NewRecipe {

    private String variantName;

    private Material variantTopMaterial;
    private Material variantSideMaterial;
    private Material variantBottomMaterial;

    public RocketVariant(String name, Material top, Material side, Material bottom) {

        variantName = name;

        variantTopMaterial = top;
        variantSideMaterial = side;
        variantBottomMaterial = bottom;

    }

    public static ItemStack variant(String name) {

        ItemStack variant = new ItemStack(Material.NOTE_BLOCK, 1);
        ItemMeta variantMeta = variant.getItemMeta();

        variantMeta.setDisplayName(ChatColor.AQUA + "Variant Booster");
        variantMeta.setLore(Arrays.asList(name));

        variantMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        variant.setItemMeta(variantMeta);

        variant.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 1);

        return variant;

    }

    public ShapedRecipe recipe() {

        ShapedRecipe variantRecipe = new ShapedRecipe(variant(variantName));
        variantRecipe.shape("T T", "SRS", "BNB");

        variantRecipe.setIngredient('T', variantTopMaterial);
        variantRecipe.setIngredient('S', variantSideMaterial);
        variantRecipe.setIngredient('B', variantBottomMaterial);

        variantRecipe.setIngredient('R', Material.REDSTONE_BLOCK);
        variantRecipe.setIngredient('N', Material.TNT);

        return variantRecipe;

    }

}
