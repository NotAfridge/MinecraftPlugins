package com.ullarah.urocket.recipe;

import com.ullarah.ulib.function.NewRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static com.ullarah.urocket.RocketLanguage.RB_USELESS;

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

        Material bootType = Material.getMaterial(bootMaterial.name().replaceAll("_(.*)", "") + "_BOOTS");
        ItemStack boots = new ItemStack(bootType, 1);

        ItemMeta bootMeta = boots.getItemMeta();
        bootMeta.setDisplayName(RB_USELESS);

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
