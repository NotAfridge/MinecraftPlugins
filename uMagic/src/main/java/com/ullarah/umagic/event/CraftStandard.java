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

            int normalHoes = 0;
            int superHoes = 0;
            int uberHoes = 0;

            boolean hasDiamond = false;

            for (ItemStack hoe : event.getInventory().getMatrix()) {
                if (hoe == null)
                    return;

                if (hoe.hasItemMeta()) if (hoe.getItemMeta().hasDisplayName()) {

                    if (hoe.getItemMeta().hasLore()) {

                        if (hoe.getItemMeta().getLore().get(0).equals(new MagicHoeNormal().getHoeTypeLore()))
                            normalHoes++;

                        if (hoe.getItemMeta().getLore().get(0).equals(new MagicHoeSuper().getHoeTypeLore()))
                            superHoes++;

                        if (hoe.getItemMeta().getLore().get(0).equals(new MagicHoeUber().getHoeTypeLore()))
                            uberHoes++;

                    }
                    else if(hoe.getItemMeta().getDisplayName().equals(new MagicHoeNormal().getHoeDisplayName()))
                        normalHoes++;

                }
            }

            if (event.getInventory().getMatrix()[4].equals(new ItemStack(Material.DIAMOND_BLOCK)))
                hasDiamond = true;

            if (hasDiamond) {
                if (normalHoes == 8)
                    event.getInventory().setResult(new MagicHoeSuper().hoe());

                else if (superHoes == 8)
                    event.getInventory().setResult(new MagicHoeUber().hoe());

                else if (uberHoes == 8)
                    event.getInventory().setResult(new MagicHoeCosmic().hoe());

                else
                    event.getInventory().setResult(null);
            }
        }

    }

}
