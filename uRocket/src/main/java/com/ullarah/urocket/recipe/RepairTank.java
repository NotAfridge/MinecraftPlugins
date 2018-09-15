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

public class RepairTank implements NewRecipe {

    public ItemStack tank() {

        ItemStack tank = new ItemStack(Material.FURNACE, 1);

        ItemMeta tankMeta = tank.getItemMeta();
        tankMeta.setDisplayName(ChatColor.RED + "Rocket Boot Repair Tank");
        tankMeta.setLore(Collections.singletonList(ChatColor.YELLOW + "Fuel it with normal furnace items!"));
        tankMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        tank.setItemMeta(tankMeta);

        tank.addUnsafeEnchantment(Enchantment.LUCK, 1);

        return tank;

    }

    public ShapedRecipe recipe() {

        NamespacedKey key = new NamespacedKey(RocketInit.getPlugin(), "rocket.repairtank");
        ShapedRecipe tankRecipe = new ShapedRecipe(key, tank());
        tankRecipe.shape(" I ", "RFR");

        tankRecipe.setIngredient('I', Material.IRON_BLOCK);
        tankRecipe.setIngredient('F', Material.FURNACE);
        tankRecipe.setIngredient('R', Material.REDSTONE_BLOCK);

        return tankRecipe;

    }

}
