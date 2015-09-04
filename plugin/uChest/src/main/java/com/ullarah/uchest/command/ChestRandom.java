package com.ullarah.uchest.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.uchest.ChestFunctions.openRandomChest;
import static com.ullarah.uchest.ChestInit.*;

public class ChestRandom {

    public void runCommand(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {

            sender.sendMessage(getMsgNoConsole());

        } else if (!getMaintenanceCheck()) {

            if (chestTypeEnabled.get("rchest")) {

                openRandomChest(sender);

            } else {

                sender.sendMessage(getMsgPrefix() + "Random Chest is currently unavailable.");

            }

        } else {

            sender.sendMessage(getMaintenanceMessage());

        }

    }

}
