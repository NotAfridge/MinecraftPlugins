package com.ullarah.uchest.command;

import com.ullarah.uchest.function.Broadcast;
import com.ullarah.uchest.function.CommonString;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.uchest.ChestInit.getChestDonationInventory;
import static com.ullarah.uchest.ChestInit.getPlugin;

public class DonationReset {

    public static void resetDonationChest(CommandSender sender) {

        if (sender.hasPermission("chest.reset") || !(sender instanceof Player)) {

            getChestDonationInventory().clear();
            new Broadcast().sendMessage(getPlugin(), false, new String[]{ChatColor.RED + "Donation Chest has been reset!"});

        } else new CommonString().messagePermDeny(getPlugin(), sender);

    }

}
