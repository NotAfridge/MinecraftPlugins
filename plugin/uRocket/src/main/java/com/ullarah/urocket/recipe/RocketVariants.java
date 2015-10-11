package com.ullarah.urocket.recipe;

import com.ullarah.ulib.function.NewRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class RocketVariants implements NewRecipe {

    private final Material bootMaterial;

    public RocketVariants(Material material) {

        bootMaterial = material;

    }

    private static ItemStack boots() {

        ItemStack boots = new ItemStack(Material.IRON_BOOTS, 1);

        ItemMeta bootMeta = boots.getItemMeta();
        bootMeta.setDisplayName(ChatColor.RED + "Useless Rocket Boots");
        bootMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Useless Variant"));

        boots.setItemMeta(bootMeta);

        return boots;

    }

    public ShapedRecipe recipe() {

        ShapedRecipe bootRecipe = new ShapedRecipe(boots());
        bootRecipe.shape("H H", "M M", "TNT");

        bootRecipe.setIngredient('H', Material.TRIPWIRE_HOOK);
        bootRecipe.setIngredient('M', bootMaterial);
        bootRecipe.setIngredient('N', Material.NOTE_BLOCK);
        bootRecipe.setIngredient('T', Material.TNT);

        return bootRecipe;

    }

}
