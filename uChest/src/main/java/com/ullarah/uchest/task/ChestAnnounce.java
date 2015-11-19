package com.ullarah.uchest.task;

import com.ullarah.uchest.function.Broadcast;
import org.bukkit.ChatColor;

import static com.ullarah.uchest.ChestInit.displayClearMessage;
import static com.ullarah.uchest.ChestInit.getPlugin;

public class ChestAnnounce {

    private final long chestCountdownFinal = getPlugin().getConfig().getLong("countdown") * 20;

    public void task() {

        if (chestCountdownFinal > 0) {

            Broadcast broadcast = new Broadcast();

            getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(),
                    () -> {
                        if (displayClearMessage)
                            broadcast.sendMessage(getPlugin(), new String[]{ChatColor.AQUA
                                + "10 minutes left until the Donation Chest is emptied!"});
                    },
                    0, (chestCountdownFinal - 600) * 20);

            getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(),
                    () -> {
                        if (displayClearMessage)
                            broadcast.sendMessage(getPlugin(), new String[]{ChatColor.RED
                                + "60 seconds left until the Donation Chest is emptied!"});
                    },
                    0, (chestCountdownFinal - 60) * 20);

        }

    }

}
