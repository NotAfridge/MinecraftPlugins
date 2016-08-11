package com.ullarah.umagic;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class MagicRecipe {

    private String hoeStableName;

    public MagicRecipe() {
        setHoeStableName(ChatColor.AQUA + "Magical Hoe");
    }

    public String getHoeStableName() {
        return this.hoeStableName;
    }

    private void setHoeStableName(String name) {
        this.hoeStableName = name;
    }

    ItemStack hoeStable() {

        ItemStack hoeStableStack = new ItemStack(Material.DIAMOND_HOE, 1);
        ItemMeta hoeStableMeta = hoeStableStack.getItemMeta();

        hoeStableMeta.setDisplayName(getHoeStableName());

        ArrayList<String> hoeLore = new ArrayList<>();
        hoeLore.add(ChatColor.RED + "Use this at your own risk!");
        hoeLore.add(ChatColor.YELLOW + "If anything breaks, it's your own fault!");

        hoeStableMeta.setLore(hoeLore);

        hoeStableMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        hoeStableStack.setItemMeta(hoeStableMeta);

        hoeStableStack.addUnsafeEnchantment(Enchantment.LUCK, 1);

        return hoeStableStack;

    }

    ShapedRecipe hoeStableRecipe() {

        ShapedRecipe hoeStableRecipe = new ShapedRecipe(hoeStable());
        hoeStableRecipe.shape("RMR", "MHM", "RMR");

        hoeStableRecipe.setIngredient('H', Material.DIAMOND_HOE);
        hoeStableRecipe.setIngredient('M', Material.SPECKLED_MELON);

        return hoeStableRecipe;

    }

}
