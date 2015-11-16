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
import java.util.List;

public class RocketEnhance implements NewRecipe {

    private final String enhancementName;
    private final Material enhancementMaterial;
    private final List<String> enhancementLore;

    public RocketEnhance(String name, Material material, List<String> lore) {

        enhancementName = name;
        enhancementMaterial = material;
        enhancementLore = lore;

    }

    public static ItemStack enhancement(String name, List<String> lore) {

        ItemStack enhancement = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta enhancementMeta = enhancement.getItemMeta();

        enhancementMeta.setDisplayName(ChatColor.AQUA + "Rocket Boot Enhancement");

        lore = new ArrayList<>(lore); // You would think that this is silly right?
        lore.add(0, name);            // Well it's not, try adding without creating a new ArrayList...

        enhancementMeta.setLore(lore);

        enhancementMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        enhancement.setItemMeta(enhancementMeta);

        enhancement.addUnsafeEnchantment(Enchantment.LUCK, 1);

        return enhancement;

    }

    public ShapedRecipe recipe() {

        ShapedRecipe enhanceRecipe = new ShapedRecipe(enhancement(enhancementName, enhancementLore));
        enhanceRecipe.shape("E E", "NRN");

        enhanceRecipe.setIngredient('E', enhancementMaterial);
        enhanceRecipe.setIngredient('N', Material.NETHER_STAR);
        enhanceRecipe.setIngredient('R', Material.REDSTONE_BLOCK);

        return enhanceRecipe;

    }

}
