package com.ullarah.uchest.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.uchest.ChestInit.*;

public class ChestSwap {

    public void runCommand(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {

            sender.sendMessage(getMsgNoConsole());

        } else if (!getMaintenanceCheck()) {

            if (chestTypeEnabled.get("schest")) {

                if (chestSwapBusy) {

                    sender.sendMessage(getMsgPrefix() + "The swap chest is busy. Try again later!");

                } else {

                    ((Player) sender).openInventory(getChestSwapHolder().getInventory());

                }

            } else {

                sender.sendMessage(getMsgPrefix() + "Swapping Chest is currently unavailable.");

            }

        } else {

            sender.sendMessage(getMaintenanceMessage());

        }

    }

}
