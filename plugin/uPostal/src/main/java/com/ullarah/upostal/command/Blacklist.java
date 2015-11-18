package com.ullarah.upostal.command;

import com.ullarah.ulib.function.CommonString;
import com.ullarah.ulib.function.PlayerProfile;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

import static com.ullarah.upostal.PostalInit.*;

public class Blacklist {

    public static void toggle(CommandSender sender, String[] args) {

        if (!getMaintenanceCheck()) {

            if (sender.hasPermission("postal.blacklist")) {

                if (args.length >= 2) {

                    try {

                        File inboxFile = new File(getInboxDataPath(),
                                new PlayerProfile().lookup(args[1]).getId().toString() + ".yml");

                        if (inboxFile.exists()) {

                            FileConfiguration inboxConfig = YamlConfiguration.loadConfiguration(inboxFile);

                            String inboxPlayerName = (String) inboxConfig.get("name");
                            Boolean inboxBlacklist = (Boolean) inboxConfig.get("blacklist");

                            if (inboxBlacklist) {

                                inboxConfig.set("blacklist", false);
                                new CommonString().messageSend(getPlugin(), (Player) sender, true, new String[]{
                                        ChatColor.GREEN + inboxPlayerName + " no longer blacklisted."
                                });

                            } else {

                                inboxConfig.set("blacklist", true);
                                new CommonString().messageSend(getPlugin(), (Player) sender, true, new String[]{
                                        ChatColor.RED + inboxPlayerName + " is now blacklisted."
                                });

                            }

                            inboxConfig.save(inboxFile);

                        } else new CommonString().messageSend(getPlugin(), (Player) sender, true, new String[]{
                                "That player does not have an inbox!"});

                    } catch (IOException e) {

                        e.printStackTrace();

                    }

                } else new CommonString().messageSend(getPlugin(), (Player) sender, true, new String[]{
                        ChatColor.YELLOW + "/postal blacklist <player>"});

            }

        } else new CommonString().messageMaintenance(getPlugin(), sender);

    }


}
