package com.ullarah.uchest.task;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import static com.ullarah.uchest.ChestInit.getMsgPrefix;
import static com.ullarah.uchest.ChestInit.getPlugin;

public class ChestAnnounce {

    private static final long chestCountdownFinal = getPlugin().getConfig().getLong("countdown") * 20;
    private static final long chestCountdownWarning = (getPlugin().getConfig().getLong("countdown") - 600) * 20;
    private static final long chestCountdownCritical = (getPlugin().getConfig().getLong("countdown") - 60) * 20;

    public static void task() {

        if (chestCountdownFinal > 0) {

            Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(),
                    () -> Bukkit.broadcastMessage(getMsgPrefix() + ChatColor.AQUA
                            + "10 minutes left until the Donation Chest is emptied!"),
                    chestCountdownWarning, chestCountdownWarning);

            Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(),
                    () -> Bukkit.broadcastMessage(getMsgPrefix() + ChatColor.RED
                            + "60 seconds left until the Donation Chest is emptied!"),
                    chestCountdownCritical, chestCountdownCritical);

        }

    }

}
