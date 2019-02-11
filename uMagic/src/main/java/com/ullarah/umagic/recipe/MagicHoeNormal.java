package com.ullarah.umagic.recipe;

import com.ullarah.umagic.MagicInit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class MagicHoeNormal extends HoeRecipe {

    public MagicHoeNormal() {
        super("NORMAL", Material.GLISTERING_MELON_SLICE);
    }

    public ShapedRecipe recipe() {

        NamespacedKey key = new NamespacedKey(MagicInit.getPlugin(), "magichoe.normal");
        ShapedRecipe hoeRecipe = new ShapedRecipe(key, hoe());
        hoeRecipe.shape("MMM", "MHM", "MMM");

        hoeRecipe.setIngredient('H', Material.DIAMOND_HOE);
        hoeRecipe.setIngredient('M', Material.GLISTERING_MELON_SLICE);

        return hoeRecipe;
    }
}
