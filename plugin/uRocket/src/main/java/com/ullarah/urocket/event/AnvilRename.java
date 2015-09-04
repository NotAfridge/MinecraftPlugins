package com.ullarah.urocket.event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.ullarah.urocket.RocketInit.getMsgPrefix;

public class AnvilRename implements Listener {

    @EventHandler
    public static void onInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();

        if (inventory instanceof AnvilInventory) {

            if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {

                if (event.getCurrentItem().getItemMeta().hasDisplayName()) {

                    Set<String> rocketItems = new HashSet<>(Arrays.asList(
                            "Rocket Boots", "Rocket Repair Station", "Rocket Repair Tank", "Rocket Repair Stand",
                            "Rocket Control", "Rocket Fly Zone Controller", "Rocket Booster", "Variant Booster"));

                    ItemStack currentItem = event.getCurrentItem();

                    if (rocketItems.contains(ChatColor.stripColor(currentItem.getItemMeta().getDisplayName()))) {

                        if (currentItem.getItemMeta().hasLore()) {

                            if (!currentItem.getItemMeta().getLore().get(0).equals(
                                    ChatColor.YELLOW + "Rocket Level V")) {

                                player.closeInventory();
                                player.sendMessage(getMsgPrefix() + ChatColor.RED + "You cannot modify Rocket Equipment!");

                            }

                        }

                    }

                }

            }

        }

    }

}
