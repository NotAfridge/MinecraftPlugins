package com.ullarah.urocket.event;

import com.ullarah.urocket.RocketFunctions;
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

import java.util.ArrayList;
import java.util.UUID;

import static com.ullarah.urocket.RocketInit.rocketEntity;
import static com.ullarah.urocket.RocketInit.rocketJacket;
import static com.ullarah.urocket.init.RocketLanguage.RB_USELESS;

public class InventoryClick implements Listener {

    @EventHandler
    public void playerInventoryClick(InventoryClickEvent event) {

        RocketFunctions rocketFunctions = new RocketFunctions();

        if (event.getClickedInventory() == null) return;

        if (event.getInventory() instanceof CraftingInventory) {

            if (event.getSlotType() == InventoryType.SlotType.RESULT) {

                ItemStack itemStack = event.getCurrentItem();

                if (itemStack.hasItemMeta()) if (itemStack.getItemMeta().hasDisplayName())
                    if (itemStack.getItemMeta().getDisplayName().equals(RB_USELESS))
                        event.setCancelled(true);

            }

            if (event.getSlotType().equals(InventoryType.SlotType.ARMOR) && event.getRawSlot() == 8)
                rocketFunctions.interactRocketBoots(event, event.getWhoClicked().getItemOnCursor());

            if (event.getSlotType().equals(InventoryType.SlotType.ARMOR) && event.getRawSlot() == 6) {

                Player player = (Player) event.getWhoClicked();
                ItemStack itemCursor = event.getCursor();
                ItemStack itemCurrent = event.getCurrentItem();

                if (itemCursor.hasItemMeta()) if (itemCursor.getItemMeta().hasDisplayName())
                    if (itemCursor.getItemMeta().getDisplayName().equals(ChatColor.RED + "Rocket Boot Fuel Jacket")) {
                        rocketJacket.add(player.getUniqueId());
                        return;
                    }

                if (itemCurrent.hasItemMeta()) if (itemCurrent.getItemMeta().hasDisplayName())
                    if (itemCurrent.getItemMeta().getDisplayName().equals(ChatColor.RED + "Rocket Boot Fuel Jacket"))
                        if (player.isFlying()) {
                            rocketJacket.remove(player.getUniqueId());
                            rocketFunctions.disableRocketBoots(player, true, true, true, true, true, false);
                            return;
                        }

            }

            if (event.isShiftClick()) rocketFunctions.interactRocketBoots(event, event.getCurrentItem());

        }

        if (event.getInventory() instanceof HorseInventory) {

            if (event.getRawSlot() == 0) {

                Horse horse = (Horse) event.getClickedInventory().getHolder();
                UUID horseUUID = horse.getUniqueId();
                ItemStack saddle = event.getWhoClicked().getItemOnCursor();

                if (rocketFunctions.rocketSaddleCheck(saddle))
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
                add(Material.LOG);
                add(Material.WOOD);
                add(Material.REDSTONE_BLOCK);
                add(Material.REDSTONE);
                add(Material.GLOWSTONE);
                add(Material.GLOWSTONE_DUST);
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
