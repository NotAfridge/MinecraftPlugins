package com.ullarah.urocket.recipe;

import com.ullarah.urocket.function.NewRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class RocketFlyZone implements NewRecipe {

    public ItemStack zone() {

        ItemStack zone = new ItemStack(Material.ENDER_PORTAL_FRAME, 1);

        ItemMeta zoneMeta = zone.getItemMeta();
        zoneMeta.setDisplayName(ChatColor.RED + "Rocket Boot Fly Zone Controller");
        zoneMeta.setLore(Collections.singletonList(ChatColor.YELLOW + "Stop players flying in your area!"));

        zoneMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        zone.setItemMeta(zoneMeta);

        zone.addUnsafeEnchantment(Enchantment.LUCK, 1);

        return zone;

    }

    public ShapedRecipe recipe() {

        ShapedRecipe zoneRecipe = new ShapedRecipe(zone());
        zoneRecipe.shape("EEE", "GDG", "GSG");

        zoneRecipe.setIngredient('D', Material.DIAMOND_BLOCK);
        zoneRecipe.setIngredient('E', Material.EYE_OF_ENDER);
        zoneRecipe.setIngredient('G', Material.STAINED_GLASS);
        zoneRecipe.setIngredient('S', Material.SEA_LANTERN);

        return zoneRecipe;

    }

}
