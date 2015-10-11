package com.ullarah.urocket.recipe;

import com.ullarah.ulib.function.NewRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class RepairTank implements NewRecipe {

    public static ItemStack tank() {

        ItemStack tank = new ItemStack(Material.FURNACE, 1);

        ItemMeta tankMeta = tank.getItemMeta();
        tankMeta.setDisplayName(ChatColor.RED + "Rocket Repair Tank");
        tankMeta.setLore(Collections.singletonList(ChatColor.YELLOW + "Fuel it with normal furnace items!"));
        tankMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        tank.setItemMeta(tankMeta);

        tank.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 1);

        return tank;

    }

    public ShapedRecipe recipe() {

        ShapedRecipe tankRecipe = new ShapedRecipe(tank());
        tankRecipe.shape(" I ", "RFR");

        tankRecipe.setIngredient('I', Material.IRON_BLOCK);
        tankRecipe.setIngredient('F', Material.FURNACE);
        tankRecipe.setIngredient('R', Material.REDSTONE_BLOCK);

        return tankRecipe;

    }

}
