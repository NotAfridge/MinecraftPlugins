package com.ullarah.uchest.command;

import com.ullarah.uchest.function.CommonString;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.uchest.ChestFunctions.validCommands;
import static com.ullarah.uchest.ChestInit.*;

public class ChestDonation {

    public void runCommand(CommandSender sender, String[] args) {

        CommonString commonString = new CommonString();
        String consoleTools = new CommonString().pluginPrefix(getPlugin()) + ChatColor.WHITE + "random | reset";

        if (args.length == 0) {

            if (!(sender instanceof Player)) {
                new CommonString().messageNoConsole(getPlugin(), sender);
                return;
            }

            if (chestTypeEnabled.get("dchest"))
                ((Player) sender).openInventory(getChestDonationHolder().getInventory());
            else commonString.messageSend(getPlugin(), sender, "Donation Chest is currently unavailable.");

        } else try {

            switch (validCommands.valueOf(args[0].toUpperCase())) {

                case RANDOM:
                    new DonationRandom().fillDonationChest(sender);
                    break;

                case RESET:
                    new DonationReset().resetDonationChest(sender);
                    break;

                default:
                    if (!(sender instanceof Player)) sender.sendMessage(consoleTools);
                    else ((Player) sender).openInventory(getChestDonationHolder().getInventory());

            }

        } catch (IllegalArgumentException e) {

            if (!(sender instanceof Player)) sender.sendMessage(consoleTools);
            else new DisplayHelp().runHelp(sender);

        }

    }

}
