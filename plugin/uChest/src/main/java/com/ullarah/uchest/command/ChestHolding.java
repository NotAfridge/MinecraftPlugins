package com.ullarah.uchest.command;

import com.ullarah.ulib.function.ProfileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.uchest.ChestFunctions.validCommands;
import static com.ullarah.uchest.ChestFunctions.validStorage.HOLD;
import static com.ullarah.uchest.ChestInit.*;

public class ChestHolding {

    public void runCommand(CommandSender sender, String[] args) {

        if (args.length == 0) if (!(sender instanceof Player))
            sender.sendMessage(getMsgNoConsole());
        else if (!getMaintenanceCheck()) if (chestTypeEnabled.get("hchest")) ChestCreation.create(sender, HOLD, true);
        else sender.sendMessage(getMsgPrefix() + "Hold Chest is currently unavailable.");
        else
            sender.sendMessage(getMaintenanceMessage());

        else try {

            switch (validCommands.valueOf(args[0].toUpperCase())) {

                case VIEW:
                    if (!(sender instanceof Player))
                        sender.sendMessage(getMsgNoConsole());
                    else if (sender.hasPermission("chest.view")) {
                        if (args.length == 2) {
                            try {
                                ChestPrepare.prepare(Bukkit.getPlayer(ProfileUtils.lookup(args[1]).getId()),
                                        (Player) sender, HOLD);
                            } catch (Exception e) {
                                sender.sendMessage(getMsgPrefix() + ChatColor.RED +
                                        "Cannot view hold chest at this time. Try again later!");
                            }
                        } else sender.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "/hchest view <player>");
                    } else sender.sendMessage(getMsgPrefix() + getMsgPermDeny());
                    break;

                default:
                    if (!(sender instanceof Player))
                        sender.sendMessage(getMsgNoConsole());
                    else
                        ChestCreation.create(sender, HOLD, true);

            }

        } catch (IllegalArgumentException e) {

            if (!(sender instanceof Player))
                sender.sendMessage(getMsgNoConsole());
            else if (!args[0].equalsIgnoreCase("view")) DisplayHelp.runHelp(sender);

        }

    }

}
