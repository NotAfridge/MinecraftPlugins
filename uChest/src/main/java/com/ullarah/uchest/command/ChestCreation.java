package com.ullarah.uchest.command;

import com.ullarah.uchest.ChestFunctions.ValidChest;
import com.ullarah.uchest.ChestInit;
import com.ullarah.uchest.function.CommonString;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class ChestCreation {

    public void create(CommandSender sender, ValidChest type, Boolean display) {

        CommonString commonString = new CommonString();

        Player player = (Player) sender;

        File chestFile = new File(ChestInit.getPlugin().getDataFolder() + File.separator + type.toString(),
                player.getUniqueId().toString() + ".yml");

        if (!chestFile.exists()) {

            boolean chestFileCreation = false;

            File dataDir = ChestInit.getPlugin().getDataFolder();
            if (!dataDir.exists()) chestFileCreation = dataDir.mkdir();

            File chestDir = new File(dataDir + File.separator + type.toString());
            if (!chestDir.exists()) chestFileCreation = chestDir.mkdir();

            File chestFileNew = new File(chestDir, player.getUniqueId().toString() + ".yml");
            if (!chestFileNew.exists()) try {
                chestFileCreation = chestFileNew.createNewFile();
            } catch (IOException e) {
                commonString.messageSend(ChestInit.getPlugin(), player, ChatColor.RED + "Error creating chest.");
                e.printStackTrace();
            }

            if (chestFileCreation) {

                FileConfiguration chestConfig = YamlConfiguration.loadConfiguration(chestFileNew);

                chestConfig.set("name", player.getName());
                chestConfig.set("slots", 9);
                chestConfig.set("item", new ArrayList<>());

                try {

                    chestConfig.save(chestFileNew);

                } catch (IOException e) {
                    commonString.messageSend(ChestInit.getPlugin(), player, ChatColor.RED + "Error saving chest.");
                    e.printStackTrace();
                }

            } else {
                commonString.messageSend(ChestInit.getPlugin(), player, ChatColor.RED + "Error creating chest.");
                player.closeInventory();
            }

        }

        if (display) new ChestPrepare().prepare(player, player.getUniqueId(), type);

    }

    public UUID create(Player player, Inventory inventory) {

        CommonString commonString = new CommonString();
        UUID uuid = UUID.randomUUID();

        File chestFile = new File(ChestInit.getPlugin().getDataFolder() + File.separator + "chest",
                uuid.toString() + ".yml");

        if (!chestFile.exists()) {

            boolean chestFileCreation = false;

            File dataDir = ChestInit.getPlugin().getDataFolder();
            if (!dataDir.exists()) chestFileCreation = dataDir.mkdir();

            File chestDir = new File(dataDir + File.separator + "chest");
            if (!chestDir.exists()) chestFileCreation = chestDir.mkdir();

            File chestFileNew = new File(chestDir, uuid.toString() + ".yml");
            if (!chestFileNew.exists()) try {
                chestFileCreation = chestFileNew.createNewFile();
            } catch (IOException e) {
                commonString.messageSend(ChestInit.getPlugin(), player, ChatColor.RED + "Error creating chest.");
                e.printStackTrace();
            }

            if (chestFileCreation) {

                FileConfiguration chestConfig = YamlConfiguration.loadConfiguration(chestFileNew);

                chestConfig.set("p_name", player.getName());
                chestConfig.set("p_uuid", player.getUniqueId().toString());
                chestConfig.set("location", inventory.getLocation());
                chestConfig.set("contents", inventory.getContents());

                try {

                    chestConfig.save(chestFileNew);
                    return uuid;

                } catch (IOException e) {
                    commonString.messageSend(ChestInit.getPlugin(), player, ChatColor.RED + "Error saving chest.");
                    e.printStackTrace();
                }

            } else {
                commonString.messageSend(ChestInit.getPlugin(), player, ChatColor.RED + "Error creating chest.");
                player.closeInventory();
            }

        }

        return null;

    }

}
