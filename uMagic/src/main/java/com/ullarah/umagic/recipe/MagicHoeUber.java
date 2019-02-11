package com.ullarah.umagic.recipe;

import com.ullarah.umagic.MagicInit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class MagicHoeUber extends HoeRecipe {

    public MagicHoeUber() {
        super("UBER", Material.DIAMOND_BLOCK);
    }

    public ShapedRecipe recipe() {

        NamespacedKey key = new NamespacedKey(MagicInit.getPlugin(), "magichoe.uber");
        ShapedRecipe hoeRecipe = new ShapedRecipe(key, hoe());
        hoeRecipe.shape("BBB", "BHB", "BBB");

        hoeRecipe.setIngredient('B', Material.DIAMOND_BLOCK);
        hoeRecipe.setIngredient('H', Material.DIAMOND_HOE);

        return hoeRecipe;
    }

    @Override
    public ItemStack hoe() {
        ItemStack hoeStack = super.hoe();
        hoeStack.addEnchantment(Enchantment.MENDING, 1);
        return hoeStack;
    }
}
