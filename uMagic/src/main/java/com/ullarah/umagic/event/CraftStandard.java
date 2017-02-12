package com.ullarah.umagic.event;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.recipe.MagicHoeCosmic;
import com.ullarah.umagic.recipe.MagicHoeNormal;
import com.ullarah.umagic.recipe.MagicHoeSuper;
import com.ullarah.umagic.recipe.MagicHoeUber;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class CraftStandard extends MagicFunctions implements Listener {

    public CraftStandard() {
        super(false);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void event(PrepareItemCraftEvent event) {

        if (event.getInventory().getType().equals(InventoryType.WORKBENCH)) {

            boolean hasNormalHoes = false;
            boolean hasSuperHoes = false;
            boolean hasUberHoes = false;

            boolean hasDiamond = false;

            for (ItemStack hoe : event.getInventory().getMatrix())
                if (hoe.hasItemMeta()) if (hoe.getItemMeta().hasDisplayName()) {
                    hasNormalHoes = hoe.getItemMeta().getDisplayName().equals(new MagicHoeNormal().getHoeDisplayName());

                    if (hoe.getItemMeta().hasLore()) {

                        if (hoe.getItemMeta().getLore().get(0).equals(new MagicHoeSuper().getHoeTypeLore())) {

                            hasNormalHoes = false;
                            hasSuperHoes = true;
                            hasUberHoes = false;

                        }

                        if (hoe.getItemMeta().getLore().get(0).equals(new MagicHoeUber().getHoeTypeLore())) {

                            hasNormalHoes = false;
                            hasSuperHoes = false;
                            hasUberHoes = true;

                        }

                    }

                }

            if (event.getInventory().getMatrix()[4].equals(new ItemStack(Material.DIAMOND_BLOCK)))
                hasDiamond = true;

            if (hasNormalHoes && !hasSuperHoes && !hasUberHoes && hasDiamond)
                event.getInventory().setResult(new MagicHoeSuper().hoe());

            if (!hasNormalHoes && hasSuperHoes && hasDiamond)
                event.getInventory().setResult(new MagicHoeUber().hoe());

            if (!hasNormalHoes && !hasSuperHoes && hasUberHoes && hasDiamond)
                event.getInventory().setResult(new MagicHoeCosmic().hoe());

        }

    }

}
