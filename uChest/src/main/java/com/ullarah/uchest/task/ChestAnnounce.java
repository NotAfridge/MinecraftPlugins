package com.ullarah.uchest.task;

import com.ullarah.uchest.function.Broadcast;
import org.bukkit.ChatColor;

import static com.ullarah.uchest.ChestInit.displayClearMessage;
import static com.ullarah.uchest.ChestInit.getPlugin;

public class ChestAnnounce {

    private final long chestCountdownFinal = getPlugin().getConfig().getLong("dchest.clean") * 20;
    private final boolean chestRandomEnabled = getPlugin().getConfig().getBoolean("dchest.enabled");

    private final long chestCountdownWarning = getPlugin().getConfig().getLong("dchest.warning") * 20;
    private final long chestCountdownCritical = getPlugin().getConfig().getLong("dchest.critical") * 20;

    public void task() {

        if (chestRandomEnabled && chestCountdownFinal > 0) {

            Broadcast broadcast = new Broadcast();

            getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(),
                    () -> {
                        String s = (chestCountdownWarning * 20) > 1 ? "s" : "";
                        if (displayClearMessage)
                            broadcast.sendMessage(getPlugin(), new String[]{ChatColor.AQUA
                                    + String.valueOf(chestCountdownWarning * 20) +
                                    " minute" + s + " left until the Donation Chest is emptied!"});
                    },
                    0, (chestCountdownFinal - 600) * 20);

            getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(),
                    () -> {
                        String s = (chestCountdownCritical * 20) > 1 ? "s" : "";
                        if (displayClearMessage)
                            broadcast.sendMessage(getPlugin(), new String[]{ChatColor.RED
                                    + String.valueOf(chestCountdownCritical * 20) +
                                    " minute" + s + " left until the Donation Chest is emptied!"});
                    },
                    0, (chestCountdownFinal - 60) * 20);

        }

    }

}
