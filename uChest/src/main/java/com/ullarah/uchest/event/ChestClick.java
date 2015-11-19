package com.ullarah.uchest.event;

import com.ullarah.uchest.ChestFunctions;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import static com.ullarah.uchest.ChestInit.*;

public class ChestClick implements Listener {

    private void chestDonatePlayerUnlock(Player player) {

        new BukkitRunnable() {

            int c = getPlugin().getConfig().getInt("unlock");

            @Override
            public void run() {
                if (c <= 0) {
                    chestDonateLockout.remove(player.getUniqueId());
                    this.cancel();
                    return;
                }
                chestDonateLockout.add(player.getUniqueId());
                c--;
            }

        }.runTaskTimer(getPlugin(), 0, 20);

    }

    @EventHandler
    public void event(final InventoryClickEvent event) {

        ChestFunctions chestFunctions = new ChestFunctions();

        Inventory chestInventory = event.getClickedInventory();
        Player chestPlayer = (Player) event.getWhoClicked();

        if (chestInventory == null) return;

        if (chestInventory.getName().matches("§2Donation Chest")) {
            if (chestDonateLock && event.getRawSlot() <= 53)
                if (chestDonateLockout.contains(chestPlayer.getUniqueId())) {

                    event.setCancelled(true);
                    event.getCursor().setType(Material.AIR);

                } else chestDonatePlayerUnlock(chestPlayer);
        }

        if (chestInventory.getName().matches("§2Random Chest"))
            if (event.getCurrentItem().getAmount() >= 1 && event.getRawSlot() >= 45) {
                event.setCancelled(true);
                event.getCursor().setType(Material.AIR);
            }

        if (chestInventory.getName().matches("§6§lMixed Chests")) {

            event.setCancelled(true);
            event.getCursor().setType(Material.AIR);
            chestPlayer.closeInventory();

            switch (event.getRawSlot()) {

                case 0:
                    chestFunctions.openChestDelay(chestPlayer, "dchest");
                    break;

                case 1:
                    chestFunctions.openChestDelay(chestPlayer, "hchest");
                    break;

                case 2:
                    chestFunctions.openChestDelay(chestPlayer, "mchest");
                    break;

                case 3:
                    chestFunctions.openChestDelay(chestPlayer, "rchest");
                    break;

                case 4:
                    chestFunctions.openChestDelay(chestPlayer, "schest");
                    break;

                case 5:
                    chestFunctions.openChestDelay(chestPlayer, "vchest");
                    break;

                case 6:
                    chestFunctions.openChestDelay(chestPlayer, "xchest");
                    break;

            }

        }

    }

}
