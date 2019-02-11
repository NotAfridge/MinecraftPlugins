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

public class RocketFlyZone implements NewRecipe {

    public ItemStack zone() {

        ItemStack zone = new ItemStack(Material.END_PORTAL_FRAME, 1);

        ItemMeta zoneMeta = zone.getItemMeta();
        zoneMeta.setDisplayName(ChatColor.RED + "Rocket Boot Fly Zone Controller");
        zoneMeta.setLore(Collections.singletonList(ChatColor.YELLOW + "Stop players flying in your area!"));

        zoneMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        zone.setItemMeta(zoneMeta);

        zone.addUnsafeEnchantment(Enchantment.LUCK, 1);

        return zone;

    }

    public ShapedRecipe recipe() {

        NamespacedKey key = new NamespacedKey(RocketInit.getPlugin(), "rocket.flyzone");
        ShapedRecipe zoneRecipe = new ShapedRecipe(key, zone());
        zoneRecipe.shape("EEE", "GDG", "GSG");

        zoneRecipe.setIngredient('D', Material.DIAMOND_BLOCK);
        zoneRecipe.setIngredient('E', Material.ENDER_EYE);
        zoneRecipe.setIngredient('G', Material.WHITE_STAINED_GLASS);
        zoneRecipe.setIngredient('S', Material.SEA_LANTERN);

        return zoneRecipe;

    }

}
