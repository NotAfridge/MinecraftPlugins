package com.ullarah.urocket.recipe;

import com.ullarah.ulib.function.NewRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class RocketBoots implements NewRecipe {

    private final Material bootMaterial;
    private final boolean hasEnhancement;

    public RocketBoots(Material material, boolean enhancement) {

        bootMaterial = material;
        hasEnhancement = enhancement;

    }

    private static ItemStack boots(boolean hasEnhancement) {

        ItemStack boots = new ItemStack(Material.IRON_BOOTS, 1);

        ItemMeta bootMeta = boots.getItemMeta();
        bootMeta.setDisplayName(ChatColor.GRAY + "Useless Rocket Boots");

        if (hasEnhancement) {

            bootMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Useless Enhancement"));

            bootMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            boots.setItemMeta(bootMeta);

        }

        boots.setItemMeta(bootMeta);

        return boots;

    }

    public ShapedRecipe recipe() {

        ShapedRecipe bootRecipe = new ShapedRecipe(boots(hasEnhancement));

        if (hasEnhancement) {

            bootRecipe.shape("H H", "MAM", "T T");
            bootRecipe.setIngredient('A', Material.NETHER_STAR);

        } else bootRecipe.shape("H H", "M M", "T T");

        bootRecipe.setIngredient('H', Material.TRIPWIRE_HOOK);
        bootRecipe.setIngredient('M', bootMaterial);
        bootRecipe.setIngredient('T', Material.TNT);

        return bootRecipe;

    }

}
