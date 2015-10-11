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

public class RocketBooster implements NewRecipe {

    private final String boosterLevel;
    private final Material boosterMaterial;

    public RocketBooster(String level, Material material) {

        boosterLevel = level;
        boosterMaterial = material;

    }

    public static ItemStack booster(String level) {

        ItemStack booster = new ItemStack(Material.TNT, 1);
        ItemMeta boosterMeta = booster.getItemMeta();

        boosterMeta.setDisplayName(ChatColor.RED + "Rocket Booster");
        boosterMeta.setLore(Collections.singletonList(ChatColor.YELLOW + "Rocket Level " + level.toUpperCase()));

        boosterMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        booster.setItemMeta(boosterMeta);

        booster.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 1);

        return booster;

    }

    public ShapedRecipe recipe() {

        ShapedRecipe boosterRecipe = new ShapedRecipe(booster(boosterLevel));
        boosterRecipe.shape("E E", "BRB", "TTT");

        boosterRecipe.setIngredient('B', Material.BLAZE_POWDER);
        boosterRecipe.setIngredient('E', boosterMaterial);
        boosterRecipe.setIngredient('R', Material.REDSTONE_BLOCK);
        boosterRecipe.setIngredient('T', Material.TNT);

        return boosterRecipe;

    }

}
