package com.ullarah.uauction.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Help {

    public static void runHelp(CommandSender sender) {

        if (sender instanceof Player) {

            sender.sendMessage(new String[]{
                    ChatColor.AQUA + "Chest Bidding System Help",
                    "------------------------------------------",
                    ChatColor.GOLD + " ▪ /alist",
                    ChatColor.YELLOW + "   Lists open auctions for bidding.",
                    ChatColor.GOLD + " ▪ /aview <name>",
                    ChatColor.YELLOW + "   Opens up a auction chest for viewing.",
                    ChatColor.GOLD + " ▪ /ainfo <name>",
                    ChatColor.YELLOW + "   See the current bid and who is winning.",
                    ChatColor.GOLD + " ▪ /abid <name> <amount>",
                    ChatColor.YELLOW + "   Bids on a running auction.",
                    ChatColor.GOLD + " ▪ /acollect <name>",
                    ChatColor.YELLOW + "   Collect your winning auction chest.",
                    ChatColor.GOLD + " ▪ /awon",
                    ChatColor.YELLOW + "   Check your auction winnings.",
                    ChatColor.GOLD + " ▪ /achat",
                    ChatColor.YELLOW + "   Toggles auction chatter and advertising."
            });

            if (sender.hasPermission("uauction.staff")) {

                sender.sendMessage(new String[]{
                        "------------------------------------------",
                        ChatColor.GOLD + " ▪ /auction create <name> [hours] [reserve] [minbid] [silent]",
                        ChatColor.YELLOW + "   Creates an auction chest.",
                        ChatColor.GOLD + " ▪ /auction edit <name>",
                        ChatColor.YELLOW + "   Edits auction chest, needs to be closed first.",
                        ChatColor.GOLD + " ▪ /auction close <name>",
                        ChatColor.YELLOW + "   Closes auction, no more bidding.",
                        ChatColor.GOLD + " ▪ /auction open <name> [silent]",
                        ChatColor.YELLOW + "   Opens auction, start bidding. Will broadcast name.",
                        ChatColor.GOLD + " ▪ /auction give <name> <player>",
                        ChatColor.YELLOW + "   Give auction chest to player regardless of status."
                });

            }

            if (sender.hasPermission("uauction.op")) {

                sender.sendMessage(new String[]{
                        "------------------------------------------",
                        ChatColor.GOLD + " ▪ /auction maintenance <on|off>",
                        ChatColor.YELLOW + "   Places uAuction in maintenance mode.",
                        ChatColor.GOLD + " ▪ /auction delete",
                        ChatColor.YELLOW + "   Will delete a single auction regardless of status.",
                        ChatColor.GOLD + " ▪ /auction purge",
                        ChatColor.YELLOW + "   Will erase all auctions regardless of status."
                });

            }

        }

    }

}

