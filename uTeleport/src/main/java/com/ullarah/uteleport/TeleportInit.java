package com.ullarah.uteleport;

import org.bukkit.plugin.java.JavaPlugin;

public class TeleportInit extends JavaPlugin {

    private final TeleportFunctions teleportFunctions = new TeleportFunctions(this);

    public void onEnable() {

        getConfig().options().copyDefaults(true);
        saveConfig();

        getCommand("utp").setExecutor(new TeleportCommands(this, teleportFunctions));

        getServer().getPluginManager().registerEvents(new TeleportEvents(this, teleportFunctions), this);

    }

    public void onDisable() {


    }

}