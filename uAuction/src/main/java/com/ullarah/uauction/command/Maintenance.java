package com.ullarah.uauction.command;

import com.ullarah.uauction.Init;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Maintenance {

    public static void runMaintenance(CommandSender sender, String[] args) {

        int maintenanceState = 0;

        if (sender.hasPermission("uauction.maintenance") || !(sender instanceof Player)) if (args.length == 2) {

            if (args[1].equals("on")) maintenanceState = 1;

            switch (maintenanceState) {

                case 0:
                    Init.getPlugin().getConfig().set("maintenance", false);
                    Init.setMaintenanceCheck(false);
                    sender.sendMessage(Init.getMsgPrefix() + ChatColor.GREEN + "Maintenance mode is now off.");
                    break;

                case 1:
                    Init.getPlugin().getConfig().set("maintenance", true);
                    Init.setMaintenanceCheck(true);
                    sender.sendMessage(Init.getMsgPrefix() + ChatColor.RED + "Maintenance mode is now on.");
                    break;

            }

            Init.getPlugin().saveConfig();

        } else sender.sendMessage(Init.getMsgPrefix() + ChatColor.YELLOW + "/uauction maintenance <on|off>");
        else sender.sendMessage(Init.getMsgPermDeny());

    }

}

