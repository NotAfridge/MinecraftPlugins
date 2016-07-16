package com.ullarah.utab;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import static com.ullarah.utab.TabInit.*;

class TabTask {

    void startTabTimer() {

        TabFunctions tabFunctions = new TabFunctions();

        BukkitTask tabTask = getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(),
                () -> getPlugin().getServer().getScheduler().runTask(getPlugin(), () -> {

                    for (Player player : getPlugin().getServer().getOnlinePlayers())
                        tabFunctions.sendHeaderFooter(player, tabFunctions.getCurrentHeader(), tabFunctions.getCurrentFooter());

                    if (headerMessageCurrent < headerMessageTotal) headerMessageCurrent++;
                    else headerMessageCurrent = 0;

                    if (footerMessageCurrent < footerMessageTotal) footerMessageCurrent++;
                    else footerMessageCurrent = 0;

                }), 0, getTabTimer());

        setTabTask(tabTask);

    }

}
