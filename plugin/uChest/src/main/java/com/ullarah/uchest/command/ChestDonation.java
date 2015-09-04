package com.ullarah.uchest.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.uchest.ChestFunctions.validCommands;
import static com.ullarah.uchest.ChestInit.*;

public class ChestDonation {

    public void runCommand(CommandSender sender, String[] args) {

        String consoleTools = getMsgPrefix() + ChatColor.WHITE + "random | reset";

        if (args.length == 0) if (!(sender instanceof Player))
            sender.sendMessage(getMsgNoConsole());
        else if (!getMaintenanceCheck()) if (chestTypeEnabled.get("dchest"))
            ((Player) sender).openInventory(getChestDonationHolder().getInventory());
        else sender.sendMessage(getMsgPrefix() + "Donation Chest is currently unavailable.");
        else
            sender.sendMessage(getMaintenanceMessage());

        else try {

            switch (validCommands.valueOf(args[0].toUpperCase())) {

                case RANDOM:
                    if (!getMaintenanceCheck())
                        DonationRandom.fillDonationChest(sender);
                    else
                        sender.sendMessage(getMaintenanceMessage());
                    break;

                case RESET:
                    if (!getMaintenanceCheck())
                        DonationReset.resetDonationChest(sender);
                    else
                        sender.sendMessage(getMaintenanceMessage());
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
