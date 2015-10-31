package com.ullarah.uchest.task;

import com.ullarah.ulib.function.Broadcast;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import static com.ullarah.uchest.ChestInit.displayClearMessage;
import static com.ullarah.uchest.ChestInit.getPlugin;

public class ChestAnnounce {

    private static final long chestCountdownFinal = getPlugin().getConfig().getLong("countdown") * 20;
    private static final long chestCountdownWarning = (chestCountdownFinal - 600) * 20;
    private static final long chestCountdownCritical = (chestCountdownFinal - 60) * 20;

    public static void task() {

        if (chestCountdownFinal > 0) {

            Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(),
                    () -> {
                        if (displayClearMessage) Broadcast.sendMessage(getPlugin(), false, new String[]{ChatColor.AQUA
                                + "10 minutes left until the Donation Chest is emptied!"});
                    },
                    0, chestCountdownWarning);

            Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(),
                    () -> {
                        if (displayClearMessage) Broadcast.sendMessage(getPlugin(), false, new String[]{ChatColor.RED
                                + "60 seconds left until the Donation Chest is emptied!"});
                    },
                    0, chestCountdownCritical);

        }

    }

}
