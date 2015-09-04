package com.ullarah.uchest.furnace;

import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;

public class IronChainHelmet {

    public static ItemStack result() {

        return new ItemStack(Material.CHAINMAIL_HELMET, 1);

    }

    public FurnaceRecipe furnace() {

        return new FurnaceRecipe(result(), Material.IRON_HELMET);

    }

}
