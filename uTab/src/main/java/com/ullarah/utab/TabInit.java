package com.ullarah.utab;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class TabInit extends JavaPlugin {

    private final TabFunctions tabFunctions = new TabFunctions(this);

    private TabFunctions getTabFunctions() {
        return tabFunctions;
    }

    public void onEnable() {

        PluginManager pluginManager = getServer().getPluginManager();

        getConfig().options().copyDefaults(true);
        saveConfig();

        pluginManager.registerEvents(new TabEvents(getTabFunctions()), this);

        getCommand("utab").setExecutor(new TabCommands(this, getTabFunctions()));

        if (getTabFunctions().reloadTabConfig()) new TabTask(this, getTabFunctions()).startTabTimer();

    }

    public void onDisable() {

        getTabFunctions().getTabTask().cancel();
        for (Player player : Bukkit.getOnlinePlayers()) getTabFunctions().sendHeaderFooter(player, "", "");

    }

}
