package com.ullarah.urocket.event;

import com.ullarah.urocket.RocketFunctions;
import com.ullarah.urocket.function.CommonString;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

import static com.ullarah.urocket.RocketInit.*;
import static com.ullarah.urocket.init.RocketLanguage.RB_USELESS;

public class InventoryClick implements Listener {

    @EventHandler
    public void playerInventoryClick(InventoryClickEvent event) {

        RocketFunctions rocketFunctions = new RocketFunctions();
        CommonString commonString = new CommonString();

        if (event.getClickedInventory() == null) return;

        if (event.getInventory() instanceof CraftingInventory) {

            Player player = (Player) event.getWhoClicked();
            UUID playerUUID = player.getUniqueId();
            ItemStack itemCursor = event.getCursor();
            ItemStack itemCurrent = event.getCurrentItem();

            if (event.getSlotType() == InventoryType.SlotType.RESULT) {

                ItemStack itemStack = event.getCurrentItem();

                if (itemStack.hasItemMeta()) if (itemStack.getItemMeta().hasDisplayName())
                    if (itemStack.getItemMeta().getDisplayName().equals(RB_USELESS))
                        event.setCancelled(true);

            }

            if (event.getSlotType().equals(InventoryType.SlotType.ARMOR)) switch (event.getRawSlot()) {

                case 6:
                    if (rocketFunctions.isValidFuelJacket(itemCursor)) {
                        if (event.getAction().equals(InventoryAction.SWAP_WITH_CURSOR)) {
                            if (rocketFunctions.isValidFuelJacket(itemCurrent))
                                commonString.messageSend(getPlugin(), player, true, "Fuel Jacket Swapped");
                            else {
                                rocketJacket.add(playerUUID);
                                commonString.messageSend(getPlugin(), player, true, "Fuel Jacket Attached");
                            }
                            return;
                        }
                        rocketJacket.add(playerUUID);
                        commonString.messageSend(getPlugin(), player, true, "Fuel Jacket Attached");
                    } else {
                        rocketJacket.remove(playerUUID);
                        if (player.isFlying()) player.setFlying(false);
                    }
                    break;

                case 8:
                    if (rocketFunctions.isValidRocketBoots(itemCursor)) {
                        if (event.getAction().equals(InventoryAction.SWAP_WITH_CURSOR)) {
                            if (rocketFunctions.isValidRocketBoots(itemCurrent))
                                rocketFunctions.disableRocketBoots(player, false, false, true, false, false);
                            else {
                                rocketFunctions.disableRocketBoots(player, false, false, false, false, false);
                                return;
                            }
                        }
                        rocketFunctions.interactRocketBoots(event, itemCursor);
                    } else rocketFunctions.disableRocketBoots(player, false, false, false, false, false);
                    break;

            }

            if (event.isShiftClick()) {

                if (rocketFunctions.isValidFuelJacket(itemCurrent)) if (event.getRawSlot() != 6) {
                    rocketJacket.add(player.getUniqueId());
                    commonString.messageSend(getPlugin(), player, true, "Fuel Jacket Attached");
                }

                if (rocketFunctions.isValidRocketBoots(itemCurrent)) if (event.getRawSlot() != 8)
                    rocketFunctions.interactRocketBoots(event, itemCurrent);

            }

        }

        if (event.getInventory() instanceof HorseInventory) {

            if (event.getRawSlot() == 0) {

                Horse horse = (Horse) event.getClickedInventory().getHolder();
                UUID horseUUID = horse.getUniqueId();
                ItemStack saddle = event.getWhoClicked().getItemOnCursor();

                if (rocketFunctions.isValidRocketSaddle(saddle))
                    if (!rocketEntity.containsKey(horseUUID)) rocketEntity.put(horseUUID, horse.getType());
                    else if (rocketEntity.containsKey(horseUUID)) rocketEntity.remove(horseUUID);

            }

        }

    }

    @EventHandler
    public void fuelInventoryClick(InventoryClickEvent event) {

        if (event.getClickedInventory() == null) return;

        String inventoryName = "" + ChatColor.DARK_RED + ChatColor.BOLD + "Rocket Boot Fuel Jacket";

        if (inventoryName.equals(event.getInventory().getTitle())) {

            ArrayList<Material> allowedMaterial = new ArrayList<Material>() {{
                add(Material.AIR);
                add(Material.COAL);
                add(Material.COAL_BLOCK);
                add(Material.REDSTONE);
                add(Material.REDSTONE_BLOCK);
                add(Material.GLOWSTONE_DUST);
                add(Material.GLOWSTONE);
                add(Material.WOOD);
                add(Material.LOG);
            }};

            if (!allowedMaterial.contains(event.getCurrentItem().getType())) event.setCancelled(true);
            if (!allowedMaterial.contains(event.getCursor().getType())) event.setCancelled(true);

            if (event.getClick().isShiftClick())
                if (event.getClickedInventory() == event.getWhoClicked().getInventory())
                    if (!allowedMaterial.contains(event.getCurrentItem().getType()))
                        event.setCancelled(true);

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
