package com.ullarah.umagic.recipe;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class MagicHoeUber {

    private String hoeDisplayName;
    private String hoeUberLore;

    public MagicHoeUber() {
        setHoeDisplayName(new MagicHoeNormal().getHoeDisplayName());
        setHoeUberLore("" + ChatColor.AQUA + ChatColor.BOLD + "▪▪ UBER ▪▪");
    }

    private String getHoeDisplayName() {
        return this.hoeDisplayName;
    }

    private void setHoeDisplayName(String name) {
        this.hoeDisplayName = name;
    }

    public String getHoeUberLore() {
        return this.hoeUberLore;
    }

    private void setHoeUberLore(String lore) {
        this.hoeUberLore = lore;
    }

    public ItemStack hoe() {

        ItemStack hoeStack = new ItemStack(Material.DIAMOND_HOE, 1);
        ItemMeta hoeMeta = hoeStack.getItemMeta();

        hoeMeta.setDisplayName(getHoeDisplayName());

        ArrayList<String> hoeLore = new ArrayList<>();

        hoeLore.add(getHoeUberLore());
        hoeLore.add(ChatColor.RESET + "");

        hoeLore.add("" + ChatColor.RED + ChatColor.BOLD + "Use it at your own risk!");
        hoeLore.add(ChatColor.RED + "If anything breaks, it's your own fault!");

        hoeMeta.setLore(hoeLore);

        hoeMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        hoeStack.setItemMeta(hoeMeta);

        hoeStack.addUnsafeEnchantment(Enchantment.LUCK, 1);

        return hoeStack;

    }

    public ShapedRecipe recipe() {

        ShapedRecipe hoeRecipe = new ShapedRecipe(hoe());
        hoeRecipe.shape("HHH", "HDH", "HHH");

        hoeRecipe.setIngredient('D', Material.DIAMOND_BLOCK);
        hoeRecipe.setIngredient('H', Material.DIAMOND_HOE);

        return hoeRecipe;

    }

}
