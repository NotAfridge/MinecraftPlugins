package com.ullarah.umagic.recipe;

import com.ullarah.umagic.MagicInit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class MagicHoeSuper extends HoeRecipe {

    public MagicHoeSuper() {
        super("SUPER", Material.DIAMOND);
    }

    public ShapedRecipe recipe() {

        NamespacedKey key = new NamespacedKey(MagicInit.getPlugin(), "magichoe.super");
        ShapedRecipe hoeRecipe = new ShapedRecipe(key, hoe());
        hoeRecipe.shape("DDD", "DHD", "DDD");

        hoeRecipe.setIngredient('D', Material.DIAMOND);
        hoeRecipe.setIngredient('H', Material.DIAMOND_HOE);

        return hoeRecipe;
    }
}
