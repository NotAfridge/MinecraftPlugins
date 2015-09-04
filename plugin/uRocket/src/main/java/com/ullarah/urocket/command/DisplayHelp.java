package com.ullarah.urocket.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DisplayHelp {

    public static void runHelp(CommandSender sender) {

        if (sender instanceof Player) {

            sender.sendMessage(new String[]{
                    ChatColor.AQUA + "Rocket Boot Help",
                    "------------------------------------------",
                    ChatColor.RESET + "1)  Create two rocket boosters.",
                    ChatColor.RESET + "2a) Create two rocket controls.",
                    ChatColor.RESET + "2b) Optionally create a rocket variant.",
                    ChatColor.RESET + "3)  Combine parts using iron ingots.",
                    ChatColor.RESET + "4)  Attach your Rocket Boots.",
                    ChatColor.RESET + "5)  Double tap Space Bar to start flying."
            });

            if (sender.hasPermission("rocket.staff")) {

                sender.sendMessage(new String[]{
                        "------------------------------------------",
                        ChatColor.GOLD + " ▪ /rocket chest",
                        ChatColor.YELLOW + "   Displays all Rocket Components."
                });

            }

        }

    }

}
