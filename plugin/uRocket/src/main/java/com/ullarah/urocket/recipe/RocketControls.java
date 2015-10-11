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

public class RocketControls implements NewRecipe {

    public static ItemStack control() {

        ItemStack control = new ItemStack(Material.TRIPWIRE_HOOK, 1);
        ItemMeta controlMeta = control.getItemMeta();

        controlMeta.setDisplayName(ChatColor.RED + "Rocket Control");
        controlMeta.setLore(Collections.singletonList(ChatColor.YELLOW + "Take control of your Rocket Boots!"));

        controlMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        control.setItemMeta(controlMeta);

        control.addUnsafeEnchantment(Enchantment.DURABILITY, 1);

        return control;

    }

    public ShapedRecipe recipe() {

        ShapedRecipe controlRecipe = new ShapedRecipe(control());
        controlRecipe.shape("T", "L", "L");

        controlRecipe.setIngredient('L', Material.LEASH);
        controlRecipe.setIngredient('T', Material.TRIPWIRE_HOOK);

        return controlRecipe;

    }

}
