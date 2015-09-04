package com.ullarah.uchest.furnace;

import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;

public class FlintSulphur {

    public static ItemStack result() {

        return new ItemStack(Material.SULPHUR, 1);

    }

    public FurnaceRecipe furnace() {

        return new FurnaceRecipe(result(), Material.FLINT);

    }

}
