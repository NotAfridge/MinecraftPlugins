package com.ullarah.urocket.command;

import com.ullarah.urocket.function.CommonString;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.urocket.RocketInit.getPlugin;

public class DisplayHelp {

    public void display(CommandSender sender) {

        CommonString commonString = new CommonString();

        if (sender instanceof Player) {

            commonString.messageSend(getPlugin(), sender, true, new String[]{
                    ChatColor.YELLOW + "Please visit the below URL for more information:",
                    ChatColor.GREEN + "   https://goo.gl/uiD5k3"
            });

            if (sender.hasPermission("rocket.staff")) {

                commonString.messageSend(getPlugin(), sender, true, new String[]{
                        ChatColor.RED + "----- Staff Commands -----",
                        ChatColor.GOLD + " ▪ /rocket chest",
                        ChatColor.YELLOW + "   Displays all Rocket Components."
                });

            }

        }

    }

}
