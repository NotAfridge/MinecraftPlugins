package com.ullarah.upostal.command.inbox;

import com.ullarah.ulib.function.CommonString;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

import static com.ullarah.upostal.PostalInit.*;

public class Upgrade {

    public static void run(CommandSender sender) {

        if (!getMaintenanceCheck()) {

            Player player = (Player) sender;

            File inboxFile = new File(getInboxDataPath(), player.getUniqueId().toString() + ".yml");

            if (inboxFile.exists()) {

                FileConfiguration inboxConfig = YamlConfiguration.loadConfiguration(inboxFile);

                int inboxPlayerSlot = inboxConfig.getInt("slot");

                if (inboxPlayerSlot >= 54) {

                    new CommonString().messageSend(getPlugin(), player, true, new String[]{
                            ChatColor.AQUA + "You have the maximum number of slots!"
                    });

                } else {

                    if (player.getLevel() >= 50) {

                        inboxConfig.set("slot", inboxPlayerSlot + 9);
                        player.setLevel(player.getLevel() - 50);

                        try {
                            inboxConfig.save(inboxFile);
                        } catch (IOException e) {
                            new CommonString().messageSend(getPlugin(), player, true, new String[]{ChatColor.RED + "Slot Update Error!"});
                        }

                        new CommonString().messageSend(getPlugin(), player, true, new String[]{
                                ChatColor.YELLOW + "You upgraded your inbox! You now have " +
                                        ChatColor.GREEN + (inboxPlayerSlot + 9) + ChatColor.YELLOW + " slots!"
                        });

                    } else {

                        new CommonString().messageSend(getPlugin(), player, true, new String[]{
                                ChatColor.YELLOW + "You need at least" +
                                        ChatColor.GOLD + " 50xp levels " + ChatColor.YELLOW + "to upgrade!"
                        });

                    }

                }

            }

        } else new CommonString().messageMaintenance(getPlugin(), sender);

    }

}
