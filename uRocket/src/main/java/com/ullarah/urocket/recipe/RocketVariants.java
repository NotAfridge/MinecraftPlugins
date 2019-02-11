package com.ullarah.urocket.recipe;

import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.function.NewRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class RocketVariants implements NewRecipe {

    private final String variantName;
    private final String variantNameClean;

    private final Material variantTopMaterial;
    private final Material variantSideMaterial;
    private final Material variantBottomMaterial;

    public RocketVariants(String name, Material[] materials) {

        variantName = name;
        variantNameClean = ChatColor.stripColor(name).replaceAll("[^A-Za-z0-9]", "");

        variantTopMaterial = materials[0];
        variantSideMaterial = materials[1];
        variantBottomMaterial = materials[2];

    }

    public static ItemStack variant(String name) {

        ItemStack variant = new ItemStack(Material.NOTE_BLOCK, 1);
        ItemMeta variantMeta = variant.getItemMeta();

        variantMeta.setDisplayName(ChatColor.AQUA + "Rocket Boot Variant");
        variantMeta.setLore(Collections.singletonList(name));

        variantMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        variant.setItemMeta(variantMeta);

        variant.addUnsafeEnchantment(Enchantment.LUCK, 1);

        return variant;

    }

    public ShapedRecipe recipe() {

        NamespacedKey key = new NamespacedKey(RocketInit.getPlugin(), "rocket.variant."+variantNameClean);
        ShapedRecipe variantRecipe = new ShapedRecipe(key, variant(variantName));
        variantRecipe.shape("T T", "SRS", "BNB");

        variantRecipe.setIngredient('T', variantTopMaterial);
        variantRecipe.setIngredient('S', variantSideMaterial);
        variantRecipe.setIngredient('B', variantBottomMaterial);

        variantRecipe.setIngredient('R', Material.REDSTONE_BLOCK);
        variantRecipe.setIngredient('N', Material.TNT);

        return variantRecipe;

    }

}
