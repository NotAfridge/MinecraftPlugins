package com.ullarah.ujoinquit;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.ullarah.ujoinquit.JoinQuitFunctions.*;

public class JoinQuitInit extends JavaPlugin {

    public static final HashMap<UUID, Integer> playerJoinMessage = new HashMap<>();
    public static final HashMap<UUID, Integer> playerQuitMessage = new HashMap<>();
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

        setMsgPrefix(ChatColor.GOLD + "[uJoinQuit] " + ChatColor.RESET);

        getConfig().options().copyDefaults(true);
        saveConfig();

        updateMessageHashMap();
        setPlayerConfigFile(updatePlayerConfigFile());
        setPlayerConfig(YamlConfiguration.loadConfiguration(getPlayerConfigFile()));
        updatePlayerMessageIndex();

        getCommand("jq").setExecutor(new JoinQuitExecutor());
        getServer().getPluginManager().registerEvents(new JoinQuitEvents(), getPlugin());

        setMsgPermDeny(getMsgPrefix() + ChatColor.RED + "No permission.");
        setMsgNoConsole(getMsgPrefix() + ChatColor.RED + "No console usage.");

    }

    @Override
    public void onDisable() {
    }

}