package com.ullarah.upostal.command.inbox;

import com.ullarah.upostal.function.CommonString;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

import static com.ullarah.upostal.PostalInit.getInboxDataPath;
import static com.ullarah.upostal.PostalInit.getPlugin;

public class Upgrade {

    public void run(CommandSender sender) {

        CommonString commonString = new CommonString();
        Player player = (Player) sender;

        File inboxFile = new File(getInboxDataPath(), player.getUniqueId().toString() + ".yml");

        if (inboxFile.exists()) {

            FileConfiguration inboxConfig = YamlConfiguration.loadConfiguration(inboxFile);

            int inboxPlayerSlot = inboxConfig.getInt("slot");

            if (inboxPlayerSlot >= 54) {
                commonString.messageSend(getPlugin(), player, ChatColor.AQUA + "You have the maximum number of slots!");
                return;
            }

            if (player.getLevel() >= 50) {

                inboxConfig.set("slot", inboxPlayerSlot + 9);
                player.setLevel(player.getLevel() - 50);

                try {
                    inboxConfig.save(inboxFile);
                    commonString.messageSend(getPlugin(), player,
                            ChatColor.YELLOW + "You upgraded your inbox! You now have " +
                                    ChatColor.GREEN + (inboxPlayerSlot + 9) + ChatColor.YELLOW + " slots!");
                } catch (IOException e) {
                    commonString.messageSend(getPlugin(), player, ChatColor.RED + "Slot Update Error!");
                }

            } else commonString.messageSend(getPlugin(), player,
                    ChatColor.YELLOW + "You need at least" + ChatColor.GOLD + " 50xp levels " + ChatColor.YELLOW + "to upgrade!");

        }

    }

}