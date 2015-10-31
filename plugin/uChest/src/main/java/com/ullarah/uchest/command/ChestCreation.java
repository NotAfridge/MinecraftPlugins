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

import static com.ullarah.uchest.ChestInit.getPlugin;
import static com.ullarah.uchest.command.ChestPrepare.prepare;
import static com.ullarah.ulib.function.CommonString.messagePlayer;

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
                messagePlayer(getPlugin(), player, new String[]{ChatColor.RED + "Error creating chest."});
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
                    messagePlayer(getPlugin(), player, new String[]{ChatColor.RED + "Error saving chest."});
                    e.printStackTrace();
                }

            } else {
                messagePlayer(getPlugin(), player, new String[]{ChatColor.RED + "Error creating chest."});
                player.closeInventory();
            }

        }

        if (display) prepare(player, null, type);

    }

}
