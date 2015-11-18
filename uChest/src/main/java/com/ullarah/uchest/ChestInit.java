package com.ullarah.uchest;

import com.ullarah.uchest.event.*;
import com.ullarah.uchest.function.PluginRegisters;
import com.ullarah.uchest.task.ChestAnnounce;
import com.ullarah.uchest.task.ChestClean;
import net.milkbowl.vault.economy.Economy;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import static com.ullarah.uchest.function.PluginRegisters.RegisterType.EVENT;
import static com.ullarah.uchest.function.PluginRegisters.RegisterType.TASK;

public class ChestInit extends JavaPlugin {

    public static final HashMap<String, Boolean> chestTypeEnabled = new HashMap<>();
    public static final Set<UUID> chestConvertLockout = new HashSet<>();
    public static final Set<UUID> chestRandomLockout = new HashSet<>();
    public static final Set<UUID> chestDonateLockout = new HashSet<>();
    public static final ConcurrentHashMap<UUID, Integer> chestConvertLockoutCount = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<UUID, Integer> chestRandomLockoutCount = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<UUID, Integer> chestConvertLockoutTimer = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<UUID, Integer> chestRandomLockoutTimer = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<UUID, BukkitTask> chestRandomTask = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Integer> registerMap = new ConcurrentHashMap<>();
    public static Boolean chestSwapBusy;
    public static ItemStack[] chestSwapItemStack;
    public static Player chestSwapPlayer;
    public static Integer chestAccessLevel;
    public static Integer holdingAccessLevel;
    public static Integer randomAccessLevel;
    public static Boolean allowMoneyChest = false;
    public static Boolean chestDonateLock = false;
    public static Boolean displayClearMessage = false;
    private static Plugin plugin;
    private static Economy vaultEconomy;
    private static Boolean maintenanceCheck;
    private static InventoryHolder chestDonationHolder = ChestInit::getChestDonationInventory;
    private static final Inventory chestDonationInventory = Bukkit.createInventory(getChestDonationHolder(), 54,
            ChatColor.DARK_GREEN + "Donation Chest");
    private static InventoryHolder chestRandomHolder = ChestInit::getChestRandomInventory;
    private static final Inventory chestRandomInventory = Bukkit.createInventory(getChestRandomHolder(), 54,
            ChatColor.DARK_GREEN + "Random Chest");
    private static InventoryHolder chestSwapHolder = ChestInit::getChestSwapInventory;
    private static final Inventory chestSwapInventory = Bukkit.createInventory(getChestSwapHolder(), 54,
            ChatColor.DARK_GREEN + "Swap Chest");

    public static Plugin getPlugin() {
        return plugin;
    }

    private static void setPlugin(Plugin plugin) {
        ChestInit.plugin = plugin;
    }

    public static Boolean getMaintenanceCheck() {
        return maintenanceCheck;
    }

    public static void setMaintenanceCheck(Boolean maintenanceCheck) {
        ChestInit.maintenanceCheck = maintenanceCheck;
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

        Set<String> pluginList = new HashSet<>();

        getConfig().options().copyDefaults(true);
        saveConfig();

        registerMap.put(EVENT.toString(), new PluginRegisters().register(getPlugin(), EVENT,
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

        registerMap.put(TASK.toString(), new PluginRegisters().register(getPlugin(), TASK,
                new ChestAnnounce(),
                new ChestClean()
        ));

        setMaintenanceCheck(getPlugin().getConfig().getBoolean("maintenance"));

        chestAccessLevel = getPlugin().getConfig().getInt("chestaccess");
        holdingAccessLevel = getPlugin().getConfig().getInt("holdaccess");
        randomAccessLevel = getPlugin().getConfig().getInt("ranaccess");

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

        new BukkitRunnable() {
            @Override
            public void run() {
                displayClearMessage = true;
            }
        }.runTaskLater(plugin, 100);

    }

    public void onDisable() {
    }

}