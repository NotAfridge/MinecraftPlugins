package com.ullarah.upostal.command;

import com.ullarah.upostal.function.CommonString;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.upostal.PostalInit.getPlugin;
import static com.ullarah.upostal.PostalInit.setMaintenanceCheck;

public class Maintenance {

    public static void toggle(CommandSender sender, String[] args) {

        int maintenanceState = 0;

        if (sender.hasPermission("postal.maintenance") || !(sender instanceof Player)) if (args.length == 2) {

            if (args[1].equals("on")) maintenanceState = 1;

            switch (maintenanceState) {

                case 0:
                    getPlugin().getConfig().set("maintenance", false);
                    setMaintenanceCheck(false);
                    new CommonString().messageSend(getPlugin(), sender, true, new String[]{
                            ChatColor.GREEN + "Maintenance mode is now off."
                    });
                    break;

                case 1:
                    getPlugin().getConfig().set("maintenance", true);
                    setMaintenanceCheck(true);
                    new CommonString().messageSend(getPlugin(), sender, true, new String[]{
                            ChatColor.RED + "Maintenance mode is now on."
                    });
                    break;

            }

            getPlugin().saveConfig();

        } else
            new CommonString().messageSend(getPlugin(), sender, true, new String[]{ChatColor.YELLOW + "/postal maintenance <on|off>"});
        else new CommonString().messagePermDeny(getPlugin(), sender);

    }

}
