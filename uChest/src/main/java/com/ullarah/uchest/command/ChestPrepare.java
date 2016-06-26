package com.ullarah.uchest.command;

import com.ullarah.uchest.ChestFunctions;
import com.ullarah.uchest.function.CommonString;
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
import java.util.stream.Collectors;

import static com.ullarah.uchest.ChestFunctions.ValidChest;
import static com.ullarah.uchest.ChestFunctions.ValidChest.HOLD;
import static com.ullarah.uchest.ChestInit.getPlugin;

public class ChestPrepare {

    public void prepare(Player player, UUID uuid, ValidChest type) {

        CommonString commonString = new CommonString();

        File chestFile = new File(getPlugin().getDataFolder() + File.separator + type, uuid.toString() + ".yml");
        FileConfiguration chestConfig = YamlConfiguration.loadConfiguration(chestFile);

        Inventory chestInventory;

        UUID viewerUUID = player.getUniqueId();

        if (viewerUUID.equals(uuid)) {

            int playerLevel = player.getLevel();
            int playerSlotLevel = chestConfig.getInt("slots");
            String playerName = chestConfig.getString("name");

            if (!player.getPlayerListName().equals(playerName)) chestConfig.set("name", player.getPlayerListName());

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
                commonString.messageSend(getPlugin(), player, ChatColor.RED + "Error saving existing " + type + " chest.");
                e.printStackTrace();
            }

            chestInventory = Bukkit.createInventory(
                    player, chestConfig.getInt("slots"), ChatColor.DARK_GREEN +
                            type.toString().substring(0, 1).toUpperCase() +
                            type.toString().substring(1) + " Chest");

        } else chestInventory = Bukkit.createInventory(
                player, chestConfig.getInt("slots"), ChatColor.DARK_GREEN +
                        type.toString().substring(0, 1).toUpperCase() +
                        type.toString().substring(1) + " Chest - " + chestConfig.get("name"));

        if (chestConfig.get("item") != null) {

            ArrayList<ItemStack> itemStack = chestConfig.getList("item").stream().map(inboxCurrentItem
                    -> (ItemStack) inboxCurrentItem).collect(Collectors.toCollection(ArrayList::new));

            chestInventory.setContents(itemStack.toArray(new ItemStack[itemStack.size()]));

        }

        new ChestFunctions().chestView(player, uuid, chestInventory, type);

    }

}
