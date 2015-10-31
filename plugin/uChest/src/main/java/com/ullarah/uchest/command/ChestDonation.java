package com.ullarah.uchest.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.uchest.ChestFunctions.validCommands;
import static com.ullarah.uchest.ChestInit.*;
import static com.ullarah.ulib.function.CommonString.*;

public class ChestDonation {

    public void runCommand(CommandSender sender, String[] args) {

        String consoleTools = pluginPrefix(getPlugin()) + ChatColor.WHITE + "random | reset";

        if (args.length == 0) {

            if (!(sender instanceof Player)) messageNoConsole(getPlugin(), sender);
            else if (!getMaintenanceCheck()) {

                if (chestTypeEnabled.get("dchest"))
                    ((Player) sender).openInventory(getChestDonationHolder().getInventory());
                else
                    messagePlayer(getPlugin(), (Player) sender, new String[]{"Donation Chest is currently unavailable."});

            } else messageMaintenance(getPlugin(), sender);

        } else try {

            switch (validCommands.valueOf(args[0].toUpperCase())) {

                case RANDOM:
                    if (!getMaintenanceCheck())
                        DonationRandom.fillDonationChest(sender);
                    else
                        messageMaintenance(getPlugin(), sender);
                    break;

                case RESET:
                    if (!getMaintenanceCheck())
                        DonationReset.resetDonationChest(sender);
                    else
                        messageMaintenance(getPlugin(), sender);
                    break;

                default:
                    if (!(sender instanceof Player))
                        sender.sendMessage(consoleTools);
                    else
                        ((Player) sender).openInventory(getChestDonationHolder().getInventory());

            }

        } catch (IllegalArgumentException e) {

            if (!(sender instanceof Player))
                sender.sendMessage(consoleTools);
            else {
                DisplayHelp.runHelp(sender);
            }

        }

    }

}
