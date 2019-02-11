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

import java.util.Arrays;

public class RepairStand implements NewRecipe {

    public ItemStack stand() {

        ItemStack stand = new ItemStack(Material.ARMOR_STAND, 1);

        ItemMeta standMeta = stand.getItemMeta();
        standMeta.setDisplayName(ChatColor.RED + "Rocket Boot Repair Stand");
        standMeta.setLore(Arrays.asList(
                ChatColor.YELLOW + "Auto-charge your Rocket Boots!",
                ChatColor.YELLOW + "Place this on top of your Rocket Station!")
        );

        standMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        stand.setItemMeta(standMeta);

        stand.addUnsafeEnchantment(Enchantment.LUCK, 1);

        return stand;

    }

    public ShapedRecipe recipe() {

        NamespacedKey key = new NamespacedKey(RocketInit.getPlugin(), "rocket.repairstand");
        ShapedRecipe standRecipe = new ShapedRecipe(key, stand());
        standRecipe.shape("A", "R");

        standRecipe.setIngredient('A', Material.ARMOR_STAND);
        standRecipe.setIngredient('R', Material.REDSTONE_BLOCK);

        return standRecipe;

    }

}
