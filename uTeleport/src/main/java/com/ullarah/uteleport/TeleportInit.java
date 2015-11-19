package com.ullarah.uteleport;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TeleportInit extends JavaPlugin {

    public static final ConcurrentHashMap<UUID, ConcurrentHashMap<ArrayList<Location>, ArrayList<Date>>> historyMap = new ConcurrentHashMap<>();
    public static final HashSet<UUID> historyBlock = new HashSet<>();
    private static Plugin plugin;

    public static Plugin getPlugin() {
        return plugin;
    }

    private static void setPlugin(Plugin plugin) {
        TeleportInit.plugin = plugin;
    }

    public void onEnable() {

        setPlugin(this);

        getConfig().options().copyDefaults(true);
        saveConfig();

        getCommand("utp").setExecutor(new TeleportCommands());

        getServer().getPluginManager().registerEvents(new TeleportEvents(), getPlugin());

    }

    public void onDisable() {


    }

}