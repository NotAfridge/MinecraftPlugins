package com.ullarah.uauction;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class Init extends JavaPlugin {

    public static final List<String> activeAuctions = new ArrayList<>();
    public static final List<Player> chatMembers = new ArrayList<>();
    public static final HashMap<String, UUID> winnerAlerts = new HashMap<>();

    public static Logger log;
    public static Economy economy = null;
    private static Plugin plugin;
    private static String msgPrefix = null;
    private static String msgChatPrefix = null;
    private static String msgPermDeny = null;
    private static String msgNoConsole = null;

    private static Boolean maintenanceCheck;
    private static String maintenanceMessage;

    public static Plugin getPlugin() {
        return plugin;
    }

    private static void setPlugin(Plugin plugin) {
        Init.plugin = plugin;
    }

    public static String getMsgPrefix() {
        return msgPrefix;
    }

    private static void setMsgPrefix(String msgPrefix) {
        Init.msgPrefix = msgPrefix;
    }

    public static String getMsgChatPrefix() {
        return msgChatPrefix;
    }

    private static void setMsgChatPrefix(String msgChatPrefix) {
        Init.msgChatPrefix = msgChatPrefix;
    }

    public static String getMsgPermDeny() {
        return msgPermDeny;
    }

    private static void setMsgPermDeny(String msgPermDeny) {
        Init.msgPermDeny = msgPermDeny;
    }

    public static String getMsgNoConsole() {
        return msgNoConsole;
    }

    private static void setMsgNoConsole(String msgNoConsole) {
        Init.msgNoConsole = msgNoConsole;
    }

    public static Boolean getMaintenanceCheck() {
        return maintenanceCheck;
    }

    public static void setMaintenanceCheck(Boolean maintenanceCheck) {
        Init.maintenanceCheck = maintenanceCheck;
    }

    public static String getMaintenanceMessage() {
        return maintenanceMessage;
    }

    private static void setMaintenanceMessage(String maintenanceMessage) {
        Init.maintenanceMessage = maintenanceMessage;
    }

    @SuppressWarnings("serial")
    public void onEnable() {

        log = getLogger();

        setMsgPrefix(ChatColor.GOLD + "[uAuction] " + ChatColor.RESET);
        setMsgChatPrefix(ChatColor.DARK_GREEN + "[uAuctionChat] " + ChatColor.RESET);

        setPlugin(this);

        PluginManager pluginManager = getServer().getPluginManager();

        getCommand("auction").setExecutor(new Commands());
        getCommand("alist").setExecutor(new Commands());
        getCommand("abid").setExecutor(new Commands());
        getCommand("aview").setExecutor(new Commands());
        getCommand("ainfo").setExecutor(new Commands());
        getCommand("acollect").setExecutor(new Commands());
        getCommand("achat").setExecutor(new Commands());
        getCommand("awon").setExecutor(new Commands());

        pluginManager.registerEvents(new Events(), getPlugin());

        setMsgPermDeny(getMsgPrefix() + ChatColor.RED + "No permission.");
        setMsgNoConsole(getMsgPrefix() + ChatColor.RED + "No console usage.");

        setMaintenanceCheck(Init.getPlugin().getConfig().getBoolean("maintenance"));
        setMaintenanceMessage(getMsgPrefix() + ChatColor.RED + "Auctions are under maintenance.");

        setupEconomy();

        File configFile = new File(Init.getPlugin().getDataFolder(), "config.yml");
        if (!(configFile.exists())) Init.getPlugin().saveResource("config.yml", true);

        Tasks.runHousekeeping();
        Tasks.runAuctionTimers();
        Tasks.collectionReminder();
        Tasks.runChatMessages();

    }

    public void onDisable() {
    }

    private void setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) economy = economyProvider.getProvider();
    }

}
