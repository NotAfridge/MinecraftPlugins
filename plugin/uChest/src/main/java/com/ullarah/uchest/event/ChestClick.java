package com.ullarah.uchest.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import static com.ullarah.uchest.ChestFunctions.openChestDelay;

public class ChestClick implements Listener {

    @EventHandler
    public void event(final InventoryClickEvent event) {

        Inventory chestInventory = event.getInventory();
        Player chestPlayer = (Player) event.getWhoClicked();

        if (chestInventory.getName().matches("§2Donation Chest") && event.getRawSlot() == -999)
            event.getView().close();

        if (chestInventory.getName().matches("§2Experience Chest") && event.getRawSlot() == -999)
            event.getView().close();

        if (chestInventory.getName().matches("§2Random Chest")) if (event.getRawSlot() == -999) event.getView().close();
        else if (event.getCurrentItem().getAmount() >= 1 && event.getRawSlot() >= 54) event.setCancelled(true);

        if (chestInventory.getName().matches("§6§lMixed Chests")) {
            event.setCancelled(true);
            chestPlayer.closeInventory();

            switch (event.getRawSlot()) {

                case 0:
                    openChestDelay(chestPlayer, "dchest");
                    break;

                case 1:
                    openChestDelay(chestPlayer, "hchest");
                    break;

                case 2:
                    openChestDelay(chestPlayer, "mchest");
                    break;

                case 3:
                    openChestDelay(chestPlayer, "pchest");
                    break;

                case 4:
                    openChestDelay(chestPlayer, "rchest");
                    break;

                case 5:
                    openChestDelay(chestPlayer, "schest");
                    break;

                case 6:
                    openChestDelay(chestPlayer, "vchest");
                    break;

                case 7:
                    openChestDelay(chestPlayer, "xchest");
                    break;

            }
        }

    }

}
