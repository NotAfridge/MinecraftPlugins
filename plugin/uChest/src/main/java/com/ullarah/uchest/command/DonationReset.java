package com.ullarah.uchest.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.uchest.ChestInit.*;

class DonationReset {

    public static void resetDonationChest(CommandSender sender) {

        if (sender.hasPermission("chest.reset") || !(sender instanceof Player)) {

            getChestDonationInventory().clear();
            Bukkit.broadcastMessage(getMsgPrefix() + ChatColor.RED + "Donation Chest has been reset!");

        } else sender.sendMessage(getMsgPermDeny());

    }

}
