package com.ullarah.upostal.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

public class InboxDrag implements Listener {

    @EventHandler
    public void event(final InventoryDragEvent event) {

        Inventory chestInventory = event.getInventory();

        if (chestInventory.getName().matches("§4Inbox: §3(.*)"))
            event.setCancelled(true);

    }

}
