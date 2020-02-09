package com.ullarah.uchest.event;

import com.ullarah.uchest.ChestFunctions;
import com.ullarah.uchest.ChestInit;
import com.ullarah.uchest.init.ChestLanguage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;

public class ChestClick implements Listener {

    private void chestDonatePlayerUnlock(Player player) {

        new BukkitRunnable() {

            int c = ChestInit.getPlugin().getConfig().getInt("dchest.random.itemsecond");

            @Override
            public void run() {
                if (c <= 0) {
                    ChestInit.chestLockoutMap.get("dchest_itemlock").remove(player.getUniqueId());
                    this.cancel();
                    return;
                }
                ChestInit.chestLockoutMap.get("dchest_itemlock").put(player.getUniqueId(), c);
                c--;
            }

        }.runTaskTimer(ChestInit.getPlugin(), 0, 20);

    }

    @EventHandler
    public void event(final InventoryClickEvent event) {

        ChestFunctions chestFunctions = new ChestFunctions();

        Inventory chestInventory = event.getClickedInventory();
        InventoryView view = event.getView();
        Player chestPlayer = (Player) event.getWhoClicked();

        if (chestInventory == null) return;

        ItemStack clicked = event.getCurrentItem();

        if (view.getTitle().matches(ChestLanguage.N_DCHEST)) {
            if (ChestInit.chestDonateLock && event.getRawSlot() <= 53)
                if (ChestInit.chestLockoutMap.get("dchest_itemlock").containsKey(chestPlayer.getUniqueId())) {

                    event.setCancelled(true);
                    event.getCursor().setType(Material.AIR);

                } else chestDonatePlayerUnlock(chestPlayer);
        }

        if (view.getTitle().matches(ChestLanguage.N_RCHEST))
            if (clicked != null) {
                if (clicked.getAmount() >= 1 && event.getRawSlot() >= chestInventory.getSize()) {
                    event.setCancelled(true);
                    event.getCursor().setType(Material.AIR);
                }
            }

        if (view.getTitle().matches(ChestLanguage.N_ECHEST)) {
            for (int s : new int[]{0, 1, 2, 3, 5, 6, 7, 8})
                if (event.getRawSlot() == s) {
                    event.setCancelled(true);
                    event.getCursor().setType(Material.AIR);
                }
        }

        if (view.getTitle().matches(ChestLanguage.N_SCHEST)) {
            if (clicked != null) {
                if (event.getClick().isShiftClick()) {
                    event.setCancelled(true);
                    event.getCursor().setType(Material.AIR);
                } else if (clicked.getAmount() >= 1 && event.getRawSlot() >= chestInventory.getSize()) {
                    event.setCancelled(true);
                    event.getCursor().setType(Material.AIR);
                } else {
                    Player player = (Player) event.getWhoClicked();
                    player.getWorld().dropItemNaturally(player.getLocation(), clicked);
                    event.getCursor().setType(Material.AIR);
                    event.setCancelled(true);
                    player.closeInventory();
                }
            }
        }

        if (view.getTitle().matches("" + ChatColor.GOLD + ChatColor.BOLD + "Mixed Chests")) {

            event.setCancelled(true);
            event.getCursor().setType(Material.AIR);
            chestPlayer.closeInventory();

            List<String> chestArray = Arrays.asList("d", "e", "h", "m", "r", "s", "v", "w", "x");
            for (String c : chestArray)
                if (event.getRawSlot() == chestArray.indexOf(c))
                    chestFunctions.openChestDelay(chestPlayer, c + "chest");

        }

    }

}
