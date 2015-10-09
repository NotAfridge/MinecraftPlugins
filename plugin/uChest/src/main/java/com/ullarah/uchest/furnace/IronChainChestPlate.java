package com.ullarah.uchest.furnace;

import com.ullarah.ulib.function.NewFurnace;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;

public class IronChainChestPlate implements NewFurnace {

    public static ItemStack result() {

        return new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);

    }

    public FurnaceRecipe furnace() {

        return new FurnaceRecipe(result(), Material.IRON_CHESTPLATE);

    }

}
