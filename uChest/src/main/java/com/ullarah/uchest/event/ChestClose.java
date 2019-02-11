package com.ullarah.uchest.event;

import com.ullarah.uchest.ChestFunctions;
import com.ullarah.uchest.ChestFunctions.ValidChest;
import com.ullarah.uchest.ChestInit;
import com.ullarah.uchest.function.CommonString;
import com.ullarah.uchest.function.PlayerProfile;
import com.ullarah.uchest.init.ChestLanguage;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ChestClose implements Listener {

    private final CommonString commonString = new CommonString();

    @EventHandler
    public void event(final InventoryCloseEvent event) {

        ChestFunctions chestFunctions = new ChestFunctions();

        Inventory chestInventory = event.getInventory();
        Player chestPlayer = (Player) event.getPlayer();
        InventoryView view = event.getView();

        if (view.getTitle().matches(ChestLanguage.N_ECHEST)) {
            chestFunctions.enchantItem(chestPlayer, chestInventory.getItem(4));
            chestInventory.clear();
        }

        if (view.getTitle().matches(ChestLanguage.N_XCHEST)) {
            chestFunctions.convertItem(chestPlayer, ValidChest.XP, chestInventory.getContents());
            chestInventory.clear();
        }

        if (view.getTitle().matches(ChestLanguage.N_MCHEST)) {
            chestFunctions.convertItem(chestPlayer, ValidChest.MONEY, chestInventory.getContents());
            chestInventory.clear();
        }

        if (view.getTitle().matches(ChestLanguage.N_RCHEST)) {
            ChestInit.chestRandomTask.entrySet().stream().filter(e ->
                    chestPlayer.getUniqueId().equals(e.getKey())).forEach(e -> {

                e.getValue().cancel();
                ChestInit.chestRandomTask.remove(e.getKey());

            });
        }

        if (view.getTitle().matches(ChestLanguage.N_WCHEST)) {
            ItemStack[] checkItems = chestInventory.getContents();

            for (ItemStack item : checkItems)
                if (item != null) {
                    if (ChestInit.chestSwapItemStack != null) for (ItemStack swapItem : ChestInit.chestSwapItemStack)
                        if (swapItem != null)
                            chestPlayer.getWorld().dropItemNaturally(chestPlayer.getEyeLocation(), swapItem);
                    ChestInit.chestSwapItemStack = checkItems;
                    break;
                }

            ChestInit.chestSwapPlayer = null;
            ChestInit.chestSwapBusy = false;
        }

        if (view.getTitle().matches(ChestLanguage.N_HCHEST)) {
            openChestType(chestPlayer, chestPlayer.getUniqueId(), chestInventory, ValidChest.HOLD);
        }
        if (view.getTitle().matches(ChestLanguage.N_HCHEST + " - (.*)")) {
            String chestOwner = view.getTitle().substring(15);
            UUID chestOwnerUUID = new PlayerProfile().lookup(chestOwner).getId();
            openChestType(chestPlayer, chestOwnerUUID, chestInventory, ValidChest.HOLD);
        }

        if (view.getTitle().matches(ChestLanguage.N_VCHEST)) {
            openChestType(chestPlayer, chestPlayer.getUniqueId(), chestInventory, ValidChest.VAULT);
        }
        if (view.getTitle().matches(ChestLanguage.N_VCHEST + " - (.*)")) {
            String chestOwner = view.getTitle().substring(15);
            UUID chestOwnerUUID = new PlayerProfile().lookup(chestOwner).getId();
            openChestType(chestPlayer, chestOwnerUUID, chestInventory, ValidChest.VAULT);
        }

    }

    private void openChestType(Player player, UUID chestPlayer, Inventory chestInventory, ValidChest type) {

        File chestFile = new File(ChestInit.getPlugin().getDataFolder() + File.separator + type, chestPlayer.toString() + ".yml");

        if (chestFile.exists()) {

            FileConfiguration chestConfig = YamlConfiguration.loadConfiguration(chestFile);
            chestConfig.set("item", chestInventory.getContents());

            try {
                chestConfig.save(chestFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else
            commonString.messageSend(ChestInit.getPlugin(), player, ChatColor.RED + "Error saving chest contents.");

    }

}
