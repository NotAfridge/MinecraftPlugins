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

public class RocketFuelJacket implements NewRecipe {

    private final Material jacketMaterial;

    public RocketFuelJacket(Material material) {
        jacketMaterial = material;
    }

    public static ItemStack jacket(Material jacketMaterial) {

        ItemStack jacket = new ItemStack(jacketMaterial, 1);

        ItemMeta jacketMeta = jacket.getItemMeta();
        jacketMeta.setDisplayName(ChatColor.RED + "Rocket Boot Fuel Jacket");
        jacketMeta.setLore(Collections.singletonList(ChatColor.YELLOW + "Stores all your Rocket Boot fuel!"));

        jacketMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        jacket.setItemMeta(jacketMeta);

        jacket.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);

        return jacket;

    }

    public ShapedRecipe recipe() {

        NamespacedKey key = new NamespacedKey(RocketInit.getPlugin(), "rocket.fueljacket."+jacketMaterial);
        ShapedRecipe jacketRecipe = new ShapedRecipe(key, jacket(jacketMaterial));

        jacketRecipe.shape("J", "E");

        jacketRecipe.setIngredient('E', Material.ENDER_CHEST);
        jacketRecipe.setIngredient('J', jacketMaterial);

        return jacketRecipe;

    }

}
