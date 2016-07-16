package com.ullarah.upostal.event;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;

class InboxDrag implements Listener {

    @EventHandler
    public void event(final InventoryDragEvent event) {

        if (event.getInventory().getName().matches(ChatColor.DARK_RED + "Inbox: " + ChatColor.DARK_AQUA + "(.*)"))
            event.setCancelled(true);

    }

}
