package com.ullarah.urocket.event;

import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.function.CommonString;
import com.ullarah.urocket.init.RocketLanguage;
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

public class AnvilRename implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        CommonString commonString = new CommonString();

        Inventory inventory = event.getClickedInventory();

        if (inventory == null) return;

        if (inventory instanceof AnvilInventory) {

            if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {

                if (event.getCurrentItem().getItemMeta().hasDisplayName()) {

                    Set<String> rocketItems = new HashSet<>(Arrays.asList(
                            "Rocket Boots",
                            "Rocket Boot Repair Station",
                            "Rocket Boot Repair Tank",
                            "Rocket Boot Repair Stand",
                            "Rocket Boot Control",
                            "Rocket Boot Fly Zone Controller",
                            "Rocket Boot Booster",
                            "Rocket Boot Saddle",
                            "Rocket Boot Fuel Jacket",
                            "Rocket Boot Variant",
                            "Rocket Boot Enhancement"));

                    ItemStack currentItem = event.getCurrentItem();

                    if (rocketItems.contains(ChatColor.stripColor(currentItem.getItemMeta().getDisplayName()))) {

                        if (currentItem.getItemMeta().hasLore()) {

                            if (!currentItem.getItemMeta().getLore().get(0).equals(ChatColor.YELLOW + "Rocket Level X")) {

                                Player player = (Player) event.getWhoClicked();
                                player.closeInventory();
                                commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_MOD_ERROR);

                            }

                        }

                    }

                }

            }

        }

    }

}
