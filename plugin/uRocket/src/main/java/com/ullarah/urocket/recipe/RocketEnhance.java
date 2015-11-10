package com.ullarah.urocket.recipe;

import com.ullarah.ulib.function.NewRecipe;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

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

        enhancementMeta.setDisplayName(name);
        enhancementMeta.setLore(lore);

        enhancement.setItemMeta(enhancementMeta);

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
