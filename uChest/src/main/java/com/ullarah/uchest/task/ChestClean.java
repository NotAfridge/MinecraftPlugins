package com.ullarah.uchest.task;

import com.ullarah.uchest.ChestInit;
import com.ullarah.uchest.function.Broadcast;
import org.bukkit.ChatColor;

import java.util.concurrent.TimeUnit;

public class ChestClean {

    private final long chestCountdownFinal = ChestInit.getPlugin().getConfig().getLong("dchest.clean");
    private final boolean chestRandomEnabled = ChestInit.getPlugin().getConfig().getBoolean("dchest.enabled");

    private final long chestCountdownWarning = ChestInit.getPlugin().getConfig().getLong("dchest.warning");
    private final long chestCountdownCritical = ChestInit.getPlugin().getConfig().getLong("dchest.critical");

    public void task() {

        if (chestRandomEnabled && chestCountdownFinal > 0)
            ChestInit.getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(ChestInit.getPlugin(), () -> {

                Broadcast broadcast = new Broadcast();

                boolean isSwapEnabled = ChestInit.getPlugin().getConfig().getBoolean("wchest.enabled");

                if (isSwapEnabled) ChestInit.chestSwapItemStack = ChestInit.getChestDonationInventory().getContents();
                ChestInit.getChestDonationInventory().clear();

                if (ChestInit.displayClearMessage) {
                    ChestInit.getPlugin().getLogger().info("[" + ChestInit.getPlugin().getName() + "] "
                            + "Cleaning Donation Chest Items...");
                    broadcast.sendMessage(ChestInit.getPlugin(), new String[]{
                            ChatColor.YELLOW + "Donation Chest has been emptied!",
                    });
                    if (isSwapEnabled) broadcast.sendMessage(ChestInit.getPlugin(), new String[]{
                            ChatColor.YELLOW + "Leftovers have gone to the Swap Chest!"
                    });
                }

                announce();

            }, 20, chestCountdownFinal * 20);

    }

    private void announce() {

        if (chestRandomEnabled && chestCountdownFinal > 0) {

            Broadcast broadcast = new Broadcast();

            long minuteWarning = TimeUnit.SECONDS.toMinutes(chestCountdownWarning) - (TimeUnit.SECONDS.toHours(chestCountdownWarning) * 60);
            long minuteCritical = TimeUnit.SECONDS.toMinutes(chestCountdownCritical) - (TimeUnit.SECONDS.toHours(chestCountdownCritical) * 60);

            ChestInit.getPlugin().getServer().getScheduler().runTaskLaterAsynchronously(ChestInit.getPlugin(),
                    () -> {
                        String s = (minuteWarning) > 1 ? "s" : "";
                        if (ChestInit.displayClearMessage)
                            broadcast.sendMessage(ChestInit.getPlugin(), new String[]{ChatColor.AQUA
                                    + String.valueOf(minuteWarning) +
                                    " minute" + s + " left until the Donation Chest is emptied!"});
                    },
                    (chestCountdownFinal - chestCountdownWarning) * 20);

            ChestInit.getPlugin().getServer().getScheduler().runTaskLaterAsynchronously(ChestInit.getPlugin(),
                    () -> {
                        String s = (minuteCritical) > 1 ? "s" : "";
                        if (ChestInit.displayClearMessage)
                            broadcast.sendMessage(ChestInit.getPlugin(), new String[]{ChatColor.RED
                                    + String.valueOf(minuteCritical) +
                                    " minute" + s + " left until the Donation Chest is emptied!"});
                    },
                    (chestCountdownFinal - chestCountdownCritical) * 20);

        }

    }

}
