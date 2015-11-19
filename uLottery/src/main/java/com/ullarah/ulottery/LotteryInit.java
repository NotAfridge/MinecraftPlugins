package com.ullarah.ulottery;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import static com.ullarah.ulottery.LotteryTask.deathLotteryStart;

public class LotteryInit extends JavaPlugin {

    public static final Integer totalPlayerPause = 6;
    public static final ConcurrentHashMap<UUID, Integer> playerDeathSuspension = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<UUID, Integer> playerDeathPrevious = new ConcurrentHashMap<>();
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
        LotteryInit.plugin = plugin;
    }

    public void onEnable() {

        setPlugin(this);

        PluginManager pluginManager = getPlugin().getServer().getPluginManager();

        Plugin pluginVault = pluginManager.getPlugin("Vault");

        if (pluginVault != null) {

            getCommand("dlot").setExecutor(new LotteryCommands());

            pluginManager.registerEvents(new LotteryEvents(), getPlugin());

            deathLotteryStart();

            RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager()
                    .getRegistration(net.milkbowl.vault.economy.Economy.class);
            if (economyProvider != null) economy = economyProvider.getProvider();

            if (getPlugin().getServer().getOnlinePlayers().size() < totalPlayerPause)
                deathLotteryPaused = true;

        } else {

            getPlugin().getLogger().log(Level.SEVERE, "Vault plugin not found. Disabling uLottery.");
            pluginManager.disablePlugin(getPlugin());

        }

    }

    public void onDisable() {

    }

}
