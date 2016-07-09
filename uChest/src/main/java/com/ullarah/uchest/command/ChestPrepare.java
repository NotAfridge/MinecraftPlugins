package com.ullarah.uchest.command;

import com.ullarah.uchest.ChestFunctions;
import com.ullarah.uchest.ChestInit;
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

public class ChestPrepare {

    final CommonString commonString = new CommonString();

    public void prepare(Player player, UUID uuid, ChestFunctions.ValidChest type) {

        File chestFile = new File(ChestInit.getPlugin().getDataFolder() + File.separator + type, uuid.toString() + ".yml");
        FileConfiguration chestConfig = YamlConfiguration.loadConfiguration(chestFile);

        Inventory chestInventory = player.getUniqueId().equals(uuid) ? ownedChest(player, type, chestConfig, chestFile)
                : modifyChest(player, type, chestConfig);

        if (chestConfig.get("item") != null) {

            ArrayList<ItemStack> itemStack = chestConfig.getList("item").stream().map(inboxCurrentItem
                    -> (ItemStack) inboxCurrentItem).collect(Collectors.toCollection(ArrayList::new));

            chestInventory.setContents(itemStack.toArray(new ItemStack[itemStack.size()]));

        }

        new ChestFunctions().chestView(player, uuid, chestInventory, type);

    }

    public void reset(Player player, UUID uuid, ChestFunctions.ValidChest type) {

        File chestFile = new File(ChestInit.getPlugin().getDataFolder() + File.separator + type, uuid.toString() + ".yml");
        FileConfiguration chestConfig = YamlConfiguration.loadConfiguration(chestFile);

        commonString.messageSend(ChestInit.getPlugin(), player, chestFile.delete() ? "" + ChatColor.RED + type + " chest is reset for "
                + ChatColor.YELLOW + chestConfig.getString("name") : ChatColor.RED + "Error removing " + type + " chest.");

    }

    private Inventory ownedChest(Player player, ChestFunctions.ValidChest type, FileConfiguration config, File file) {

        int playerLevel = player.getLevel();
        int playerSlotLevel = config.getInt("slots");

        if (!player.getPlayerListName().equals(config.getString("name")))
            config.set("name", player.getPlayerListName());

        if (type == ChestFunctions.ValidChest.HOLD) {
            if (playerLevel >= 15 && playerSlotLevel == 9) config.set("slots", 18);
            if (playerLevel >= 25 && playerSlotLevel == 18) config.set("slots", 27);
            if (playerLevel >= 50 && playerSlotLevel == 27) config.set("slots", 36);
            if (playerLevel >= 75 && playerSlotLevel == 36) config.set("slots", 45);
            if (playerLevel >= 100 && playerSlotLevel == 45) config.set("slots", 54);
        }

        try {
            config.save(file);
        } catch (IOException e) {
            commonString.messageSend(ChestInit.getPlugin(), player, ChatColor.RED + "Error saving existing " + type + " chest.");
            e.printStackTrace();
        }

        return Bukkit.createInventory(
                player, config.getInt("slots"), ChatColor.DARK_GREEN +
                        type.toString().substring(0, 1).toUpperCase() +
                        type.toString().substring(1) + " Chest");

    }

    private Inventory modifyChest(Player player, ChestFunctions.ValidChest type, FileConfiguration config) {

        return Bukkit.createInventory(
                player, config.getInt("slots"), ChatColor.DARK_GREEN +
                        type.toString().substring(0, 1).toUpperCase() +
                        type.toString().substring(1) + " Chest - " + config.get("name"));

    }

}
