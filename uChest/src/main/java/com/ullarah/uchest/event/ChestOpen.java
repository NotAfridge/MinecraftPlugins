package com.ullarah.uchest.event;

import com.ullarah.uchest.ChestInit;
import com.ullarah.uchest.init.ChestLanguage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class ChestOpen implements Listener {

    @EventHandler
    public void event(final InventoryOpenEvent event) {

        Inventory chestInventory = event.getInventory();
        InventoryView view = event.getView();

        if (view.getTitle().matches(ChestLanguage.N_WCHEST)) {
            ChestInit.chestSwapPlayer = (Player) event.getPlayer();
            ChestInit.chestSwapBusy = true;
            chestInventory.clear();
        }

    }

}
