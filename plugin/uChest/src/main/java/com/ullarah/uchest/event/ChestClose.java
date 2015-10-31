package com.ullarah.uchest.event;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

import static com.ullarah.uchest.ChestFunctions.convertItem;
import static com.ullarah.uchest.ChestInit.*;
import static com.ullarah.ulib.function.CommonString.messagePlayer;

public class ChestClose implements Listener {

    @EventHandler
    public void event(final InventoryCloseEvent event) throws IOException {

        Inventory chestInventory = event.getInventory();
        Player chestPlayer = (Player) event.getPlayer();

        if (chestInventory.getName().matches("§2Experience Chest")) {
            ItemStack[] expItems = chestInventory.getContents();
            convertItem(chestPlayer, "XP", expItems);
            chestInventory.clear();
        }

        if (chestInventory.getName().matches("§2Money Chest")) {
            ItemStack[] moneyItems = chestInventory.getContents();
            convertItem(chestPlayer, "MONEY", moneyItems);
            chestInventory.clear();
        }

        if (chestInventory.getName().matches("§2Swap Chest")) {
            ItemStack[] checkItems = chestInventory.getContents();

            for (ItemStack item : checkItems)
                if (item != null) {
                    if (chestSwapItemStack != null) for (ItemStack swapItem : chestSwapItemStack)
                        if (swapItem != null)
                            chestPlayer.getWorld().dropItemNaturally(chestPlayer.getEyeLocation(), swapItem);
                    chestSwapItemStack = checkItems;
                    break;
                }

            chestSwapPlayer = null;
            chestSwapBusy = false;
        }

        if (chestInventory.getName().matches("§2Hold Chest")) {

            File holdFile = new File(getPlugin().getDataFolder() + File.separator + "hold",
                    chestPlayer.getUniqueId().toString() + ".yml");

            if (holdFile.exists()) {

                FileConfiguration holdConfig = YamlConfiguration.loadConfiguration(holdFile);

                holdConfig.set("item", chestInventory.getContents());
                holdConfig.save(holdFile);

            } else messagePlayer(getPlugin(), chestPlayer, new String[]{
                    ChatColor.RED + "Error saving holding chest contents."
            });

        }

        if (chestInventory.getName().matches("§2Vault Chest")) {

            File vaultFile = new File(getPlugin().getDataFolder() + File.separator + "vault",
                    chestPlayer.getUniqueId().toString() + ".yml");

            if (vaultFile.exists()) {

                FileConfiguration vaultConfig = YamlConfiguration.loadConfiguration(vaultFile);

                vaultConfig.set("item", chestInventory.getContents());
                vaultConfig.save(vaultFile);

            } else messagePlayer(getPlugin(), chestPlayer, new String[]{
                    ChatColor.RED + "Error saving vault chest contents."
            });

        }

    }

}
