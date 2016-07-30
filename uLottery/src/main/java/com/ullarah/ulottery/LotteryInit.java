package com.ullarah.ulottery;

import com.ullarah.ulottery.message.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class LotteryInit extends JavaPlugin {

    public static RecentWinner recentWinner = new RecentWinner();
    public static Economy economy = null;
    static Bank bank = new Bank();
    static Block block = new Block();
    static Countdown countdown = new Countdown();
    static Duration duration = new Duration();
    static History history = new History();
    static Pause pause = new Pause();
    static RecentDeath recentDeath = new RecentDeath();
    static Suspension suspension = new Suspension();
    private static Exclude exclude;
    private static Plugin plugin;

    public static Plugin getPlugin() {
        return plugin;
    }
    private static void setPlugin(Plugin plugin) {
        LotteryInit.plugin = plugin;
    }

    static Exclude getExclude() {
        return exclude;
    }

    private static void setExclude(Exclude exclude) {
        LotteryInit.exclude = exclude;
    }

    public void onEnable() {

        setPlugin(this);
        setExclude(new Exclude());

        PluginManager pluginManager = getPlugin().getServer().getPluginManager();

        Plugin pluginVault = pluginManager.getPlugin("Vault");

        getConfig().options().copyDefaults(true);
        saveConfig();

        pause.setTotal(getPlugin().getConfig().getInt("players"));
        countdown.setCount(getPlugin().getConfig().getInt("countdown"));
        countdown.setOriginal(getPlugin().getConfig().getInt("countdown"));
        suspension.setTime(getPlugin().getConfig().getInt("suspension"));

        block.setLimit(getPlugin().getConfig().getInt("blocks"));

        recentWinner.setVaultAmount(getPlugin().getConfig().getInt("vault.amount"));
        recentWinner.setItemAmount(getPlugin().getConfig().getInt("item.amount"));

        Material winItem = Material.getMaterial(getPlugin().getConfig().getString("item.material"));

        recentWinner.setItemMaterial(winItem);
        bank.setItemMaterial(winItem);
        history.setItemMaterial(winItem);

        if (pluginVault != null && getPlugin().getConfig().getBoolean("vault.enable")) {

            RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager()
                    .getRegistration(net.milkbowl.vault.economy.Economy.class);
            if (economyProvider != null) economy = economyProvider.getProvider();

        } else getPlugin().getLogger().log(Level.WARNING, "Economy plugin not found. Enabling item bank.");

        getCommand("lottery").setExecutor(new LotteryCommands());

        pluginManager.registerEvents(new LotteryEvents(), getPlugin());

        if (getPlugin().getServer().getOnlinePlayers().size() < pause.getTotal())
            pause.setPaused(true);

        LotteryTask.deathLotteryStart();

    }

    public void onDisable() {

    }

}
