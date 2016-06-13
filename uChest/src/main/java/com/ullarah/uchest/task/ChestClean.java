package com.ullarah.uchest.task;

import com.ullarah.uchest.function.Broadcast;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import static com.ullarah.uchest.ChestInit.*;

public class ChestClean {

    private final long chestCountdownFinal = getPlugin().getConfig().getLong("dchest.clean") * 20;
    private final boolean chestRandomEnabled = getPlugin().getConfig().getBoolean("dchest.enabled");

    public void task() {

        if (chestRandomEnabled && chestCountdownFinal > 0)
            getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(), () -> {

                Broadcast broadcast = new Broadcast();

                boolean isSwapEnabled = getPlugin().getConfig().getBoolean("schest.enabled");

                if (isSwapEnabled) chestSwapItemStack = getChestDonationInventory().getContents();
                getChestDonationInventory().clear();

                if (displayClearMessage) {
                    Bukkit.getLogger().info("[" + getPlugin().getName() + "] " + "Cleaning Donation Chest Items...");
                    broadcast.sendMessage(getPlugin(), new String[]{
                            ChatColor.YELLOW + "Donation Chest has been emptied!",
                    });
                    if (isSwapEnabled) broadcast.sendMessage(getPlugin(), new String[]{
                            ChatColor.YELLOW + "Leftovers have gone to the Swap Chest!"
                    });
                }

            }, 0, chestCountdownFinal);

    }

}
