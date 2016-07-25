package com.ullarah.ulottery;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

public class LotteryInit extends JavaPlugin {

    static final ConcurrentHashMap<UUID, Integer> playerDeathSuspension = new ConcurrentHashMap<>();
    static final ConcurrentHashMap<UUID, Integer> playerDeathPrevious = new ConcurrentHashMap<>();
    static Integer totalPlayerPause;
    static Economy economy = null;
    static Integer deathDuration = 0;
    static Integer deathCountdown;
    static Integer deathCountdownReset;
    static Integer deathSuspension;
    static String deathRecent;
    static Integer deathLotteryBank = 0;
    static Boolean deathLotteryPaused = false;
    static String recentDeathName = "";
    static String recentDeathReason = null;
    static String recentWinnerName = null;
    static Integer recentWinnerAmount = 0;
    static Integer winVaultAmount;
    static Integer winItemAmount;
    static Material winItemMaterial;
    private static Plugin plugin;

    static Plugin getPlugin() {
        return plugin;
    }

    private static void setPlugin(Plugin plugin) {
        LotteryInit.plugin = plugin;
    }

    public void onEnable() {

        setPlugin(this);

        PluginManager pluginManager = getPlugin().getServer().getPluginManager();

        Plugin pluginVault = pluginManager.getPlugin("Vault");

        getConfig().options().copyDefaults(true);
        saveConfig();

        totalPlayerPause = getPlugin().getConfig().getInt("activeplayers");
        deathCountdown = getPlugin().getConfig().getInt("countdown");
        deathCountdownReset = getPlugin().getConfig().getInt("countdown");

        deathSuspension = getPlugin().getConfig().getInt("suspension");

        winVaultAmount = getPlugin().getConfig().getInt("vault.addamount");
        winItemAmount = getPlugin().getConfig().getInt("item.addamount");
        winItemMaterial = Material.getMaterial(getPlugin().getConfig().getString("item.material"));

        if (pluginVault != null && getPlugin().getConfig().getBoolean("vault.enable")) {

            RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager()
                    .getRegistration(net.milkbowl.vault.economy.Economy.class);
            if (economyProvider != null) economy = economyProvider.getProvider();

        } else getPlugin().getLogger().log(Level.WARNING, "Economy plugin not found. Enabling item bank.");

        getCommand("dlot").setExecutor(new LotteryCommands());

        pluginManager.registerEvents(new LotteryEvents(), getPlugin());

        if (getPlugin().getServer().getOnlinePlayers().size() < totalPlayerPause)
            deathLotteryPaused = true;

        LotteryTask.deathLotteryStart();

    }

    public void onDisable() {

    }

}
