package com.ullarah.urocket.recipe;

import com.ullarah.ulib.function.NewRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class RocketEnhance implements NewRecipe {

    private final String enhancementName;
    private final Material enhancementMaterial;

    public RocketEnhance(String name, Material material) {

        enhancementName = name;
        enhancementMaterial = material;

    }

    public static ItemStack enhancement(String name) {

        ItemStack enhancement = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta enhancementMeta = enhancement.getItemMeta();

        enhancementMeta.setDisplayName(ChatColor.AQUA + "Rocket Boot Enhancement");

        ArrayList<String> enhancementLore = new ArrayList<>();
        enhancementLore.add(0, name);

        enhancementMeta.setLore(enhancementLore);

        enhancementMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        enhancement.setItemMeta(enhancementMeta);

        enhancement.addUnsafeEnchantment(Enchantment.LUCK, 1);

        return enhancement;

    }

    public ShapedRecipe recipe() {

        ShapedRecipe enhanceRecipe = new ShapedRecipe(enhancement(enhancementName));
        enhanceRecipe.shape("EEE", "NRN");

        enhanceRecipe.setIngredient('E', enhancementMaterial);
        enhanceRecipe.setIngredient('N', Material.NETHER_STAR);
        enhanceRecipe.setIngredient('R', Material.REDSTONE_BLOCK);

        return enhanceRecipe;

    }

}
