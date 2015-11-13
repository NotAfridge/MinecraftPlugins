package com.ullarah.urocket.event;

import org.bukkit.ChatColor;
import org.bukkit.Material;
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
import static com.ullarah.urocket.RocketLanguage.RB_USELESS;

public class InventoryClick implements Listener {

    @EventHandler
    public void playerInventoryClick(InventoryClickEvent event) {

        if (event.getClickedInventory() == null) return;

        if (event.getInventory() instanceof CraftingInventory) {

            if (event.getSlotType() == InventoryType.SlotType.RESULT) {

                ItemStack itemStack = event.getCurrentItem();

                if (itemStack.hasItemMeta()) if (itemStack.getItemMeta().hasDisplayName())
                    if (itemStack.getItemMeta().getDisplayName().equals(RB_USELESS))
                        event.setCancelled(true);

            }

            if (event.getSlotType() == InventoryType.SlotType.ARMOR && event.getRawSlot() == 8)
                interactRocketBoots(event, event.getWhoClicked().getItemOnCursor());

            if (event.isShiftClick()) interactRocketBoots(event, event.getCurrentItem());

        }

        if (event.getInventory() instanceof HorseInventory) {

            if (event.getRawSlot() == 0) {

                Horse horse = (Horse) event.getClickedInventory().getHolder();
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

        if (event.getClickedInventory() == null) return;

        String inventoryName = ChatColor.DARK_RED + "Rocket Components";

        if (event.getRawSlot() <= 53 && inventoryName.equals(event.getClickedInventory().getTitle())) {

            Player player = (Player) event.getWhoClicked();
            ItemStack componentType = event.getCurrentItem();

            componentType.setAmount(1);
            event.setCancelled(true);

            player.getItemOnCursor().setType(Material.AIR);
            player.getInventory().addItem(new ItemStack(componentType));
        }

    }

}
