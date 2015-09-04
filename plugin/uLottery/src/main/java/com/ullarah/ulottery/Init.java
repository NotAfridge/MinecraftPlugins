package com.ullarah.ulottery;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.ullarah.ulottery.Task.deathLotteryStart;

public class Init extends JavaPlugin {

    public static final Integer totalPlayerPause = 6;
    public static Map<UUID, Integer> playerDeathSuspension = new ConcurrentHashMap<>();
    public static Map<UUID, Integer> playerDeathPrevious = new ConcurrentHashMap<>();
    public static Economy economy = null;
    public static Integer deathDuration = 0;
    public static Integer deathCountdown = 60;
    public static Integer deathLotteryBank = 0;
    public static Boolean deathLotteryPaused = false;
    public static String recentDeathName = "";
    public static String recentDeathReason = null;
    public static String recentWinnerName = null;
    public static Integer recentWinnerAmount = 0;
    private static Plugin plugin;

    public static Plugin getPlugin() {
        return plugin;
    }

    private static void setPlugin(Plugin plugin) {
        Init.plugin = plugin;
    }

    public void onEnable() {

        setPlugin(this);

        getCommand("dlot").setExecutor(new Commands());

        getServer().getPluginManager().registerEvents(new Events(), getPlugin());

        deathLotteryStart();

        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) economy = economyProvider.getProvider();

        if (Bukkit.getOnlinePlayers().size() < totalPlayerPause) deathLotteryPaused = true;

    }

    public void onDisable() {

    }

}
