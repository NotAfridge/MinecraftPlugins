package com.ullarah.uchest.event;

import com.ullarah.uchest.ChestFunctions;
import com.ullarah.uchest.ChestFunctions.ValidChest;
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

import static com.ullarah.uchest.ChestFunctions.ValidChest.*;
import static com.ullarah.uchest.ChestInit.*;
import static com.ullarah.uchest.init.ChestLanguage.*;

public class ChestClose implements Listener {

    private final CommonString commonString = new CommonString();

    @EventHandler
    public void event(final InventoryCloseEvent event) {

        ChestFunctions chestFunctions = new ChestFunctions();

        Inventory chestInventory = event.getInventory();
        Player chestPlayer = (Player) event.getPlayer();

        if (chestInventory.getName().matches(N_ECHEST)) {
            chestFunctions.enchantItem(chestPlayer, chestInventory.getItem(4));
            chestInventory.clear();
        }

        if (chestInventory.getName().matches(N_XCHEST)) {
            chestFunctions.convertItem(chestPlayer, XP, chestInventory.getContents());
            chestInventory.clear();
        }

        if (chestInventory.getName().matches(N_MCHEST)) {
            chestFunctions.convertItem(chestPlayer, MONEY, chestInventory.getContents());
            chestInventory.clear();
        }

        if (chestInventory.getName().matches(N_RCHEST)) {
            chestRandomTask.entrySet().stream().filter(e ->
                    chestPlayer.getUniqueId().equals(e.getKey())).forEach(e -> {

                e.getValue().cancel();
                chestRandomTask.remove(e.getKey());

            });
        }

        if (chestInventory.getName().matches(N_WCHEST)) {
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

        if (chestInventory.getName().matches(N_HCHEST))
            openChestType(chestPlayer, chestPlayer.getUniqueId(), chestInventory, HOLD);
        if (chestInventory.getName().matches(N_HCHEST + " - (.*)")) {
            String chestOwner = chestInventory.getName().substring(15);
            UUID chestOwnerUUID = new PlayerProfile().lookup(chestOwner).getId();
            openChestType(chestPlayer, chestOwnerUUID, chestInventory, HOLD);
        }

        if (chestInventory.getName().matches(N_VCHEST))
            openChestType(chestPlayer, chestPlayer.getUniqueId(), chestInventory, VAULT);
        if (chestInventory.getName().matches(N_VCHEST + " - (.*)")) {
            String chestOwner = chestInventory.getName().substring(15);
            UUID chestOwnerUUID = new PlayerProfile().lookup(chestOwner).getId();
            openChestType(chestPlayer, chestOwnerUUID, chestInventory, VAULT);
        }

    }

    private void openChestType(Player player, UUID chestPlayer, Inventory chestInventory, ValidChest type) {

        File chestFile = new File(getPlugin().getDataFolder() + File.separator + type, chestPlayer.toString() + ".yml");

        if (chestFile.exists()) {

            FileConfiguration chestConfig = YamlConfiguration.loadConfiguration(chestFile);
            chestConfig.set("item", chestInventory.getContents());

            try {
                chestConfig.save(chestFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else commonString.messageSend(getPlugin(), player, ChatColor.RED + "Error saving vault chest contents.");

    }

}
