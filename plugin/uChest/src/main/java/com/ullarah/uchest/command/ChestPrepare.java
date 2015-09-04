package com.ullarah.uchest.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import static com.ullarah.uchest.ChestFunctions.chestView;
import static com.ullarah.uchest.ChestFunctions.validStorage;
import static com.ullarah.uchest.ChestFunctions.validStorage.HOLD;
import static com.ullarah.uchest.ChestInit.getMsgPrefix;
import static com.ullarah.uchest.ChestInit.getPlugin;

class ChestPrepare {

    public static void prepare(Player owner, Player viewer, validStorage type) {

        UUID chestUUID = owner.getUniqueId();

        File chestFile = new File(getPlugin().getDataFolder() + File.separator + type, chestUUID.toString() + ".yml");
        FileConfiguration chestConfig = YamlConfiguration.loadConfiguration(chestFile);

        Inventory chestInventory;

        if (viewer == null) {

            int playerLevel = owner.getLevel();
            int playerSlotLevel = chestConfig.getInt("slots");
            String playerName = chestConfig.getString("name");

            if (!owner.getPlayerListName().equals(playerName)) chestConfig.set("name", owner.getPlayerListName());

            if (type == HOLD) {
                if (playerLevel >= 15 && playerSlotLevel == 9) chestConfig.set("slots", 18);
                if (playerLevel >= 25 && playerSlotLevel == 18) chestConfig.set("slots", 27);
                if (playerLevel >= 50 && playerSlotLevel == 27) chestConfig.set("slots", 36);
                if (playerLevel >= 75 && playerSlotLevel == 36) chestConfig.set("slots", 45);
                if (playerLevel >= 100 && playerSlotLevel == 45) chestConfig.set("slots", 54);
            }

            try {
                chestConfig.save(chestFile);
            } catch (IOException e) {
                owner.sendMessage(getMsgPrefix() + "Error saving existing " + type + " chest.");
                e.printStackTrace();
            }

            chestInventory = Bukkit.createInventory(
                    owner, chestConfig.getInt("slots"), ChatColor.DARK_GREEN +
                            type.toString().substring(0, 1).toUpperCase() +
                            type.toString().substring(1) + " Chest");

        } else chestInventory = Bukkit.createInventory(
                owner, chestConfig.getInt("slots"), ChatColor.DARK_GREEN +
                        type.toString().substring(0, 1).toUpperCase() +
                        type.toString().substring(1) + " Chest - " + chestConfig.get("name"));

        if (chestConfig.get("item") != null) {
            ArrayList<ItemStack> itemStack = new ArrayList<>();

            for (Object inboxCurrentItem : chestConfig.getList("item"))
                itemStack.add((ItemStack) inboxCurrentItem);

            chestInventory.setContents(itemStack.toArray(new ItemStack[itemStack.size()]));
        }

        chestView(owner, viewer, chestInventory, type);

    }

}
