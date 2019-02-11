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

import java.util.ArrayList;

public class RocketEnhance implements NewRecipe {

    private final String enhancementName;
    private final String enhancementNameClean;
    private final Material enhancementMaterial;

    public RocketEnhance(String name, Material material) {

        enhancementName = name;
        enhancementNameClean = ChatColor.stripColor(name).replaceAll("[^A-Za-z0-9]", "");
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

        NamespacedKey key = new NamespacedKey(RocketInit.getPlugin(), "rocket.rocketenhance."+enhancementNameClean);
        ShapedRecipe enhanceRecipe = new ShapedRecipe(key, enhancement(enhancementName));
        enhanceRecipe.shape("EEE", "NRN");

        enhanceRecipe.setIngredient('E', enhancementMaterial);
        enhanceRecipe.setIngredient('N', Material.NETHER_STAR);
        enhanceRecipe.setIngredient('R', Material.REDSTONE_BLOCK);

        return enhanceRecipe;

    }

}
