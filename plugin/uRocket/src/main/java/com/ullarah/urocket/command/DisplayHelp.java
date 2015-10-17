package com.ullarah.urocket.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.urocket.RocketInit.getMsgPrefix;

public class DisplayHelp {

    public static void runHelp(CommandSender sender) {

        if (sender instanceof Player) {

            sender.sendMessage(new String[]{
                    getMsgPrefix() + ChatColor.YELLOW + "Please visit the below URL for more information:",
                    getMsgPrefix() + ChatColor.GREEN + "   https://goo.gl/uiD5k3"
            });

            if (sender.hasPermission("rocket.staff")) {

                sender.sendMessage(new String[]{
                        ChatColor.RED + "----- Staff Commands -----",
                        ChatColor.GOLD + " ▪ /rocket chest",
                        ChatColor.YELLOW + "   Displays all Rocket Components."
                });

            }

        }

    }

}
