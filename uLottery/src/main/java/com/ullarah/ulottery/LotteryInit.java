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

    static final Bank bank = new Bank();
    static final Block block = new Block();
    static final Countdown countdown = new Countdown();
    static final Duration duration = new Duration();
    static final Pause pause = new Pause();
    static final RecentDeath recentDeath = new RecentDeath();
    static final RecentWinner recentWinner = new RecentWinner();
    static final Suspension suspension = new Suspension();

    public static Economy economy = null;
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

        pause.setTotal(getPlugin().getConfig().getInt("players"));
        countdown.setCount(getPlugin().getConfig().getInt("countdown"));
        countdown.setOriginal(getPlugin().getConfig().getInt("countdown"));
        suspension.setTime(getPlugin().getConfig().getInt("suspension"));

        block.setTotal(getPlugin().getConfig().getInt("blocks"));

        recentWinner.setVaultAmount(getPlugin().getConfig().getInt("vault.amount"));
        recentWinner.setItemAmount(getPlugin().getConfig().getInt("item.amount"));
        recentWinner.setItemMaterial(Material.getMaterial(getPlugin().getConfig().getString("item.material")));

        bank.setItemMaterial(Material.getMaterial(getPlugin().getConfig().getString("item.material")));

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
