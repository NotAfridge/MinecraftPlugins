package com.ullarah.uchest.event;

import com.ullarah.uchest.ChestFunctions;
import com.ullarah.uchest.function.CommonString;
import com.ullarah.uchest.function.PlayerProfile;
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
import java.util.UUID;

import static com.ullarah.uchest.ChestFunctions.validConvert.MONEY;
import static com.ullarah.uchest.ChestFunctions.validConvert.XP;
import static com.ullarah.uchest.ChestInit.*;

public class ChestClose implements Listener {

    private final CommonString commonString = new CommonString();

    @EventHandler
    public void event(final InventoryCloseEvent event) {

        ChestFunctions chestFunctions = new ChestFunctions();

        Inventory chestInventory = event.getInventory();
        Player chestPlayer = (Player) event.getPlayer();

        if (chestInventory.getName().matches(ChatColor.DARK_GREEN + "Enchantment Chest")) {
            chestFunctions.enchantItem(chestPlayer, chestInventory.getItem(4));
            chestInventory.clear();
        }

        if (chestInventory.getName().matches(ChatColor.DARK_GREEN + "Experience Chest")) {
            ItemStack[] expItems = chestInventory.getContents();
            chestFunctions.convertItem(chestPlayer, XP, expItems);
            chestInventory.clear();
        }

        if (chestInventory.getName().matches(ChatColor.DARK_GREEN + "Money Chest")) {
            ItemStack[] moneyItems = chestInventory.getContents();
            chestFunctions.convertItem(chestPlayer, MONEY, moneyItems);
            chestInventory.clear();
        }

        if (chestInventory.getName().matches(ChatColor.DARK_GREEN + "Random Chest")) {
            chestRandomTask.entrySet().stream().filter(e ->
                    chestPlayer.getUniqueId().equals(e.getKey())).forEach(e -> {

                e.getValue().cancel();
                chestRandomTask.remove(e.getKey());

            });
        }

        if (chestInventory.getName().matches(ChatColor.DARK_GREEN + "Swap Chest")) {
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

        if (chestInventory.getName().matches(ChatColor.DARK_GREEN + "Hold Chest"))
            openChestType(chestPlayer, chestPlayer.getUniqueId(), chestInventory, "hold");
        if (chestInventory.getName().matches(ChatColor.DARK_GREEN + "Vault Chest"))
            openChestType(chestPlayer, chestPlayer.getUniqueId(), chestInventory, "vault");

        if (chestInventory.getName().matches(ChatColor.DARK_GREEN + "Hold Chest - (.*)")) {
            String chestOwner = chestInventory.getName().substring(15);
            UUID chestOwnerUUID = new PlayerProfile().lookup(chestOwner).getId();
            openChestType(chestPlayer, chestOwnerUUID, chestInventory, "hold");
        }
        if (chestInventory.getName().matches(ChatColor.DARK_GREEN + "Vault Chest - (.*)")) {
            String chestOwner = chestInventory.getName().substring(15);
            UUID chestOwnerUUID = new PlayerProfile().lookup(chestOwner).getId();
            openChestType(chestPlayer, chestOwnerUUID, chestInventory, "vault");
        }

    }

    private void openChestType(Player player, UUID chestPlayer, Inventory chestInventory, String type) {

        File vaultFile = new File(getPlugin().getDataFolder() + File.separator + type, chestPlayer.toString() + ".yml");

        if (vaultFile.exists()) {

            FileConfiguration vaultConfig = YamlConfiguration.loadConfiguration(vaultFile);
            vaultConfig.set("item", chestInventory.getContents());

            try {
                vaultConfig.save(vaultFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else commonString.messageSend(getPlugin(), player, ChatColor.RED + "Error saving vault chest contents.");

    }

}
