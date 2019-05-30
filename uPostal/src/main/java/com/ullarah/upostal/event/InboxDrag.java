package com.ullarah.upostal.event;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.InventoryView;

public class InboxDrag implements Listener {

    @EventHandler
    public void event(final InventoryDragEvent event) {

        InventoryView view = event.getView();

        if (view.getTitle().matches(ChatColor.DARK_RED + "Inbox: " + ChatColor.DARK_AQUA + "(.*)"))
            event.setCancelled(true);

    }

}
