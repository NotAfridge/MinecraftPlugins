package com.ullarah.utab;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class TabInit extends JavaPlugin {

    public static int headerMessageTotal = 0;
    public static int headerMessageCurrent = 0;
    public static int footerMessageTotal = 0;
    public static int footerMessageCurrent = 0;

    private static Plugin plugin;
    private static BukkitTask tabTask;
    private static List headerMessages;
    private static List footerMessages;
    private static int tabTimer;
    private final TabFunctions tabFunctions = new TabFunctions();

    public static Plugin getPlugin() {
        return plugin;
    }

    private static void setPlugin(Plugin plugin) {
        TabInit.plugin = plugin;
    }

    public static BukkitTask getTabTask() {
        return tabTask;
    }

    public static void setTabTask(BukkitTask task) {
        TabInit.tabTask = task;
    }

    public static List getHeaderMessages() {
        return headerMessages;
    }

    public static void setHeaderMessages(List messages) {
        TabInit.headerMessages = messages;
    }

    public static List getFooterMessages() {
        return footerMessages;
    }

    public static void setFooterMessages(List messages) {
        TabInit.footerMessages = messages;
    }

    public static int getTabTimer() {
        return tabTimer;
    }

    public static void setTabTimer(int time) {
        TabInit.tabTimer = time * 20;
    }

    public void onEnable() {

        setPlugin(this);

        PluginManager pluginManager = getServer().getPluginManager();

        getConfig().options().copyDefaults(true);
        saveConfig();

        pluginManager.registerEvents(new TabEvents(), getPlugin());

        getCommand("utab").setExecutor(new TabCommands());
        if (tabFunctions.reloadTabConfig()) new TabTask().startTabTimer();

    }

    public void onDisable() {

        getTabTask().cancel();
        for (Player player : Bukkit.getOnlinePlayers()) tabFunctions.sendHeaderFooter(player, "", "");

    }

}
