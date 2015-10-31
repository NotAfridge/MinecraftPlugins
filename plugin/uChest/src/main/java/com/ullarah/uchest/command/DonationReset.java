package com.ullarah.uchest.command;

import com.ullarah.ulib.function.Broadcast;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.uchest.ChestInit.getChestDonationInventory;
import static com.ullarah.uchest.ChestInit.getPlugin;
import static com.ullarah.ulib.function.CommonString.messagePermDeny;

public class DonationReset {

    public static void resetDonationChest(CommandSender sender) {

        if (sender.hasPermission("chest.reset") || !(sender instanceof Player)) {

            getChestDonationInventory().clear();
            Broadcast.sendMessage(getPlugin(), false, new String[]{ChatColor.RED + "Donation Chest has been reset!"});

        } else messagePermDeny(getPlugin(), sender);

    }

}
