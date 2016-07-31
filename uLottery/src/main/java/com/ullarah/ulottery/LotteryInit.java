package com.ullarah.ulottery;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class LotteryInit extends JavaPlugin {

    private static Economy economy;
    private static Plugin plugin;

    public static Economy getEconomy() {
        return economy;
    }

    private static void setEconomy(Economy economy) {
        LotteryInit.economy = economy;
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    private static void setPlugin(Plugin plugin) {
        LotteryInit.plugin = plugin;
    }

    public void onEnable() {

        setPlugin(this);

        LotteryMessageInit.setDefaults(getPlugin().getConfig());

        PluginManager pluginManager = getPlugin().getServer().getPluginManager();

        Plugin pluginVault = pluginManager.getPlugin("Vault");

        getConfig().options().copyDefaults(true);
        saveConfig();

        if (pluginVault != null && getPlugin().getConfig().getBoolean("vault.enable")) {

            RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager()
                    .getRegistration(net.milkbowl.vault.economy.Economy.class);
            setEconomy(economyProvider != null ? economyProvider.getProvider() : null);

        } else {
            setEconomy(null);
            getPlugin().getLogger().log(Level.WARNING, "Economy plugin not found. Enabling item bank.");
        }

        getCommand("lottery").setExecutor(new LotteryCommands());

        pluginManager.registerEvents(new LotteryEvents(), getPlugin());

        if (getPlugin().getServer().getOnlinePlayers().size() < LotteryMessageInit.getPause().getTotal())
            LotteryMessageInit.getPause().setPaused(true);

        LotteryTask.deathLotteryStart();

    }

    public void onDisable() {

    }

}
