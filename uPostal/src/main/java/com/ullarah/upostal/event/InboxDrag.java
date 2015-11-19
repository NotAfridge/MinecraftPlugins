package com.ullarah.upostal.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;

public class InboxDrag implements Listener {

    @EventHandler
    public void event(final InventoryDragEvent event) {

        if (event.getInventory().getName().matches("§4Inbox: §3(.*)")) event.setCancelled(true);

    }

}
