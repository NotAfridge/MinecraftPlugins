package com.ullarah.urocket.command;

import com.ullarah.ulib.function.CommonString;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.urocket.RocketInit.getPlugin;

public class DisplayHelp {

    public static void runHelp(CommandSender sender) {

        if (sender instanceof Player) {

            new CommonString().messageSend(getPlugin(), sender, true, new String[]{
                    ChatColor.YELLOW + "Please visit the below URL for more information:",
                    ChatColor.GREEN + "   https://goo.gl/uiD5k3"
            });

            if (sender.hasPermission("rocket.staff")) {

                new CommonString().messageSend(getPlugin(), sender, true, new String[]{
                        ChatColor.RED + "----- Staff Commands -----",
                        ChatColor.GOLD + " ▪ /rocket chest",
                        ChatColor.YELLOW + "   Displays all Rocket Components."
                });

            }

        }

    }

}
