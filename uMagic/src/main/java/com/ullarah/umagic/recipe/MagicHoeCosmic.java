package com.ullarah.umagic.recipe;

import com.ullarah.umagic.MagicInit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class MagicHoeCosmic extends HoeRecipe {

    public MagicHoeCosmic() {
        super("COSMIC", Material.NETHER_STAR);
        setUnbreakable(true);
    }

    public ShapedRecipe recipe() {

        NamespacedKey key = new NamespacedKey(MagicInit.getPlugin(), "magichoe.cosmic");
        ShapedRecipe hoeRecipe = new ShapedRecipe(key, hoe());
        hoeRecipe.shape("SSS", "SHS", "SSS");

        hoeRecipe.setIngredient('S', Material.NETHER_STAR);
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
