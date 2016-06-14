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

import static com.ullarah.ulottery.LotteryTask.deathLotteryStart;

public class LotteryInit extends JavaPlugin {

    public static Integer totalPlayerPause;
    public static final ConcurrentHashMap<UUID, Integer> playerDeathSuspension = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<UUID, Integer> playerDeathPrevious = new ConcurrentHashMap<>();
    public static Economy economy = null;
    public static Integer deathDuration = 0;
    public static Integer deathCountdown;
    public static Integer deathCountdownReset;
    public static Integer deathSuspension;
    public static Integer deathLotteryBank = 0;
    public static Boolean deathLotteryPaused = false;
    public static String recentDeathName = "";
    public static String recentDeathReason = null;
    public static String recentWinnerName = null;
    public static Integer recentWinnerAmount = 0;
    public static Integer winVaultAmount;
    public static Integer winItemAmount;
    public static Material winItemMaterial;
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

        deathLotteryStart();

    }

    public void onDisable() {

    }

}
