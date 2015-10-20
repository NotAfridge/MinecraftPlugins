package com.ullarah.uchest.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.uchest.ChestInit.*;

public class ToggleAccess {

    public static void toggleChestAccess(CommandSender sender, String[] args) {

        if (sender.hasPermission("chest.maintenance") || !(sender instanceof Player))
            if (args.length == 2) if (args[1].toLowerCase().matches("[dhmrvx]chest")) {

                switch (chestTypeEnabled.get(args[1].toLowerCase()) ? 0 : 1) {

                    case 0:
                        getPlugin().getConfig().set(args[1].toLowerCase() + "enabled", false);
                        chestTypeEnabled.put(args[1].toLowerCase(), false);
                        sender.sendMessage(getMsgPrefix() + ChatColor.YELLOW + args[1].toLowerCase() +
                                ChatColor.RED + " is now disabled.");
                        break;

                    case 1:
                        getPlugin().getConfig().set(args[1] + "enabled", true);
                        chestTypeEnabled.put(args[1].toLowerCase(), true);
                        sender.sendMessage(getMsgPrefix() + ChatColor.YELLOW + args[1].toLowerCase() +
                                ChatColor.GREEN + " is now enabled.");
                        break;

                }

                getPlugin().saveConfig();

            } else sender.sendMessage(getMsgPrefix() + ChatColor.RED + "That type of chest doesn't exist.");
            else sender.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "/chest toggle <chest type> <on|off>");
        else sender.sendMessage(getMsgPermDeny());

    }

}
