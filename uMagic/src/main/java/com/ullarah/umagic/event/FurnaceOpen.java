package com.ullarah.umagic.event;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class FurnaceOpen extends MagicFunctions implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void event(InventoryOpenEvent event) {

        if (usingMagicHoe((Player) event.getPlayer())) {
            event.setCancelled(true);
            return;
        }

        if (event.getInventory().contains(getFurnaceFuel()) || event.getInventory().contains(getFurnaceSmelt())) {
            getCommonString().messageSend((Player) event.getPlayer(), "This is a magical furnace. Cannot be used.");
            event.setCancelled(true);
        }

    }

}
