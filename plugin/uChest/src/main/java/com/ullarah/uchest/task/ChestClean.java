package com.ullarah.uchest.task;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import static com.ullarah.uchest.ChestInit.*;

public class ChestClean {

    private static final long chestCountdownFinal = getPlugin().getConfig().getLong("countdown") * 20;


    public static void task() {

        if (chestCountdownFinal > 0)
            Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(), () -> {

                chestSwapItemStack = getChestDonationInventory().getContents();
                getChestDonationInventory().clear();

                if (displayClearMessage) {
                    Bukkit.getLogger().info("[" + getPlugin().getName() + "] " + "Cleaning Donation Chest Items...");
                    Bukkit.broadcastMessage(getMsgPrefix() + ChatColor.YELLOW + "Donation Chest has been emptied!");
                    Bukkit.broadcastMessage(getMsgPrefix() + ChatColor.YELLOW + "Leftovers have gone to the Swap Chest!");
                }

            }, 0, chestCountdownFinal);

    }

}
