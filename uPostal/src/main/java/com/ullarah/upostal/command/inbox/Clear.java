package com.ullarah.upostal.command.inbox;

import com.ullarah.upostal.function.CommonString;
import com.ullarah.upostal.function.PlayerProfile;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.ullarah.upostal.PostalInit.getInboxDataPath;
import static com.ullarah.upostal.PostalInit.getPlugin;

public class Clear {

    public void run(CommandSender sender, String[] args) {

        CommonString commonString = new CommonString();

        if (!sender.hasPermission("postal.clear")) {
            commonString.messagePermDeny(getPlugin(), sender);
            return;
        }

        if (args.length >= 2) {

            File inboxFile = new File(getInboxDataPath(), new PlayerProfile().lookup(args[1]).getId().toString() + ".yml");

            if (inboxFile.exists()) {

                try {

                    FileConfiguration inboxConfig = YamlConfiguration.loadConfiguration(inboxFile);
                    inboxConfig.set("item", new ArrayList<>());
                    inboxConfig.save(inboxFile);

                    commonString.messageSend(getPlugin(), sender, ChatColor.GREEN + "Inbox cleared successfully!");

                } catch (IOException e) {

                    commonString.messageSend(getPlugin(), sender, ChatColor.RED + "Inbox clear error!");
                    e.printStackTrace();

                }

            } else commonString.messageSend(getPlugin(), sender, "That player does not have an inbox!");

        } else commonString.messageSend(getPlugin(), sender, ChatColor.YELLOW + "/postal clear <player>");

    }

}
