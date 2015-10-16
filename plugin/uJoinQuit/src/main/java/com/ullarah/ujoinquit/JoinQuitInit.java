package com.ullarah.ujoinquit;

import com.ullarah.ujoinquit.event.MessageClick;
import com.ullarah.ujoinquit.event.OptionClick;
import com.ullarah.ujoinquit.event.PlayerJoin;
import com.ullarah.ujoinquit.event.PlayerQuit;
import com.ullarah.ulib.function.PluginRegisters;
import org.bukkit.ChatColor;
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
    private static Plugin plugin;
    private static String msgPrefix = null;
    private static String msgPermDeny = null;
    private static String msgNoConsole = null;
    private static File playerConfigFile;
    private static YamlConfiguration playerConfig;

    public static Plugin getPlugin() {
        return plugin;
    }
    private static void setPlugin(Plugin plugin) {
        JoinQuitInit.plugin = plugin;
    }

    public static String getMsgPrefix() {
        return msgPrefix;
    }
    private static void setMsgPrefix(String msgPrefix) {
        JoinQuitInit.msgPrefix = msgPrefix;
    }

    public static String getMsgPermDeny() {
        return msgPermDeny;
    }
    private static void setMsgPermDeny(String msgPermDeny) {
        JoinQuitInit.msgPermDeny = msgPermDeny;
    }

    public static String getMsgNoConsole() {
        return msgNoConsole;
    }
    private static void setMsgNoConsole(String msgNoConsole) {
        JoinQuitInit.msgNoConsole = msgNoConsole;
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

        setMsgPrefix(ChatColor.GOLD + "[" + getPlugin().getName() + "] " + ChatColor.RESET);

        getConfig().options().copyDefaults(true);
        saveConfig();

        updateMessageHashMap();

        setPlayerConfigFile(updatePlayerConfigFile());
        setPlayerConfig(YamlConfiguration.loadConfiguration(getPlayerConfigFile()));

        updatePlayerMessageIndex();

        getCommand("jq").setExecutor(new JoinQuitExecutor());

        PluginRegisters.register(getPlugin(), EVENT,
                new MessageClick(),
                new OptionClick(),
                new PlayerJoin(),
                new PlayerQuit()
        );

        setMsgPermDeny(getMsgPrefix() + ChatColor.RED + "No permission.");
        setMsgNoConsole(getMsgPrefix() + ChatColor.RED + "No console usage.");

    }

    @Override
    public void onDisable() {
    }

}