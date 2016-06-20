package com.ullarah.uchest.task;

import com.ullarah.uchest.function.Broadcast;
import org.bukkit.ChatColor;

import static com.ullarah.uchest.ChestInit.displayClearMessage;
import static com.ullarah.uchest.ChestInit.getPlugin;
import static java.util.concurrent.TimeUnit.SECONDS;

public class ChestAnnounce {

    private final long chestCountdownFinal = getPlugin().getConfig().getLong("dchest.clean");
    private final boolean chestRandomEnabled = getPlugin().getConfig().getBoolean("dchest.enabled");

    private final long chestCountdownWarning = getPlugin().getConfig().getLong("dchest.warning");
    private final long chestCountdownCritical = getPlugin().getConfig().getLong("dchest.critical");

    public void task() {

        if (chestRandomEnabled && chestCountdownFinal > 0) {

            Broadcast broadcast = new Broadcast();

            long minuteWarning = SECONDS.toMinutes(chestCountdownWarning) - (SECONDS.toHours(chestCountdownWarning) * 60);
            long minuteCritical = SECONDS.toMinutes(chestCountdownCritical) - (SECONDS.toHours(chestCountdownCritical) * 60);

            getPlugin().getServer().getScheduler().runTaskLaterAsynchronously(getPlugin(),
                    () -> {
                        String s = (chestCountdownWarning) > 1 ? "s" : "";
                        if (displayClearMessage)
                            broadcast.sendMessage(getPlugin(), new String[]{ChatColor.AQUA
                                    + String.valueOf(minuteWarning) +
                                    " minute" + s + " left until the Donation Chest is emptied!"});
                    },
                    (chestCountdownFinal - chestCountdownWarning) * 20);

            getPlugin().getServer().getScheduler().runTaskLaterAsynchronously(getPlugin(),
                    () -> {
                        String s = (chestCountdownCritical) > 1 ? "s" : "";
                        if (displayClearMessage)
                            broadcast.sendMessage(getPlugin(), new String[]{ChatColor.RED
                                    + String.valueOf(minuteCritical) +
                                    " minute" + s + " left until the Donation Chest is emptied!"});
                    },
                    (chestCountdownFinal - chestCountdownCritical) * 20);

        }

    }

}
