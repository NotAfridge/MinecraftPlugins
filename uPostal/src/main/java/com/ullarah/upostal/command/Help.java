package com.ullarah.upostal.command;

import com.ullarah.upostal.PostalInit;
import com.ullarah.upostal.function.CommonString;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Help {

    public void display(CommandSender sender) {

        CommonString commonString = new CommonString();

        if (sender instanceof Player) {

            Player player = (Player) sender;

            commonString.messageSend(player, new String[]{
                    ChatColor.AQUA + " uPostal Help",
                    " " + ChatColor.STRIKETHROUGH + "----------------------------------------------------",
                    ChatColor.GOLD + " /inbox" + ChatColor.YELLOW + " - Opens up your inbox.",
                    ChatColor.GOLD + " /inbox upgrade" + ChatColor.YELLOW + " - Purchase another inbox slot.",
                    ChatColor.GOLD + " /post <player>" + ChatColor.YELLOW + " - Opens up a players inbox for sending."
            });

            if (sender.hasPermission("postal.staff")) {

                commonString.messageSend(player, new String[]{
                        " " + ChatColor.STRIKETHROUGH + "----------------------------------------------------",
                        ChatColor.GOLD + " /postal blacklist <player>" + ChatColor.YELLOW + " - Blacklist the players inbox.",
                        ChatColor.GOLD + " /postal modify <player>" + ChatColor.YELLOW + " - Modify the players inbox.",
                        ChatColor.GOLD + " /postal clear <player>" + ChatColor.YELLOW + " - Clear the players inbox.",
                        ChatColor.GOLD + " /postal reset <player>" + ChatColor.YELLOW + " - Resets the players busy status."
                });

            }

        } else commonString.messageNoConsole(PostalInit.getPlugin(), sender);

    }

}
