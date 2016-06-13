package com.ullarah.uchest.event;

import com.ullarah.uchest.ChestFunctions;
import com.ullarah.uchest.ChestInit;
import org.bukkit.ChatColor;
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

            int c = getPlugin().getConfig().getInt("dchest.random.itemsecond");

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

        if (chestInventory.getName().matches(ChatColor.DARK_GREEN + "Donation Chest")) {
            if (chestDonateLock && event.getRawSlot() <= 53)
                if (chestDonateLockout.contains(chestPlayer.getUniqueId())) {

                    event.setCancelled(true);
                    event.getCursor().setType(Material.AIR);

                } else chestDonatePlayerUnlock(chestPlayer);
        }

        if (chestInventory.getName().matches(ChatColor.DARK_GREEN + "Random Chest"))
            if (event.getCurrentItem().getAmount() >= 1 && event.getRawSlot() >= 54) {
                event.setCancelled(true);
                event.getCursor().setType(Material.AIR);
            }

        if (chestInventory.getName().matches("" + ChatColor.GOLD + ChatColor.BOLD + "Mixed Chests")) {

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
                    if (ChestInit.allowMoneyChest) chestFunctions.openChestDelay(chestPlayer, "mchest");
                    else chestFunctions.openChestDelay(chestPlayer, "rchest");
                    break;

                case 3:
                    if (ChestInit.allowMoneyChest) chestFunctions.openChestDelay(chestPlayer, "rchest");
                    chestFunctions.openChestDelay(chestPlayer, "schest");
                    break;

                case 4:
                    if (ChestInit.allowMoneyChest) chestFunctions.openChestDelay(chestPlayer, "schest");
                    chestFunctions.openChestDelay(chestPlayer, "vchest");
                    break;

                case 5:
                    if (ChestInit.allowMoneyChest) chestFunctions.openChestDelay(chestPlayer, "vchest");
                    chestFunctions.openChestDelay(chestPlayer, "xchest");
                    break;

                case 6:
                    if (ChestInit.allowMoneyChest) chestFunctions.openChestDelay(chestPlayer, "xchest");
                    break;

            }

        }

    }

}
