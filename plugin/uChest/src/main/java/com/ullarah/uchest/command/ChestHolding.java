package com.ullarah.uchest.command;

import com.ullarah.ulib.function.PlayerProfile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.uchest.ChestFunctions.validCommands;
import static com.ullarah.uchest.ChestFunctions.validStorage.HOLD;
import static com.ullarah.uchest.ChestInit.*;
import static com.ullarah.ulib.function.CommonString.*;

public class ChestHolding {

    public void runCommand(CommandSender sender, String[] args) {

        if (args.length == 0) if (!(sender instanceof Player)) messageNoConsole(getPlugin(), sender);
        else if (!getMaintenanceCheck()) {

            if (chestTypeEnabled.get("hchest")) ChestCreation.create(sender, HOLD, true);
            else messageSend(getPlugin(), sender, true, new String[]{"Hold Chest is currently unavailable."});

        } else messageMaintenance(getPlugin(), sender);

        else try {

            switch (validCommands.valueOf(args[0].toUpperCase())) {

                case VIEW:
                    if (!(sender instanceof Player)) messageNoConsole(getPlugin(), sender);
                    else if (sender.hasPermission("chest.view")) {
                        if (args.length == 2) {
                            try {
                                ChestPrepare.prepare(Bukkit.getPlayer(PlayerProfile.lookup(args[1]).getId()),
                                        (Player) sender, HOLD);
                            } catch (Exception e) {
                                messageSend(getPlugin(), sender, true, new String[]{
                                        ChatColor.RED + "Cannot view hold chest at this time. Try again later!"
                                });
                            }
                        } else
                            messageSend(getPlugin(), sender, true, new String[]{ChatColor.YELLOW + "/hchest view <player>"});
                    } else messagePermDeny(getPlugin(), sender);
                    break;

                default:
                    if (!(sender instanceof Player)) messageNoConsole(getPlugin(), sender);
                    else ChestCreation.create(sender, HOLD, true);

            }

        } catch (IllegalArgumentException e) {

            if (!(sender instanceof Player)) messageNoConsole(getPlugin(), sender);
            else if (!args[0].equalsIgnoreCase("view")) DisplayHelp.runHelp(sender);

        }

    }

}
