package com.ullarah.urocket.recipe;

import com.ullarah.ulib.function.NewRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class RocketBoots implements NewRecipe {

    public static ItemStack boots() {

        ItemStack boots = new ItemStack(Material.IRON_BOOTS, 1);

        ItemMeta bootMeta = boots.getItemMeta();
        bootMeta.setDisplayName(ChatColor.GRAY + "Useless Rocket Boots");

        boots.setItemMeta(bootMeta);

        return boots;

    }

    public ShapedRecipe recipe() {

        ShapedRecipe bootRecipe = new ShapedRecipe(boots());
        bootRecipe.shape("H H", "I I", "T T");

        bootRecipe.setIngredient('H', Material.TRIPWIRE_HOOK);
        bootRecipe.setIngredient('I', Material.IRON_INGOT);
        bootRecipe.setIngredient('T', Material.TNT);

        return bootRecipe;

    }

}
