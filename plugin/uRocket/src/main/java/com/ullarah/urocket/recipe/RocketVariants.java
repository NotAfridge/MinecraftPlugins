package com.ullarah.urocket.recipe;

import com.ullarah.ulib.function.NewRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class RocketVariants implements NewRecipe {

    private final Material bootMaterial;
    private final boolean hasEnhancement;

    public RocketVariants(Material material, boolean enhancement) {

        bootMaterial = material;
        hasEnhancement = enhancement;

    }

    private static ItemStack boots(boolean hasEnhancement) {

        ItemStack boots = new ItemStack(Material.IRON_BOOTS, 1);

        ItemMeta bootMeta = boots.getItemMeta();
        bootMeta.setDisplayName(ChatColor.GRAY + "Useless Rocket Boots");
        bootMeta.setLore(Arrays.asList(
                        ChatColor.GRAY + "Useless Variant",
                        " ", ChatColor.RED + "Bad Rocket Boot Combination!")
        );

        if (hasEnhancement) {

            bootMeta.setLore(Arrays.asList(
                            ChatColor.GRAY + "Useless Variant",
                            ChatColor.GRAY + "Useless Enhancement",
                            " ", ChatColor.RED + "Bad Rocket Boot Combination!")
            );

            bootMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            boots.setItemMeta(bootMeta);

        }

        boots.setItemMeta(bootMeta);

        return boots;

    }

    public ShapedRecipe recipe() {

        ShapedRecipe bootRecipe = new ShapedRecipe(boots(hasEnhancement));

        if (hasEnhancement) {

            bootRecipe.shape("H H", "MAM", "TNT");
            bootRecipe.setIngredient('A', Material.NETHER_STAR);

        } else bootRecipe.shape("H H", "M M", "TNT");

        bootRecipe.setIngredient('H', Material.TRIPWIRE_HOOK);
        bootRecipe.setIngredient('M', bootMaterial);
        bootRecipe.setIngredient('N', Material.NOTE_BLOCK);
        bootRecipe.setIngredient('T', Material.TNT);

        return bootRecipe;

    }

}
