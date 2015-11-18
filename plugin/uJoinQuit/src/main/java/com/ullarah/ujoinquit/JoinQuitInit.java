package com.ullarah.ujoinquit;

import com.ullarah.ujoinquit.event.MessageClick;
import com.ullarah.ujoinquit.event.OptionClick;
import com.ullarah.ujoinquit.event.PlayerJoin;
import com.ullarah.ujoinquit.event.PlayerQuit;
import com.ullarah.ulib.function.PluginRegisters;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.ullarah.ujoinquit.JoinQuitFunctions.*;
import static com.ullarah.ulib.function.PluginRegisters.RegisterType.EVENT;

public class JoinQuitInit extends JavaPlugin {

    public static final ConcurrentHashMap<UUID, Integer> playerJoinMessage = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<UUID, Integer> playerQuitMessage = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<UUID, Location> playerJoinLocation = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<UUID, Integer> playerTimeout = new ConcurrentHashMap<>();
    public static final List<String> joinMessages = new ArrayList<>();
    public static final List<String> quitMessages = new ArrayList<>();
    public static String lastPlayer = "nobody";
    public static String joinChar;
    public static String quitChar;
    private static Plugin plugin;
    private static File playerConfigFile;
    private static YamlConfiguration playerConfig;

    public static Plugin getPlugin() {
        return plugin;
    }
    private static void setPlugin(Plugin plugin) {
        JoinQuitInit.plugin = plugin;
    }

    public static File getPlayerConfigFile() {
        return playerConfigFile;
    }
    private static void setPlayerConfigFile(File playerConfigFile) {
        JoinQuitInit.playerConfigFile = playerConfigFile;
    }

    public static YamlConfiguration getPlayerConfig() {
        return playerConfig;
    }
    private static void setPlayerConfig(YamlConfiguration playerConfig) {
        JoinQuitInit.playerConfig = playerConfig;
    }

    @Override
    public void onEnable() {

        setPlugin(this);

        getConfig().options().copyDefaults(true);
        saveConfig();

        updateMessageHashMap();

        setPlayerConfigFile(updatePlayerConfigFile());
        setPlayerConfig(YamlConfiguration.loadConfiguration(getPlayerConfigFile()));

        joinChar = getConfig().getString("joinChar");
        quitChar = getConfig().getString("quitChar");

        updatePlayerMessageIndex();

        getCommand("jq").setExecutor(new JoinQuitExecutor());

        new PluginRegisters().register(getPlugin(), EVENT,
                new MessageClick(),
                new OptionClick(),
                new PlayerJoin(),
                new PlayerQuit()
        );

    }

    @Override
    public void onDisable() {
    }

}