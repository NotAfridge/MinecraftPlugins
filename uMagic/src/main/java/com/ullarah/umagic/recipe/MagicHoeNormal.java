package com.ullarah.umagic.recipe;

import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;

public class MagicHoeNormal extends HoeRecipe {

    public MagicHoeNormal() {
        super("NORMAL", Material.GLISTERING_MELON_SLICE);
    }

    public ShapedRecipe recipe() {
        ShapedRecipe hoeRecipe = new ShapedRecipe(hoe());
        hoeRecipe.shape("MMM", "MHM", "MMM");

        hoeRecipe.setIngredient('H', Material.DIAMOND_HOE);
        hoeRecipe.setIngredient('M', Material.GLISTERING_MELON_SLICE);

        return hoeRecipe;
    }
}
