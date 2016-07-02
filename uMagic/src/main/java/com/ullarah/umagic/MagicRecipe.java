package com.ullarah.umagic;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import static org.bukkit.Material.DIAMOND_HOE;
import static org.bukkit.Material.SPECKLED_MELON;

public class MagicRecipe {

    public static String hoeName = ChatColor.AQUA + "Magical Hoe";

    public ItemStack hoe() {

        ItemStack hoeStack = new ItemStack(DIAMOND_HOE, 1);
        ItemMeta hoeMeta = hoeStack.getItemMeta();

        hoeMeta.setDisplayName(hoeName);

        hoeMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        hoeStack.setItemMeta(hoeMeta);

        hoeStack.addUnsafeEnchantment(Enchantment.LUCK, 1);

        return hoeStack;

    }

    public ShapedRecipe hoeRecipe() {

        ShapedRecipe hoeRecipe = new ShapedRecipe(hoe());
        hoeRecipe.shape(" M ", "MHM", " M ");

        hoeRecipe.setIngredient('H', DIAMOND_HOE);
        hoeRecipe.setIngredient('M', SPECKLED_MELON);

        return hoeRecipe;

    }

}
