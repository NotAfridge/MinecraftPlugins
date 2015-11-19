package com.ullarah.uchest.command;

import com.ullarah.uchest.ChestFunctions.validStorage;
import com.ullarah.uchest.function.CommonString;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.ullarah.uchest.ChestInit.getPlugin;

public class ChestCreation {

    public void create(CommandSender sender, validStorage type, Boolean display) {

        CommonString commonString = new CommonString();

        Player player = (Player) sender;

        File chestFile = new File(getPlugin().getDataFolder() + File.separator + type.toString(), player.getUniqueId().toString() + ".yml");

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
                commonString.messageSend(getPlugin(), player, ChatColor.RED + "Error creating chest.");
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
                    commonString.messageSend(getPlugin(), player, ChatColor.RED + "Error saving chest.");
                    e.printStackTrace();
                }

            } else {
                commonString.messageSend(getPlugin(), player, ChatColor.RED + "Error creating chest.");
                player.closeInventory();
            }

        }

        if (display) new ChestPrepare().prepare(player, null, type);

    }

}
