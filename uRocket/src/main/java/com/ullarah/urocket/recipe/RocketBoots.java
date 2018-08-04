package com.ullarah.urocket.recipe;

import com.ullarah.urocket.function.NewRecipe;
import com.ullarah.urocket.init.RocketLanguage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class RocketBoots implements NewRecipe {

    private final Material bootMaterial;
    private final boolean hasVariant;
    private final boolean hasEnhancement;

    public RocketBoots(Material material, boolean variant, boolean enhancement) {

        bootMaterial = material;
        hasVariant = variant;
        hasEnhancement = enhancement;

    }

    private ItemStack boots(Material bootMaterial, boolean hasVariant, boolean hasEnhancement) {

        Material bootType;
        switch (bootMaterial) {
            case LEATHER:
                bootType = Material.LEATHER_BOOTS;
                break;
            case IRON_INGOT:
                bootType = Material.IRON_BOOTS;
                break;
            case GOLD_INGOT:
                bootType = Material.GOLDEN_BOOTS;
                break;
            case DIAMOND:
                bootType = Material.DIAMOND_BOOTS;
                break;
            default:
                throw new IllegalArgumentException("Invalid boot material");
        }

        ItemStack boots = new ItemStack(bootType, 1);

        ItemMeta bootMeta = boots.getItemMeta();
        bootMeta.setDisplayName(RocketLanguage.RB_USELESS);

        List<String> loreList = new ArrayList<>();

        if (hasVariant) loreList.add(ChatColor.GRAY + "Useless Variant");
        if (hasEnhancement) loreList.add(ChatColor.GRAY + "Useless Enhancement");

        loreList.add("");
        loreList.add(ChatColor.RED + "Bad Rocket Boot Combination!");

        bootMeta.setLore(loreList);
        boots.setItemMeta(bootMeta);

        return boots;

    }

    public ShapedRecipe recipe() {

        ShapedRecipe bootRecipe = new ShapedRecipe(boots(bootMaterial, hasVariant, hasEnhancement));

        bootRecipe.shape("H H", hasEnhancement ? "MEM" : "M M", hasVariant ? "TVT" : "T T");

        bootRecipe.setIngredient('H', Material.TRIPWIRE_HOOK);
        bootRecipe.setIngredient('M', bootMaterial);
        bootRecipe.setIngredient('T', Material.TNT);

        if (hasVariant) bootRecipe.setIngredient('V', Material.NOTE_BLOCK);
        if (hasEnhancement) bootRecipe.setIngredient('E', Material.NETHER_STAR);

        return bootRecipe;

    }

}
