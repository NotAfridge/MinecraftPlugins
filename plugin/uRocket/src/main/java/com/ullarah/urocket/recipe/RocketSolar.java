package com.ullarah.urocket.recipe;

import com.ullarah.ulib.function.NewRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class RocketSolar implements NewRecipe {

    public static ItemStack solar() {

        ItemStack solar = new ItemStack(Material.NETHER_STAR, 1);

        ItemMeta solarMeta = solar.getItemMeta();
        solarMeta.setDisplayName(ChatColor.RED + "Solar Enhancement");
        solarMeta.setLore(Arrays.asList(
                        ChatColor.YELLOW + "Fly until the sun goes down!",
                        "" + ChatColor.WHITE + ChatColor.ITALIC + "Will only affect XP based fuels!")
        );

        solar.setItemMeta(solarMeta);

        return solar;

    }

    public ShapedRecipe recipe() {

        ShapedRecipe solarRecipe = new ShapedRecipe(solar());
        solarRecipe.shape("D D", "NRN");

        solarRecipe.setIngredient('D', Material.DAYLIGHT_DETECTOR);
        solarRecipe.setIngredient('N', Material.NETHER_STAR);
        solarRecipe.setIngredient('R', Material.REDSTONE_BLOCK);

        return solarRecipe;

    }

}
