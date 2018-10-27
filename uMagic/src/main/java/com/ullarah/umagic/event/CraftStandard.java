package com.ullarah.umagic.event;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.recipe.*;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class CraftStandard extends MagicFunctions implements Listener {

    public CraftStandard() {
        super(false);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void event(PrepareItemCraftEvent event) {

        if (!event.getInventory().getType().equals(InventoryType.WORKBENCH))
            return;

        ItemStack[] matrix = event.getInventory().getMatrix();
        ItemStack hoe = matrix[4];

        if (hoe == null || hoe.getType() != Material.DIAMOND_HOE)
            return;

        // Set to no result until we figure out what it is
        event.getInventory().setResult(null);

        List<HoeRecipe> recipes = Arrays.asList(new MagicHoeNormal(), new MagicHoeSuper(), new MagicHoeUber(), new MagicHoeCosmic());
        HoeRecipe recipe = null;
        if (hoe.hasItemMeta() && hoe.getItemMeta().hasDisplayName() && hoe.getItemMeta().hasLore()){
            String topLore = hoe.getItemMeta().getLore().get(0);

            for (int i = 0; i < recipes.size() - 1; i++) {
                HoeRecipe input = recipes.get(i);
                if (topLore.equals(input.getHoeTypeLore())) {
                    recipe = recipes.get(i + 1);
                    break;
                }
            }
        }

        if (recipe == null)
            recipe = recipes.get(0);

        if (surroundedBy(matrix, recipe.getRecipePadding())) {
            event.getInventory().setResult(recipe.hoe());
        }
    }

    private boolean surroundedBy(ItemStack[] matrix, Material material) {
        for (int i = 0; i < 9; i++) {
            if (i == 4)
                continue;

            if (matrix[i] == null || matrix[i].getType() != material)
                return false;
        }
        return true;
    }

}
