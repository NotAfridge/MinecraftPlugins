package com.ullarah.utab;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

class TabTask {

    private final Plugin plugin;
    private final TabFunctions tabFunctions;

    TabTask(Plugin instance, TabFunctions functions) {
        plugin = instance;
        tabFunctions = functions;
    }

    private Plugin getPlugin() {
        return plugin;
    }

    private TabFunctions getTabFunctions() {
        return tabFunctions;
    }

    void startTabTimer() {

        BukkitTask tabTask = getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(),
                () -> getPlugin().getServer().getScheduler().runTask(getPlugin(), () -> {

                    for (Player player : getPlugin().getServer().getOnlinePlayers())
                        getTabFunctions().sendHeaderFooter(
                                player, getTabFunctions().getCurrentHeader(), getTabFunctions().getCurrentFooter());

                    if (getTabFunctions().headerMessageCurrent < getTabFunctions().headerMessageTotal)
                        getTabFunctions().headerMessageCurrent++;
                    else getTabFunctions().headerMessageCurrent = 0;

                    if (getTabFunctions().footerMessageCurrent < getTabFunctions().footerMessageTotal)
                        getTabFunctions().footerMessageCurrent++;
                    else getTabFunctions().footerMessageCurrent = 0;

                }), 0, getTabFunctions().getTabTimer());

        getTabFunctions().setTabTask(tabTask);

    }

}
