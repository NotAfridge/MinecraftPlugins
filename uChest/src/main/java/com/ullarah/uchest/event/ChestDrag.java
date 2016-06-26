package com.ullarah.uchest.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

import static com.ullarah.uchest.init.ChestLanguage.N_DCHEST;

public class ChestDrag implements Listener {

    @EventHandler
    public void event(final InventoryDragEvent event) {

        Inventory chestInventory = event.getInventory();

        if (chestInventory.getName().matches(N_DCHEST))
            event.setCancelled(true);

    }

}
