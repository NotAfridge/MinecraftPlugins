package com.ullarah.uchest.event;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.InventoryView;

public class ChestInteract implements Listener {

    @EventHandler
    public void event(final InventoryInteractEvent event) {

        InventoryView view = event.getView();

        if (view.getTitle().matches("" + ChatColor.GOLD + ChatColor.BOLD + "Mixed Chests"))
            event.setCancelled(true);

    }

}
