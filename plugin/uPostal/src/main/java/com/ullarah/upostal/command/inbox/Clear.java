package com.ullarah.upostal.command.inbox;

import com.ullarah.ulib.function.CommonString;
import com.ullarah.ulib.function.PlayerProfile;
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

                    File inboxFile = new File(getInboxDataPath(), new PlayerProfile().lookup(args[1]).getId().toString() + ".yml");

                    if (inboxFile.exists()) {

                        try {

                            FileConfiguration inboxConfig = YamlConfiguration.loadConfiguration(inboxFile);
                            inboxConfig.set("item", new ArrayList<>());
                            inboxConfig.save(inboxFile);

                            new CommonString().messageSend(getPlugin(), sender, true, new String[]{
                                    ChatColor.GREEN + "Inbox cleared successfully!"
                            });

                        } catch (IOException e) {

                            new CommonString().messageSend(getPlugin(), sender, true, new String[]{
                                    ChatColor.RED + "Inbox Clear Error!"
                            });
                            e.printStackTrace();

                        }

                    } else
                        new CommonString().messageSend(getPlugin(), sender, true, new String[]{"That player does not have an inbox!"});

                } else
                    new CommonString().messageSend(getPlugin(), sender, true, new String[]{ChatColor.YELLOW + "/postal clear <player>"});

            } else new CommonString().messagePermDeny(getPlugin(), sender);

        } else new CommonString().messageMaintenance(getPlugin(), sender);

    }

}
