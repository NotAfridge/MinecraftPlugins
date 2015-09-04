package com.ullarah.uchest.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.uchest.ChestFunctions.openPerkChest;
import static com.ullarah.uchest.ChestInit.*;

public class ChestPerk {

    public void runCommand(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {

            sender.sendMessage(getMsgNoConsole());

        } else if (!getMaintenanceCheck()) {

            if (allowMoneyChest && chestTypeEnabled.get("pchest")) {

                openPerkChest(sender);

            } else {

                sender.sendMessage(getMsgPrefix() + "Perk Chest is currently unavailable.");

            }

        } else {

            sender.sendMessage(getMaintenanceMessage());

        }

    }

}
