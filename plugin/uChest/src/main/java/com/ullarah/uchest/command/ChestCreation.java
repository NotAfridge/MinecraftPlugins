package com.ullarah.uchest.command;

import com.ullarah.uchest.ChestFunctions.validStorage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.ullarah.uchest.ChestFunctions.validStorage.REMOTE;
import static com.ullarah.uchest.ChestInit.getMsgPrefix;
import static com.ullarah.uchest.ChestInit.getPlugin;
import static com.ullarah.uchest.command.ChestPrepare.prepare;

public class ChestCreation {

    public static void create(CommandSender sender, validStorage type, Boolean display) {

        Player player = (Player) sender;

        File chestFile = new File(getPlugin().getDataFolder() + File.separator + type.toString(),
                player.getUniqueId().toString() + ".yml");

        if (!chestFile.exists()) {

            boolean chestFileCreation = false;

            File dataDir = getPlugin().getDataFolder();
            if (!dataDir.exists()) chestFileCreation = dataDir.mkdir();

            File chestDir = new File(dataDir + File.separator + type.toString());
            if (!chestDir.exists()) chestFileCreation = chestDir.mkdir();

            File chestFileNew = new File(chestDir, player.getUniqueId().toString() + ".yml");
            if (!chestFileNew.exists()) try {
                chestFileCreation = chestFileNew.createNewFile();
            } catch (IOException e) {
                player.sendMessage(getMsgPrefix() + ChatColor.RED + "Error creating chest.");
                e.printStackTrace();
            }

            if (chestFileCreation) {

                FileConfiguration chestConfig = YamlConfiguration.loadConfiguration(chestFileNew);

                chestConfig.set("name", player.getName());

                if (type != REMOTE) {
                    chestConfig.set("slots", 9);
                    chestConfig.set("item", new ArrayList<>());
                } else chestConfig.set("chest", new ArrayList<>());

                try {

                    chestConfig.save(chestFileNew);

                } catch (IOException e) {
                    player.sendMessage(getMsgPrefix() + ChatColor.RED + "Error saving chest.");
                    e.printStackTrace();
                }

            } else {
                player.sendMessage(getMsgPrefix() + ChatColor.RED + "Error creating chest.");
                player.closeInventory();
            }

        } else if (display) prepare(player, null, type);

    }

}
