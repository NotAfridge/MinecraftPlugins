package com.ullarah.uchest.command;

import com.ullarah.uchest.function.CommonString;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import static com.ullarah.uchest.ChestInit.chestTypeEnabled;
import static com.ullarah.uchest.ChestInit.getPlugin;

public class ToggleAccess {

    public void toggleChestAccess(CommandSender sender, String[] args) {

        CommonString commonString = new CommonString();

        if (!sender.hasPermission("chest.maintenance")) {
            commonString.messagePermDeny(getPlugin(), sender);
            return;
        }

        if (args.length == 2) {

            if (!args[1].toLowerCase().matches("[dehmrsvx]chest")) {
                commonString.messageSend(getPlugin(), sender, ChatColor.RED + "That type of chest does not exist!");
                return;
            }

            switch (chestTypeEnabled.get(args[1].toLowerCase()) ? 0 : 1) {

                case 0:
                    getPlugin().getConfig().set(args[1].toLowerCase() + ".enabled", false);
                    chestTypeEnabled.put(args[1].toLowerCase(), false);
                    commonString.messageSend(getPlugin(), sender, ChatColor.YELLOW + args[1].toLowerCase() + ChatColor.RED + " is now disabled.");
                    break;

                case 1:
                    getPlugin().getConfig().set(args[1] + ".enabled", true);
                    chestTypeEnabled.put(args[1].toLowerCase(), true);
                    new CommonString().messageSend(getPlugin(), sender, ChatColor.YELLOW + args[1].toLowerCase() + ChatColor.GREEN + " is now enabled.");
                    break;

            }

            getPlugin().saveConfig();

        } else commonString.messageSend(getPlugin(), sender, ChatColor.YELLOW + "/chest toggle [dehmrsvx]chest");

    }

}
