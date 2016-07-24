package com.ullarah.uchest.command;

import com.ullarah.uchest.ChestInit;
import com.ullarah.uchest.function.CommonString;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DisplayHelp {

    public void runHelp(CommandSender sender) {

        if (sender instanceof Player) {

            CommonString commonString = new CommonString();

            Player player = (Player) sender;

            commonString.messageSend(ChestInit.getPlugin(), player, false, new String[]{
                    ChatColor.AQUA + " uChest Help" + ChatColor.WHITE + " - Use /chest to read descriptions!",
                    " " + ChatColor.STRIKETHROUGH + "----------------------------------------------------",
                    ChatColor.GOLD + " /chest  - " + ChatColor.YELLOW + "Mixed Chest Main Menu",
                    ChatColor.GOLD + " /dchest - " + ChatColor.YELLOW + "Donation Chest",
                    ChatColor.GOLD + " /echest - " + ChatColor.YELLOW + "Enchantment Chest",
                    ChatColor.GOLD + " /hchest - " + ChatColor.YELLOW + "Hold Chest",
                    ChatColor.GOLD + " /mchest - " + ChatColor.YELLOW + "Money Chest",
                    ChatColor.GOLD + " /pchest - " + ChatColor.YELLOW + "Pickup Chest",
                    ChatColor.GOLD + " /rchest - " + ChatColor.YELLOW + "Random Chest",
                    ChatColor.GOLD + " /schest - " + ChatColor.YELLOW + "Shuffle Chest",
                    ChatColor.GOLD + " /vchest - " + ChatColor.YELLOW + "Vault Chest",
                    ChatColor.GOLD + " /wchest - " + ChatColor.YELLOW + "Swap Chest",
                    ChatColor.GOLD + " /xchest - " + ChatColor.YELLOW + "Experience Chest"
            });

            if (sender.hasPermission("chest.staff")) {

                commonString.messageSend(ChestInit.getPlugin(), player, false, new String[]{
                        " " + ChatColor.STRIKETHROUGH + "-------------------" + ChatColor.RED + " Staff Commands "
                                + ChatColor.WHITE + ChatColor.STRIKETHROUGH + "-------------------",
                        ChatColor.GOLD + " /chest toggle [dehmprsvwx]chest",
                        ChatColor.YELLOW + "   Toggles the chest type access",
                        ChatColor.GOLD + " /dchest random",
                        ChatColor.YELLOW + "   Will override items in chest with random items",
                        ChatColor.GOLD + " /dchest reset",
                        ChatColor.YELLOW + "   Clears the entire donation chest",
                        ChatColor.GOLD + " /hchest <view|reset> <player>",
                        ChatColor.YELLOW + "   Views/Resets players hold chest",
                        ChatColor.GOLD + " /vchest <view|reset> <player>",
                        ChatColor.YELLOW + "   Views/Resets players vault chest"
                });

            }

        }

    }

}
