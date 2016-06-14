package com.ullarah.uwild;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class WildInit extends JavaPlugin {

    private static Plugin plugin;

    public static Plugin getPlugin() {
        return plugin;
    }

    private static void setPlugin(Plugin plugin) {
        WildInit.plugin = plugin;
    }

    public void onEnable(){

        setPlugin(this);

        PluginManager pluginManager = getServer().getPluginManager();

        getConfig().options().copyDefaults(true);
        saveConfig();

        pluginManager.registerEvents(new WildEvent(), getPlugin());

    }

    public void onDisable(){

    }

}
