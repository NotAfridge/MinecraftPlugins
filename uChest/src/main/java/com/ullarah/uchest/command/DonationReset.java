package com.ullarah.uchest.command;

import com.ullarah.uchest.ChestInit;
import com.ullarah.uchest.function.Broadcast;
import com.ullarah.uchest.function.CommonString;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class DonationReset {

    public void resetDonationChest(CommandSender sender) {

        CommonString commonString = new CommonString();
        Broadcast broadcast = new Broadcast();

        if (!sender.hasPermission("chest.reset")) {
            commonString.messagePermDeny(ChestInit.getPlugin(), sender);
            return;
        }

        ChestInit.getChestDonationInventory().clear();
        broadcast.sendMessage(ChestInit.getPlugin(), new String[]{ChatColor.RED + "Donation Chest has been reset!"});

    }

}
