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

import java.util.Collections;

public class RepairStation implements NewRecipe {

    public ItemStack station() {

        ItemStack station = new ItemStack(Material.BEACON, 1);

        ItemMeta stationMeta = station.getItemMeta();
        stationMeta.setDisplayName(ChatColor.RED + "Rocket Boot Repair Station");
        stationMeta.setLore(Collections.singletonList(ChatColor.YELLOW + "Place it on top of a Repair Tank!"));
        stationMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        station.setItemMeta(stationMeta);

        station.addUnsafeEnchantment(Enchantment.LUCK, 1);

        return station;

    }

    public ShapedRecipe recipe() {

        NamespacedKey key = new NamespacedKey(RocketInit.getPlugin(), "rocket.repairstation");
        ShapedRecipe bootRecipe = new ShapedRecipe(key, station());
        bootRecipe.shape("E", "B");

        bootRecipe.setIngredient('B', Material.BEACON);
        bootRecipe.setIngredient('E', Material.ENCHANTING_TABLE);

        return bootRecipe;

    }

}
