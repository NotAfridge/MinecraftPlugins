package com.ullarah.utab;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import static com.ullarah.utab.TabFunctions.*;
import static com.ullarah.utab.TabInit.*;

class TabTask {

    public static void startTabTimer() {

        BukkitTask tabTask = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(),
                () -> Bukkit.getScheduler().runTask(getPlugin(), () -> {

                    for (Player player : Bukkit.getOnlinePlayers())
                        sendHeaderFooter(player, getCurrentHeader(), getCurrentFooter());

                    if (headerMessageCurrent < headerMessageTotal) headerMessageCurrent++;
                    else headerMessageCurrent = 0;

                    if (footerMessageCurrent < footerMessageTotal) footerMessageCurrent++;
                    else footerMessageCurrent = 0;

                }), 0, getTabTimer());

        setTabTask(tabTask);

    }

}
