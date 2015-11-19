package com.ullarah.uchest.command;

import com.ullarah.uchest.function.Broadcast;
import com.ullarah.uchest.function.CommonString;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import static com.ullarah.uchest.ChestInit.getChestDonationInventory;
import static com.ullarah.uchest.ChestInit.getPlugin;

public class DonationReset {

    public void resetDonationChest(CommandSender sender) {

        CommonString commonString = new CommonString();
        Broadcast broadcast = new Broadcast();

        if (!sender.hasPermission("chest.reset")) {
            commonString.messagePermDeny(getPlugin(), sender);
            return;
        }

        getChestDonationInventory().clear();
        broadcast.sendMessage(getPlugin(), new String[]{ChatColor.RED + "Donation Chest has been reset!"});

    }

}
