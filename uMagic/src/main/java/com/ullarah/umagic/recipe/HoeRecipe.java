package com.ullarah.umagic.recipe;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class HoeRecipe {

    private Material recipePadding;
    private String hoeDisplayName;
    private String hoeTypeLore;
    private boolean unbreakable;

    public HoeRecipe(String name, Material recipePadding) {
        this.recipePadding = recipePadding;
        this.hoeDisplayName = ChatColor.AQUA + "Magical Hoe";
        this.hoeTypeLore = "" + ChatColor.AQUA + ChatColor.BOLD + "▪▪ " + name + " ▪▪";
        this.unbreakable = false;
    }

    public abstract ShapedRecipe recipe();

    public ItemStack hoe() {
        ItemStack hoeStack = new ItemStack(Material.DIAMOND_HOE, 1);
        hoeStack.setItemMeta(getMeta(hoeStack));

        hoeStack.addUnsafeEnchantment(Enchantment.LUCK, 1);

        return hoeStack;
    }

    private ItemMeta getMeta(ItemStack hoeStack) {
        ItemMeta hoeMeta = hoeStack.getItemMeta();

        hoeMeta.setDisplayName(getHoeDisplayName());
        hoeMeta.setLore(getLore());
        hoeMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        if (isUnbreakable()) {
            hoeMeta.setUnbreakable(true);
            hoeMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        }

        return hoeMeta;
    }

    private List<String> getLore() {
        ArrayList<String> hoeLore = new ArrayList<>();

        hoeLore.add(getHoeTypeLore());
        hoeLore.add(ChatColor.RESET + "");

        hoeLore.add("" + ChatColor.RED + ChatColor.BOLD + "Use it at your own risk!");
        hoeLore.add(ChatColor.RED + "If anything breaks, it's your own fault!");

        return hoeLore;
    }

    public Material getRecipePadding() {
        return recipePadding;
    }

    public String getHoeDisplayName() {
        return this.hoeDisplayName;
    }

    public String getHoeTypeLore() {
        return this.hoeTypeLore;
    }

    public boolean isUnbreakable() {
        return unbreakable;
    }

    protected void setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
    }
}
