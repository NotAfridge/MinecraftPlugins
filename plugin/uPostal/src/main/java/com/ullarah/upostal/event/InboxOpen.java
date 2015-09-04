package com.ullarah.upostal.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class InboxOpen implements Listener {

    @EventHandler
    public void event(final InventoryOpenEvent event) {

        Inventory chestInventory = event.getInventory();

        if (chestInventory.getName().matches("§4Inbox: §3(.*)")) {

            UUID inboxViewerUUID = event.getInventory().getViewers().get(0).getUniqueId();

            // if (!owner.getPlayerListName().equals(playerName)) inboxConfig.set("name", owner.getPlayerListName());
            // inboxConfig.save(inboxFile);

            /* TODO
            Change the name of the inbox to the players name in case of name change.
             */

        }

    }

}
