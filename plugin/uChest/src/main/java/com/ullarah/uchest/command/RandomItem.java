package com.ullarah.uchest.command;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.ullarah.uchest.ChestInit.getChestRandomInventory;
import static com.ullarah.uchest.ChestInit.getPlugin;

public class RandomItem {

    public static void setRandomItem(Integer amount) {

        java.util.Random chestRandomItem = new java.util.Random();

        List<String> materialList = new ArrayList<>(
                getPlugin().getConfig().getConfigurationSection("materials").getKeys(false));

        try {
            for (int i = 0; i < amount; ++i) {

                getChestRandomInventory().setItem(chestRandomItem.nextInt(54),
                        new ItemStack(Material.getMaterial(
                                materialList.get(chestRandomItem.nextInt(materialList.size())))));

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

}
