package com.ullarah.uchest.task;

import com.ullarah.uchest.function.Broadcast;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import static com.ullarah.uchest.ChestInit.*;
import static java.util.concurrent.TimeUnit.SECONDS;

public class ChestClean {

    private final long chestCountdownFinal = getPlugin().getConfig().getLong("dchest.clean");
    private final boolean chestRandomEnabled = getPlugin().getConfig().getBoolean("dchest.enabled");

    private final long chestCountdownWarning = getPlugin().getConfig().getLong("dchest.warning");
    private final long chestCountdownCritical = getPlugin().getConfig().getLong("dchest.critical");

    public void task() {

        if (chestRandomEnabled && chestCountdownFinal > 0)
            getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(), () -> {

                Broadcast broadcast = new Broadcast();

                boolean isSwapEnabled = getPlugin().getConfig().getBoolean("wchest.enabled");

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

                announce();

            }, 20, chestCountdownFinal * 20);

    }

    private void announce() {

        if (chestRandomEnabled && chestCountdownFinal > 0) {

            Broadcast broadcast = new Broadcast();

            long minuteWarning = SECONDS.toMinutes(chestCountdownWarning) - (SECONDS.toHours(chestCountdownWarning) * 60);
            long minuteCritical = SECONDS.toMinutes(chestCountdownCritical) - (SECONDS.toHours(chestCountdownCritical) * 60);

            getPlugin().getServer().getScheduler().runTaskLaterAsynchronously(getPlugin(),
                    () -> {
                        String s = (chestCountdownWarning) > 60 ? "s" : "";
                        if (displayClearMessage)
                            broadcast.sendMessage(getPlugin(), new String[]{ChatColor.AQUA
                                    + String.valueOf(minuteWarning) +
                                    " minute" + s + " left until the Donation Chest is emptied!"});
                    },
                    (chestCountdownFinal - chestCountdownWarning) * 20);

            getPlugin().getServer().getScheduler().runTaskLaterAsynchronously(getPlugin(),
                    () -> {
                        String s = (chestCountdownCritical) > 60 ? "s" : "";
                        if (displayClearMessage)
                            broadcast.sendMessage(getPlugin(), new String[]{ChatColor.RED
                                    + String.valueOf(minuteCritical) +
                                    " minute" + s + " left until the Donation Chest is emptied!"});
                    },
                    (chestCountdownFinal - chestCountdownCritical) * 20);

        }

    }

}
