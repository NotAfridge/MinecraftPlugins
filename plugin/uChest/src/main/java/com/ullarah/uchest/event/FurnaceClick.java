package com.ullarah.uchest.event;

import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.FurnaceInventory;

import java.util.Set;

public class FurnaceClick implements Listener {

    @EventHandler
    public void event(final InventoryClickEvent event) {

        if (event.getInventory() instanceof FurnaceInventory) {

            Furnace furnace = (Furnace) (event.getWhoClicked().getTargetBlock((Set<Material>)null, 10)).getState();

            short cookTime = 400;

            switch (event.getCursor().getType()) {

                case IRON_HELMET:
                    furnace.setCookTime(cookTime);
                    break;

                case IRON_CHESTPLATE:
                    furnace.setCookTime(cookTime);
                    break;

                case IRON_LEGGINGS:
                    furnace.setCookTime(cookTime);
                    break;

                case IRON_BOOTS:
                    furnace.setCookTime(cookTime);
                    break;

            }

        }

    }

}
