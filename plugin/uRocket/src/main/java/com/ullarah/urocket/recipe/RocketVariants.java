package com.ullarah.urocket.recipe;

import com.ullarah.ulib.function.NewRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class RocketVariants implements NewRecipe {

    private final String variantName;

    private final Material variantTopMaterial;
    private final Material variantSideMaterial;
    private final Material variantBottomMaterial;

    public RocketVariants(String name, Material[] materials) {

        variantName = name;

        variantTopMaterial = materials[0];
        variantSideMaterial = materials[1];
        variantBottomMaterial = materials[2];

    }

    public static ItemStack variant(String name) {

        ItemStack variant = new ItemStack(Material.NOTE_BLOCK, 1);
        ItemMeta variantMeta = variant.getItemMeta();

        variantMeta.setDisplayName(ChatColor.AQUA + "Variant Booster");
        variantMeta.setLore(Collections.singletonList(name));

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
