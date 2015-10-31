package com.ullarah.uchest.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.uchest.ChestInit.getPlugin;
import static com.ullarah.uchest.ChestInit.setMaintenanceCheck;
import static com.ullarah.ulib.function.CommonString.messagePermDeny;
import static com.ullarah.ulib.function.CommonString.messagePlayer;

public class Maintenance {

    public static void runMaintenance(CommandSender sender, String[] args) {

        if (sender.hasPermission("chest.maintenance")) if (args.length == 2) {

            switch (args[1].toLowerCase().equals("off") ? 0 : 1) {

                case 0:
                    getPlugin().getConfig().set("maintenance", false);
                    setMaintenanceCheck(false);
                    messagePlayer(getPlugin(), (Player) sender, new String[]{
                            ChatColor.GREEN + "Maintenance mode is now off."
                    });
                    break;

                case 1:
                    getPlugin().getConfig().set("maintenance", true);
                    setMaintenanceCheck(true);
                    messagePlayer(getPlugin(), (Player) sender, new String[]{
                            ChatColor.RED + "Maintenance mode is now on."
                    });
                    break;

            }

            getPlugin().saveConfig();

        } else messagePlayer(getPlugin(), (Player) sender, new String[]{
                ChatColor.YELLOW + "/chest maintenance <on|off>"});
        else messagePermDeny(getPlugin(), sender);

    }

}
