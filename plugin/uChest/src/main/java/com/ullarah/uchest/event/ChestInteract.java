package com.ullarah.uchest.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;

public class ChestInteract implements Listener {

    @EventHandler
    public void event(final InventoryInteractEvent event) {

        Inventory chestInventory = event.getInventory();

        if (chestInventory.getName().matches("§6§lMixed Chests")) event.setCancelled(true);

    }

}
