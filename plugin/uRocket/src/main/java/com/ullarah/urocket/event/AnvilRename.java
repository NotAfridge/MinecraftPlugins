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

import static com.ullarah.ulib.function.CommonString.messageSend;
import static com.ullarah.urocket.RocketInit.getPlugin;
import static com.ullarah.urocket.RocketLanguage.RB_MOD_ERROR;

public class AnvilRename implements Listener {

    @EventHandler
    public static void onInventoryClick(InventoryClickEvent event) {

        Inventory inventory = event.getClickedInventory();

        if (inventory == null) return;

        if (inventory instanceof AnvilInventory) {

            if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {

                if (event.getCurrentItem().getItemMeta().hasDisplayName()) {

                    Set<String> rocketItems = new HashSet<>(Arrays.asList(
                            "Rocket Boots",
                            "Rocket Repair Station",
                            "Rocket Repair Tank",
                            "Rocket Repair Stand",
                            "Rocket Control",
                            "Rocket Fly Zone Controller",
                            "Rocket Booster",
                            "Variant Booster",
                            "Rocket Saddle",
                            "Self Repair Enhancement",
                            "Fuel Efficient Enhancement",
                            "Solar Enhancement"));

                    ItemStack currentItem = event.getCurrentItem();

                    if (rocketItems.contains(ChatColor.stripColor(currentItem.getItemMeta().getDisplayName()))) {

                        if (currentItem.getItemMeta().hasLore()) {

                            if (!currentItem.getItemMeta().getLore().get(0).equals(ChatColor.YELLOW + "Rocket Level X")) {

                                Player player = (Player) event.getWhoClicked();
                                player.closeInventory();
                                messageSend(getPlugin(), player, true, RB_MOD_ERROR);

                            }

                        }

                    }

                }

            }

        }

    }

}
