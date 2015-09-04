package com.ullarah.upostal.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.upostal.PostalInit.*;

public class Maintenance {

    public static void toggle(CommandSender sender, String[] args) {

        int maintenanceState = 0;

        if (sender.hasPermission("postal.maintenance") || !(sender instanceof Player)) if (args.length == 2) {

            if (args[1].equals("on")) maintenanceState = 1;

            switch (maintenanceState) {

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

        } else sender.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "/postal maintenance <on|off>");
        else sender.sendMessage(getMsgPermDeny());

    }

}
