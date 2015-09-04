package com.ullarah.upostal.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.upostal.PostalInit.getMsgNoConsole;

public class Help {

    public static void display(CommandSender sender) {

        if (sender instanceof Player) {

            sender.sendMessage(new String[]{
                    ChatColor.AQUA + "uPostal Help",
                    "------------------------------------------",
                    ChatColor.GOLD + " ▪ /inbox",
                    ChatColor.YELLOW + "   Opens up your inbox.",
                    ChatColor.GOLD + " ▪ /inbox upgrade",
                    ChatColor.YELLOW + "   Purchase another inbox slot.",
                    ChatColor.GOLD + " ▪ /post <player>",
                    ChatColor.YELLOW + "   Opens up a players inbox for sending."
            });

            if (sender.hasPermission("postal.staff")) {

                sender.sendMessage(new String[]{
                        ChatColor.GOLD + " ▪ /postal blacklist <player>",
                        ChatColor.YELLOW + "   Will blacklist the players inbox.",
                        ChatColor.GOLD + " ▪ /postal clear <player>",
                        ChatColor.YELLOW + "   Will clear the players inbox.",
                        ChatColor.GOLD + " ▪ /postal maintenance <on|off>",
                        ChatColor.YELLOW + "   Toggles maintenance mode."
                });

            }

            sender.sendMessage("------------------------------------------");

        } else sender.sendMessage(getMsgNoConsole());

    }

}
