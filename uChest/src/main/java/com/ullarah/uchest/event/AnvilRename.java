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
import org.bukkit.inventory.ItemStack;

public class AnvilRename implements Listener {

    @EventHandler
    public void event(InventoryClickEvent event) {

        CommonString s = new CommonString();

        Inventory i = event.getClickedInventory();

        if (i == null) return;

        if (i instanceof AnvilInventory) {

            ItemStack c = event.getCursor();

            if (c != null && c.hasItemMeta()) {

                if (c.getItemMeta().hasDisplayName()) {

                    Player p = (Player) event.getWhoClicked();
                    String d = c.getItemMeta().getDisplayName();

                    if (d.matches("" + ChatColor.YELLOW + ChatColor.BOLD + "Content Chest(.*)")) {

                        p.closeInventory();
                        s.messageSend(ChestInit.getPlugin(), p,
                                "Please rename chest using "
                                        + ChatColor.YELLOW + "/pchest [name]");

                    }

                    if (d.matches(ChatColor.YELLOW + "Chest Pickup Tool")) {

                        p.closeInventory();
                        s.messageSend(ChestInit.getPlugin(), p,
                                "Chest Pickup Tool cannot be modified.");

                    }

                }

            }

        }

    }

}
