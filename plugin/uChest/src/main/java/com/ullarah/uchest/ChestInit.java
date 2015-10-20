package com.ullarah.uchest;

import com.ullarah.uchest.event.*;
import com.ullarah.uchest.task.ChestAnnounce;
import com.ullarah.uchest.task.ChestClean;
import com.ullarah.uchest.task.ChestExperienceTimer;
import com.ullarah.uchest.task.ChestRandomTimer;
import com.ullarah.ulib.function.PluginRegisters;
import net.milkbowl.vault.economy.Economy;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

import static com.ullarah.ulib.function.PluginRegisters.RegisterType.EVENT;
import static com.ullarah.ulib.function.PluginRegisters.RegisterType.TASK;

public class ChestInit extends JavaPlugin {

    public static final HashMap<UUID, InventoryView> chestRandomRemoveXP = new HashMap<>();
    public static final HashMap<String, Boolean> chestTypeEnabled = new HashMap<>();
    public static final Set<UUID> chestLockout = new HashSet<>();
    public static final Set<UUID> chestDonateLockout = new HashSet<>();
    public static final HashMap<UUID, Integer> chestLockoutCount = new HashMap<>();
    private static final HashMap<String, Integer> registerMap = new HashMap<>();
    private static final Inventory chestDonationInventory = Bukkit.createInventory(getChestDonationHolder(), 54,
            ChatColor.DARK_GREEN + "Donation Chest");
    private static final Inventory chestRandomInventory = Bukkit.createInventory(getChestRandomHolder(), 54,
            ChatColor.DARK_GREEN + "Random Chest");
    private static final Inventory chestSwapInventory = Bukkit.createInventory(getChestSwapHolder(), 54,
            ChatColor.DARK_GREEN + "Swap Chest");
    public static Boolean chestSwapBusy;
    public static ItemStack[] chestSwapItemStack;
    public static Player chestSwapPlayer;
    public static Integer chestAccessLevel;
    public static Integer holdingAccessLevel;
    public static Boolean allowMoneyChest = false;
    public static Boolean chestDonateLock = false;
    private static Plugin plugin;
    private static Economy vaultEconomy;
    private static String msgPrefix = null;
    private static String msgPermDeny = null;
    private static String msgNoConsole = null;
    private static Boolean maintenanceCheck;
    private static String maintenanceMessage;
    private static InventoryHolder chestDonationHolder = ChestInit::getChestDonationInventory;
    private static InventoryHolder chestRandomHolder = ChestInit::getChestRandomInventory;
    private static InventoryHolder chestSwapHolder = ChestInit::getChestSwapInventory;

    public static Plugin getPlugin() {
        return plugin;
    }

    private static void setPlugin(Plugin plugin) {
        ChestInit.plugin = plugin;
    }

    public static String getMsgPrefix() {
        return msgPrefix;
    }

    private static void setMsgPrefix(String msgPrefix) {
        ChestInit.msgPrefix = msgPrefix;
    }

    public static String getMsgPermDeny() {
        return msgPermDeny;
    }

    private static void setMsgPermDeny(String msgPermDeny) {
        ChestInit.msgPermDeny = msgPermDeny;
    }

    public static String getMsgNoConsole() {
        return msgNoConsole;
    }

    private static void setMsgNoConsole(String msgNoConsole) {
        ChestInit.msgNoConsole = msgNoConsole;
    }

    public static Boolean getMaintenanceCheck() {
        return maintenanceCheck;
    }

    public static void setMaintenanceCheck(Boolean maintenanceCheck) {
        ChestInit.maintenanceCheck = maintenanceCheck;
    }

    public static String getMaintenanceMessage() {
        return maintenanceMessage;
    }

    private static void setMaintenanceMessage(String maintenanceMessage) {
        ChestInit.maintenanceMessage = maintenanceMessage;
    }

    public static Economy getVaultEconomy() {
        return vaultEconomy;
    }

    private static void setVaultEconomy(Economy vaultEconomy) {
        ChestInit.vaultEconomy = vaultEconomy;
    }

    public static InventoryHolder getChestDonationHolder() {
        return chestDonationHolder;
    }

    private static void setChestDonationHolder(InventoryHolder chestDonationHolder) {
        ChestInit.chestDonationHolder = chestDonationHolder;
    }

    public static InventoryHolder getChestRandomHolder() {
        return chestRandomHolder;
    }

    private static void setChestRandomHolder(InventoryHolder chestRandomHolder) {
        ChestInit.chestRandomHolder = chestRandomHolder;
    }

    public static InventoryHolder getChestSwapHolder() {
        return chestSwapHolder;
    }

    private static void setChestSwapHolder(InventoryHolder chestSwapHolder) {
        ChestInit.chestSwapHolder = chestSwapHolder;
    }

    public static Inventory getChestDonationInventory() {
        return chestDonationInventory;
    }

    public static Inventory getChestRandomInventory() {
        return chestRandomInventory;
    }

    private static Inventory getChestSwapInventory() {
        return chestSwapInventory;
    }

    public void onEnable() {

        setPlugin(this);

        PluginManager pluginManager = getServer().getPluginManager();
        Plugin pluginVault = pluginManager.getPlugin("Vault");

        setMsgPrefix(ChatColor.GOLD + "[" + plugin.getName() + "] " + ChatColor.WHITE);

        Set<String> pluginList = new HashSet<>();

        getConfig().options().copyDefaults(true);
        saveConfig();

        registerMap.put(EVENT.toString(), PluginRegisters.register(getPlugin(), EVENT,
                new ChestClick(),
                new ChestClose(),
                new ChestDrag(),
                new ChestInteract(),
                new ChestInteract(),
                new ChestOpen(),
                new PlayerDeath(),
                new PlayerQuit(),
                new PlayerJoin()
        ));

        registerMap.put(TASK.toString(), PluginRegisters.register(getPlugin(), TASK,
                new ChestAnnounce(),
                new ChestClean(),
                new ChestExperienceTimer(),
                new ChestRandomTimer()
        ));

        setMsgPermDeny(getMsgPrefix() + ChatColor.RED + "No permission.");
        setMsgNoConsole(getMsgPrefix() + ChatColor.RED + "No console usage.");

        setMaintenanceCheck(getPlugin().getConfig().getBoolean("maintenance"));
        setMaintenanceMessage(getMsgPrefix() + ChatColor.RED + "The Chest System is unavailable.");

        chestAccessLevel = getPlugin().getConfig().getInt("chestaccess");
        holdingAccessLevel = getPlugin().getConfig().getInt("holdaccess");

        for (String t : new String[]{"", "d", "h", "m", "r", "s", "v", "x"}) {
            getCommand(t + "chest").setExecutor(new ChestExecutor());
            chestTypeEnabled.put(t + "chest", getPlugin().getConfig().getBoolean(t + "chestenabled"));
        }

        setChestDonationHolder(chestDonationHolder);
        setChestRandomHolder(chestRandomHolder);
        setChestSwapHolder(chestSwapHolder);

        chestSwapBusy = false;

        if (pluginVault != null) {
            RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(
                    net.milkbowl.vault.economy.Economy.class);
            if (economyProvider != null) {
                setVaultEconomy(economyProvider.getProvider());
                allowMoneyChest = true;
                pluginList.add("Vault");
            }
        }

        Bukkit.getLogger().log(Level.INFO, "[" + plugin.getName() + "] "
                + "Events: " + registerMap.get(EVENT.toString()) + " | "
                + "Tasks: " + registerMap.get(TASK.toString()));

        if (pluginList.size() > 0)
            Bukkit.getLogger().log(Level.INFO,
                    "[" + plugin.getName() + "] Hooked: " + StringUtils.join(pluginList, ", "));

    }

    public void onDisable() {
    }

}