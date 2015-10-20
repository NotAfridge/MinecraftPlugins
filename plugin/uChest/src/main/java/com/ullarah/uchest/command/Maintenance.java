package com.ullarah.uchest.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.uchest.ChestInit.*;

public class Maintenance {

    public static void runMaintenance(CommandSender sender, String[] args) {

        if (sender.hasPermission("chest.maintenance") || !(sender instanceof Player)) if (args.length == 2) {

            switch (args[1].toLowerCase().equals("off") ? 0 : 1) {

                case 0:
                    getPlugin().getConfig().set("maintenance", false);
                    setMaintenanceCheck(false);
                    sender.sendMessage(getMsgPrefix() + ChatColor.GREEN + "Maintenance mode is now off.");
                    break;

                case 1:
                    getPlugin().getConfig().set("maintenance", true);
                    setMaintenanceCheck(true);
                    sender.sendMessage(getMsgPrefix() + ChatColor.RED + "Maintenance mode is now on.");
                    break;

            }

            getPlugin().saveConfig();

        } else sender.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "/chest maintenance <on|off>");
        else sender.sendMessage(getMsgPermDeny());

    }

}
