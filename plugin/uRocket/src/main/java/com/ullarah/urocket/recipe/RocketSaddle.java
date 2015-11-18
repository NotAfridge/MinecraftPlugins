package com.ullarah.urocket.recipe;

import com.ullarah.ulib.function.NewRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class RocketSaddle implements NewRecipe {

    public ItemStack saddle() {

        ItemStack saddle = new ItemStack(Material.SADDLE, 1);

        ItemMeta saddleMeta = saddle.getItemMeta();
        saddleMeta.setDisplayName(ChatColor.RED + "Rocket Boot Saddle");
        saddleMeta.setLore(Arrays.asList(
                ChatColor.YELLOW + "Make pigs fly...",
                ChatColor.YELLOW + "...maybe horses too!")
        );

        saddleMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        saddle.setItemMeta(saddleMeta);

        saddle.addUnsafeEnchantment(Enchantment.LUCK, 1);

        return saddle;

    }

    public ShapedRecipe recipe() {

        ShapedRecipe saddleRecipe = new ShapedRecipe(saddle());
        saddleRecipe.shape("TST");

        saddleRecipe.setIngredient('S', Material.SADDLE);
        saddleRecipe.setIngredient('T', Material.TNT);

        return saddleRecipe;

    }

}
