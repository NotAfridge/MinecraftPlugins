package com.ullarah.upostal.command;

import com.ullarah.ulib.function.ProfileUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

import static com.ullarah.upostal.PostalInit.*;

public class Blacklist {

    public static void toggle(CommandSender sender, String[] args) {

        if (!getMaintenanceCheck()) {

            if (sender.hasPermission("postal.blacklist")) {

                if (args.length >= 2) try {

                    File inboxFile = new File(getInboxDataPath(), ProfileUtils.lookup(args[1]).getId().toString() + ".yml");

                    if (inboxFile.exists()) {

                        FileConfiguration inboxConfig = YamlConfiguration.loadConfiguration(inboxFile);

                        String inboxPlayerName = (String) inboxConfig.get("name");
                        Boolean inboxBlacklist = (Boolean) inboxConfig.get("blacklist");

                        if (inboxBlacklist == null) {

                            // Added for existing yml files
                            // This won't be needed for future installs.
                            inboxConfig.set("blacklist", true);
                            sender.sendMessage(getMsgPrefix()
                                    + ChatColor.RED + inboxPlayerName + " is now blacklisted.");

                        } else {

                            if (inboxBlacklist) {

                                inboxConfig.set("blacklist", false);
                                sender.sendMessage(getMsgPrefix()
                                        + ChatColor.GREEN + inboxPlayerName + " no longer blacklisted.");

                            } else {

                                inboxConfig.set("blacklist", true);
                                sender.sendMessage(getMsgPrefix()
                                        + ChatColor.RED + inboxPlayerName + " is now blacklisted.");

                            }

                        }

                        inboxConfig.save(inboxFile);

                    } else sender.sendMessage(getMsgPrefix() + "That player does not have an inbox!");

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }

        } else sender.sendMessage(getMaintenanceMessage());

    }


}
