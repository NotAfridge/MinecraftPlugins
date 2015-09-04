package com.ullarah.uchest.furnace;

import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;

public class IronChainChestPlate {

    public static ItemStack result() {

        return new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);

    }

    public FurnaceRecipe furnace() {

        return new FurnaceRecipe(result(), Material.IRON_CHESTPLATE);

    }

}
