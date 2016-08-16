package com.ullarah.uwild;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class WildInit extends JavaPlugin {

    public void onEnable(){

        PluginManager pluginManager = getServer().getPluginManager();

        getConfig().options().copyDefaults(true);
        saveConfig();

        pluginManager.registerEvents(new WildEvent(this), this);

    }

    public void onDisable(){

    }

}
