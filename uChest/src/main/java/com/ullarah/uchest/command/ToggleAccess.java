package com.ullarah.uchest.command;

import com.ullarah.uchest.ChestInit;
import com.ullarah.uchest.function.CommonString;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ToggleAccess {

    public void toggleChestAccess(CommandSender sender, String[] args) {

        CommonString commonString = new CommonString();

        if (!sender.hasPermission("chest.maintenance")) {
            commonString.messagePermDeny(ChestInit.getPlugin(), sender);
            return;
        }

        if (args.length == 2) {

            if (!args[1].toLowerCase().matches("[dehmrsvwx]chest")) {
                commonString.messageSend(ChestInit.getPlugin(), sender,
                        ChatColor.RED + "That type of chest does not exist!");
                return;
            }

            switch (ChestInit.chestTypeEnabled.get(args[1].toLowerCase()) ? 0 : 1) {

                case 0:
                    ChestInit.getPlugin().getConfig().set(args[1].toLowerCase() + ".enabled", false);
                    ChestInit.chestTypeEnabled.put(args[1].toLowerCase(), false);
                    commonString.messageSend(ChestInit.getPlugin(), sender,
                            ChatColor.YELLOW + args[1].toLowerCase() + ChatColor.RED + " is now disabled.");
                    break;

                case 1:
                    ChestInit.getPlugin().getConfig().set(args[1] + ".enabled", true);
                    ChestInit.chestTypeEnabled.put(args[1].toLowerCase(), true);
                    new CommonString().messageSend(ChestInit.getPlugin(), sender,
                            ChatColor.YELLOW + args[1].toLowerCase() + ChatColor.GREEN + " is now enabled.");
                    break;

            }

            ChestInit.getPlugin().saveConfig();

        } else
            commonString.messageSend(ChestInit.getPlugin(), sender,
                    ChatColor.YELLOW + "/chest toggle [dehmrsvwx]chest");

    }

}
