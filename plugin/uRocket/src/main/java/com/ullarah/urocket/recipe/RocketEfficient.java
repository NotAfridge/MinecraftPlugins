package com.ullarah.urocket.recipe;

import com.ullarah.ulib.function.NewRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class RocketEfficient implements NewRecipe {

    public static ItemStack efficient() {

        ItemStack efficient = new ItemStack(Material.NETHER_STAR, 1);

        ItemMeta efficientMeta = efficient.getItemMeta();
        efficientMeta.setDisplayName(ChatColor.RED + "Fuel Efficient Enhancement");

        efficientMeta.setLore(Arrays.asList(
                        ChatColor.YELLOW + "Stretch your rocket fuel further!",
                        "" + ChatColor.WHITE + ChatColor.ITALIC + "Will only affect XP based fuels!")
        );

        efficient.setItemMeta(efficientMeta);

        return efficient;

    }

    public ShapedRecipe recipe() {

        ShapedRecipe efficientRecipe = new ShapedRecipe(efficient());
        efficientRecipe.shape("S S", "NRN");

        efficientRecipe.setIngredient('N', Material.NETHER_STAR);
        efficientRecipe.setIngredient('R', Material.REDSTONE_BLOCK);
        efficientRecipe.setIngredient('S', Material.SPONGE);

        return efficientRecipe;

    }

}
