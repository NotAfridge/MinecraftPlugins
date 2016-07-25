package com.ullarah.uchest.event;

import com.ullarah.uchest.ChestInit;
import com.ullarah.uchest.function.CommonString;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;

public class AnvilRename implements Listener {

    @EventHandler
    public void event(InventoryClickEvent event) {

        CommonString commonString = new CommonString();

        Inventory inventory = event.getClickedInventory();

        if (inventory == null) return;

        if (inventory instanceof AnvilInventory) {

            if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {

                if (event.getCurrentItem().getItemMeta().hasDisplayName()) {

                    Player player = (Player) event.getWhoClicked();

                    if (event.getCurrentItem().getItemMeta().getDisplayName()
                            .matches("" + ChatColor.YELLOW + ChatColor.BOLD + "Content Chest(.*)")) {

                        player.closeInventory();
                        commonString.messageSend(ChestInit.getPlugin(), player,
                                "Please rename chest using "
                                        + ChatColor.YELLOW + "/pchest [name]");

                    }

                    if (event.getCurrentItem().getItemMeta().getDisplayName()
                            .matches(ChatColor.YELLOW + "Chest Pickup Tool")) {

                        player.closeInventory();
                        commonString.messageSend(ChestInit.getPlugin(), player,
                                "Chest Pickup Tool cannot be modified.");

                    }

                }

            }

        }

    }

}
