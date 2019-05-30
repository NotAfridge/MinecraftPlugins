package com.ullarah.uchest.event;

import com.ullarah.uchest.init.ChestLanguage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.InventoryView;

public class ChestDrag implements Listener {

    @EventHandler
    public void event(final InventoryDragEvent event) {

        InventoryView view = event.getView();

        if (view.getTitle().matches(ChestLanguage.N_DCHEST))
            event.setCancelled(true);

    }

}
