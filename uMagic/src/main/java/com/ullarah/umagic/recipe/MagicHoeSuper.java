package com.ullarah.umagic.recipe;

import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;

public class MagicHoeSuper extends HoeRecipe {

    public MagicHoeSuper() {
        super("SUPER", Material.DIAMOND);
    }

    public ShapedRecipe recipe() {
        ShapedRecipe hoeRecipe = new ShapedRecipe(hoe());
        hoeRecipe.shape("DDD", "DHD", "DDD");

        hoeRecipe.setIngredient('D', Material.DIAMOND);
        hoeRecipe.setIngredient('H', Material.DIAMOND_HOE);

        return hoeRecipe;
    }
}
