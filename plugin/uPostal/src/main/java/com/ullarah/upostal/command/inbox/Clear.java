package com.ullarah.upostal.command.inbox;

import com.ullarah.ulib.function.ProfileUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.ullarah.upostal.PostalInit.*;

public class Clear {

    public static void run(CommandSender sender, String[] args) {

        if (!getMaintenanceCheck()) {

            if (sender.hasPermission("postal.clear")) {

                if (!getMaintenanceCheck()) if (args.length >= 2) {

                    File inboxFile = new File(getInboxDataPath(), ProfileUtils.lookup(args[1]).getId().toString() + ".yml");

                    if (inboxFile.exists()) {

                        try {

                            FileConfiguration inboxConfig = YamlConfiguration.loadConfiguration(inboxFile);
                            inboxConfig.set("item", new ArrayList<>());
                            inboxConfig.save(inboxFile);

                            sender.sendMessage(getMsgPrefix() + ChatColor.GREEN + "Inbox cleared successfully!");

                        } catch (IOException e) {

                            sender.sendMessage(getMsgPrefix() + ChatColor.RED + "Inbox Clear Error!");
                            e.printStackTrace();

                        }

                    } else sender.sendMessage(getMsgPrefix() + "That player does not have an inbox!");

                } else sender.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "/postal clear <player>");

                else sender.sendMessage(getMaintenanceMessage());

            } else sender.sendMessage(getMsgPermDeny());

        } else sender.sendMessage(getMaintenanceMessage());

    }

}
