package com.ullarah.urocket.event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

import static com.ullarah.urocket.RocketFunctions.interactRocketBoots;
import static com.ullarah.urocket.RocketFunctions.rocketSaddleCheck;
import static com.ullarah.urocket.RocketInit.rocketEntity;

public class InventoryClick implements Listener {

    @EventHandler
    public void playerInventoryClick(InventoryClickEvent event) {

        if (event.getInventory() instanceof CraftingInventory) {

            if (event.getSlotType() == InventoryType.SlotType.RESULT) {

                ItemStack itemStack = event.getCurrentItem();

                if (itemStack.hasItemMeta()) if (itemStack.getItemMeta().hasDisplayName())
                    if (itemStack.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Useless Rocket Boots"))
                        event.setCancelled(true);

            }

            if (event.getSlotType() == InventoryType.SlotType.ARMOR && event.getRawSlot() == 8)
                interactRocketBoots(event, event.getWhoClicked().getItemOnCursor());

            if (event.isShiftClick()) interactRocketBoots(event, event.getCurrentItem());

        }

        if (event.getInventory() instanceof HorseInventory) {

            if (event.getRawSlot() == 0) {

                Horse horse = (Horse) event.getInventory().getHolder();
                UUID horseUUID = horse.getUniqueId();
                ItemStack saddle = event.getWhoClicked().getItemOnCursor();

                if (rocketSaddleCheck(saddle)) {

                    if (!rocketEntity.containsKey(horseUUID)) rocketEntity.put(horseUUID, horse.getType());

                } else {

                    if (rocketEntity.containsKey(horseUUID)) rocketEntity.remove(horseUUID);

                }

            }

        }

    }

    @EventHandler
    public void infiniteRocketComponents(InventoryClickEvent event) {

        String inventoryName = event.getInventory().getTitle();

        if (inventoryName.matches(ChatColor.DARK_RED + "Rocket Components")) {
            Player player = (Player) event.getWhoClicked();
            ItemStack componentType = event.getCurrentItem();

            event.setCancelled(true);

            if (event.getRawSlot() == -999) event.getView().close();
            else player.getInventory().addItem(new ItemStack(componentType));

        }

    }

}
