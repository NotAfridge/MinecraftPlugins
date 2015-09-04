package com.ullarah.uchest.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.uchest.ChestFunctions.openConvertChest;
import static com.ullarah.uchest.ChestInit.*;

public class ChestExperience {

    public void runCommand(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {

            sender.sendMessage(getMsgNoConsole());

        } else if (!getMaintenanceCheck()) {

            if (allowMoneyChest && chestTypeEnabled.get("xchest")) {

                openConvertChest(sender, "XP");

            } else {

                sender.sendMessage(getMsgPrefix() + "Experience Chest is currently unavailable.");

            }

        } else {

            sender.sendMessage(getMaintenanceMessage());

        }

    }

}
