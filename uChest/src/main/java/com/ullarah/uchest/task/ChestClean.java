package com.ullarah.uchest.task;

import com.ullarah.uchest.function.Broadcast;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import static com.ullarah.uchest.ChestInit.*;

public class ChestClean {

    private final long chestCountdownFinal = getPlugin().getConfig().getLong("countdown") * 20;

    public void task() {

        if (chestCountdownFinal > 0)
            getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(), () -> {

                Broadcast broadcast = new Broadcast();

                chestSwapItemStack = getChestDonationInventory().getContents();
                getChestDonationInventory().clear();

                if (displayClearMessage) {
                    Bukkit.getLogger().info("[" + getPlugin().getName() + "] " + "Cleaning Donation Chest Items...");
                    broadcast.sendMessage(getPlugin(), new String[]{
                            ChatColor.YELLOW + "Donation Chest has been emptied!",
                            ChatColor.YELLOW + "Leftovers have gone to the Swap Chest!"
                    });
                }

            }, 0, chestCountdownFinal);

    }

}
