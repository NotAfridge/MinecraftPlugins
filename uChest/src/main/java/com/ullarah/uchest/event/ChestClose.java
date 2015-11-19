package com.ullarah.uchest.event;

import com.ullarah.uchest.ChestFunctions;
import com.ullarah.uchest.function.CommonString;
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

import static com.ullarah.uchest.ChestInit.*;

public class ChestClose implements Listener {

    private final CommonString commonString = new CommonString();

    @EventHandler
    public void event(final InventoryCloseEvent event) {

        ChestFunctions chestFunctions = new ChestFunctions();

        Inventory chestInventory = event.getInventory();
        Player chestPlayer = (Player) event.getPlayer();

        if (chestInventory.getName().matches("§2Experience Chest")) {
            ItemStack[] expItems = chestInventory.getContents();
            chestFunctions.convertItem(chestPlayer, "XP", expItems);
            chestInventory.clear();
        }

        if (chestInventory.getName().matches("§2Money Chest")) {
            ItemStack[] moneyItems = chestInventory.getContents();
            chestFunctions.convertItem(chestPlayer, "MONEY", moneyItems);
            chestInventory.clear();
        }

        if (chestInventory.getName().matches("§2Random Chest")) {
            chestRandomTask.entrySet().stream().filter(e ->
                    chestPlayer.getUniqueId().equals(e.getKey())).forEach(e -> {

                e.getValue().cancel();
                chestRandomTask.remove(e.getKey());

            });
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

        if (chestInventory.getName().matches("§2Hold Chest")) openHoldVault(chestPlayer, chestInventory, "hold");
        if (chestInventory.getName().matches("§2Vault Chest")) openHoldVault(chestPlayer, chestInventory, "vault");

    }

    private void openHoldVault(Player chestPlayer, Inventory chestInventory, String type) {

        File vaultFile = new File(getPlugin().getDataFolder() + File.separator + type, chestPlayer.getUniqueId().toString() + ".yml");

        if (vaultFile.exists()) {

            FileConfiguration vaultConfig = YamlConfiguration.loadConfiguration(vaultFile);
            vaultConfig.set("item", chestInventory.getContents());

            try {
                vaultConfig.save(vaultFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else commonString.messageSend(getPlugin(), chestPlayer, ChatColor.RED + "Error saving vault chest contents.");

    }

}
