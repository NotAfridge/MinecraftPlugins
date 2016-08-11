package com.ullarah.umagic.event;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.function.CommonString;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class FurnaceOpen extends MagicFunctions implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void event(InventoryOpenEvent event) {

        if (event.getInventory().contains(getFurnaceFuel()) || event.getInventory().contains(getFurnaceSmelt())) {
            new CommonString().messageSend(event.getPlayer(), "This is a magical furnace. Cannot be used.");
            event.setCancelled(true);
        }

    }

}
