package com.ullarah.ujoinquit;

import com.ullarah.ujoinquit.function.PluginRegisters;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class JoinQuitInit extends JavaPlugin {

    public static final ConcurrentHashMap<UUID, Integer> playerJoinMessage = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<UUID, Integer> playerQuitMessage = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<UUID, Location> playerJoinLocation = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<UUID, Integer> playerTimeout = new ConcurrentHashMap<>();
    static final List<String> joinMessages = new ArrayList<>();
    static final List<String> quitMessages = new ArrayList<>();
    public static String lastPlayer = "nobody";
    public static String joinChar;
    public static String quitChar;
    private static Plugin plugin;
    private static File playerConfigFile;
    private static YamlConfiguration playerConfig;

    static Plugin getPlugin() {
        return plugin;
    }
    private static void setPlugin(Plugin plugin) {
        JoinQuitInit.plugin = plugin;
    }

    static File getPlayerConfigFile() {
        return playerConfigFile;
    }
    private static void setPlayerConfigFile(File playerConfigFile) {
        JoinQuitInit.playerConfigFile = playerConfigFile;
    }

    static YamlConfiguration getPlayerConfig() {
        return playerConfig;
    }
    private static void setPlayerConfig(YamlConfiguration playerConfig) {
        JoinQuitInit.playerConfig = playerConfig;
    }

    @Override
    public void onEnable() {

        JoinQuitFunctions joinQuitFunctions = new JoinQuitFunctions();

        setPlugin(this);

        getConfig().options().copyDefaults(true);
        saveConfig();

        joinQuitFunctions.updateMessageHashMap();

        setPlayerConfigFile(joinQuitFunctions.updatePlayerConfigFile());
        setPlayerConfig(YamlConfiguration.loadConfiguration(getPlayerConfigFile()));

        joinChar = getConfig().getString("joinChar");
        quitChar = getConfig().getString("quitChar");

        joinQuitFunctions.updatePlayerMessageIndex();

        getCommand("jq").setExecutor(new JoinQuitExecutor());

        new PluginRegisters().registerAll(getPlugin(), PluginRegisters.RegisterType.EVENT);

    }

    @Override
    public void onDisable() {
    }

}