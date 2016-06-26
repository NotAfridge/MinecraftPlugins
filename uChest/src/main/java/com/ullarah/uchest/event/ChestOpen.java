package com.ullarah.uchest.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import static com.ullarah.uchest.ChestInit.chestSwapBusy;
import static com.ullarah.uchest.ChestInit.chestSwapPlayer;
import static com.ullarah.uchest.init.ChestLanguage.N_WCHEST;

public class ChestOpen implements Listener {

    @EventHandler
    public void event(final InventoryOpenEvent event) {

        Inventory chestInventory = event.getInventory();
        Player chestPlayer = (Player) event.getPlayer();

        if (chestInventory.getName().matches(N_WCHEST)) {
            chestSwapPlayer = chestPlayer;
            chestSwapBusy = true;
            chestInventory.clear();
        }

    }

}
