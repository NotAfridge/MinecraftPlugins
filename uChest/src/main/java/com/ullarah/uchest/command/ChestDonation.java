package com.ullarah.uchest.command;

import com.ullarah.uchest.function.CommonString;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.uchest.ChestFunctions.validCommands;
import static com.ullarah.uchest.ChestInit.*;

public class ChestDonation {

    public void runCommand(CommandSender sender, String[] args) {

        String consoleTools = new CommonString().pluginPrefix(getPlugin()) + ChatColor.WHITE + "random | reset";

        if (args.length == 0) {

            if (!(sender instanceof Player)) new CommonString().messageNoConsole(getPlugin(), sender);
            else if (!getMaintenanceCheck()) {

                if (chestTypeEnabled.get("dchest")) ((Player) sender).openInventory(getChestDonationHolder().getInventory());
                else
                    new CommonString().messageSend(getPlugin(), sender, true, new String[]{"Donation Chest is currently unavailable."});

            } else new CommonString().messageMaintenance(getPlugin(), sender);

        } else try {

            switch (validCommands.valueOf(args[0].toUpperCase())) {

                case RANDOM:
                    if (!getMaintenanceCheck())
                        DonationRandom.fillDonationChest(sender);
                    else
                        new CommonString().messageMaintenance(getPlugin(), sender);
                    break;

                case RESET:
                    if (!getMaintenanceCheck())
                        DonationReset.resetDonationChest(sender);
                    else
                        new CommonString().messageMaintenance(getPlugin(), sender);
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
