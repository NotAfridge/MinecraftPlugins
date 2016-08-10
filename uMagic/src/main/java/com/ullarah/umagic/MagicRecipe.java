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
    private String hoeExperimentalName;

    public MagicRecipe() {
        setHoeStableName(ChatColor.AQUA + "Magical Hoe");
        setHoeExperimentalName(ChatColor.RED + "Experimental Hoe");
    }

    public String getHoeStableName() {
        return this.hoeStableName;
    }

    private void setHoeStableName(String name) {
        this.hoeStableName = name;
    }

    public String getHoeExperimentalName() {
        return this.hoeExperimentalName;
    }

    private void setHoeExperimentalName(String name) {
        this.hoeExperimentalName = name;
    }

    ItemStack hoeStable() {

        ItemStack hoeStableStack = new ItemStack(Material.DIAMOND_HOE, 1);
        ItemMeta hoeStableMeta = hoeStableStack.getItemMeta();

        hoeStableMeta.setDisplayName(getHoeStableName());

        hoeStableMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        hoeStableStack.setItemMeta(hoeStableMeta);

        hoeStableStack.addUnsafeEnchantment(Enchantment.LUCK, 1);

        return hoeStableStack;

    }

    ShapedRecipe hoeStableRecipe() {

        ShapedRecipe hoeStableRecipe = new ShapedRecipe(hoeStable());
        hoeStableRecipe.shape(" M ", "MHM", " M ");

        hoeStableRecipe.setIngredient('H', Material.DIAMOND_HOE);
        hoeStableRecipe.setIngredient('M', Material.SPECKLED_MELON);

        return hoeStableRecipe;

    }

    ItemStack hoeExperimental() {

        ItemStack hoeExperimentalStack = new ItemStack(Material.DIAMOND_HOE, 1);
        ItemMeta hoeExperimentalMeta = hoeExperimentalStack.getItemMeta();

        hoeExperimentalMeta.setDisplayName(getHoeExperimentalName());

        ArrayList<String> hoeLore = new ArrayList<>();
        hoeLore.add(ChatColor.RED + "Use this at your own risk!");
        hoeLore.add(ChatColor.YELLOW + "If anything breaks, it's your own fault!");

        hoeExperimentalMeta.setLore(hoeLore);

        hoeExperimentalMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        hoeExperimentalStack.setItemMeta(hoeExperimentalMeta);

        hoeExperimentalStack.addUnsafeEnchantment(Enchantment.LUCK, 1);

        return hoeExperimentalStack;

    }

    ShapedRecipe hoeExperimentalRecipe() {

        ShapedRecipe hoeExperimentalRecipe = new ShapedRecipe(hoeExperimental());
        hoeExperimentalRecipe.shape("RMR", "MHM", "RMR");

        hoeExperimentalRecipe.setIngredient('H', Material.DIAMOND_HOE);
        hoeExperimentalRecipe.setIngredient('M', Material.SPECKLED_MELON);
        hoeExperimentalRecipe.setIngredient('R', Material.REDSTONE);

        return hoeExperimentalRecipe;

    }

}
