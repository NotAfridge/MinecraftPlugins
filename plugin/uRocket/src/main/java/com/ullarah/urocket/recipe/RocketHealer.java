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

public class RocketHealer implements NewRecipe {

    public static ItemStack healer() {

        ItemStack healer = new ItemStack(Material.ANVIL, 1);

        ItemMeta healerMeta = healer.getItemMeta();
        healerMeta.setDisplayName(ChatColor.RED + "Rocket Healer");
        healerMeta.setLore(Collections.singletonList(ChatColor.YELLOW + "Repair your Rocket Boots as you fly!"));

        healerMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        healer.setItemMeta(healerMeta);

        healer.addUnsafeEnchantment(Enchantment.LUCK, 1);

        return healer;

    }

    public ShapedRecipe recipe() {

        ShapedRecipe healerRecipe = new ShapedRecipe(healer());
        healerRecipe.shape("A A", "NRN");

        healerRecipe.setIngredient('A', Material.ANVIL);
        healerRecipe.setIngredient('N', Material.NETHER_STAR);
        healerRecipe.setIngredient('R', Material.REDSTONE_BLOCK);

        return healerRecipe;

    }

}
