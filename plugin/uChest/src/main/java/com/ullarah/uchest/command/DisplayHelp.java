package com.ullarah.uchest.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class DisplayHelp {

    public static void runHelp(CommandSender sender) {

        if (sender instanceof Player) {

            sender.sendMessage(new String[]{
                    ChatColor.AQUA + "Mixed Chest Help",
                    "------------------------------------------",
                    ChatColor.GOLD + " ▪ /chest",
                    ChatColor.YELLOW + "   Opens the main chest menu.",
                    ChatColor.GOLD + " ▪ /dchest",
                    ChatColor.YELLOW + "   Opens the donation chest.",
                    ChatColor.GOLD + " ▪ /hchest",
                    ChatColor.YELLOW + "   Opens the hold chest.",
                    ChatColor.GOLD + " ▪ /mchest",
                    ChatColor.YELLOW + "   Opens the money chest.",
                    ChatColor.GOLD + " ▪ /pchest",
                    ChatColor.YELLOW + "   Opens the perk chest.",
                    ChatColor.GOLD + " ▪ /rchest",
                    ChatColor.YELLOW + "   Opens the random chest.",
                    ChatColor.GOLD + " ▪ /schest",
                    ChatColor.YELLOW + "   Opens the swapping chest.",
                    ChatColor.GOLD + " ▪ /vchest",
                    ChatColor.YELLOW + "   Opens the vault chest.",
                    ChatColor.GOLD + " ▪ /xchest",
                    ChatColor.YELLOW + "   Opens the experience chest."
            });

            if (sender.hasPermission("chest.staff")) {

                sender.sendMessage(new String[]{
                        "------------------------------------------",
                        ChatColor.GOLD + " ▪ /chest maintenance <on|off>",
                        ChatColor.YELLOW + "   Puts the chest system in maintenance mode.",
                        ChatColor.GOLD + " ▪ /chest toggle [dhmprsvx]chest",
                        ChatColor.YELLOW + "   Toggles the chest type access.",
                        ChatColor.GOLD + " ▪ /dchest random",
                        ChatColor.YELLOW + "   Will override items in chest with random items.",
                        ChatColor.GOLD + " ▪ /dchest reset",
                        ChatColor.YELLOW + "   Clears the entire donation chest.",
                        ChatColor.GOLD + " ▪ /hchest view <player>",
                        ChatColor.YELLOW + "   Views players hold chest.",
                        ChatColor.GOLD + " ▪ /vchest view <player>",
                        ChatColor.YELLOW + "   Views players vault chest."
                });

            }

        }

    }

}
