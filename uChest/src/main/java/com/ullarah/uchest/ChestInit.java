package com.ullarah.uchest;

import com.ullarah.uchest.function.PluginRegisters;
import net.milkbowl.vault.economy.Economy;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import static com.ullarah.uchest.function.PluginRegisters.RegisterType.EVENT;
import static com.ullarah.uchest.function.PluginRegisters.RegisterType.TASK;

public class ChestInit extends JavaPlugin {

    public static final HashMap<String, Boolean> chestTypeEnabled = new HashMap<>();
    public static final Set<UUID> chestConvertLockout = new HashSet<>();
    public static final Set<UUID> chestRandomLockout = new HashSet<>();
    public static final Set<UUID> chestDonateLockout = new HashSet<>();
    public static final ConcurrentHashMap<UUID, Integer> chestConvertLockoutTimer = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<UUID, Integer> chestRandomLockoutTimer = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<UUID, BukkitTask> chestRandomTask = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Integer> registerMap = new ConcurrentHashMap<>();
    public static FileConfiguration materialConfig;
    public static Boolean chestSwapBusy;
    public static ItemStack[] chestSwapItemStack;
    public static Player chestSwapPlayer;
    public static Boolean allowMoneyChest = false;
    public static Boolean chestDonateLock = false;
    public static Boolean displayClearMessage = false;
    private static Plugin plugin;
    private static Economy vaultEconomy;
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

    public static Economy getVaultEconomy() {
        return vaultEconomy;
    }

    private static void setVaultEconomy(Economy vaultEconomy) {
        ChestInit.vaultEconomy = vaultEconomy;
    }

    public static FileConfiguration getMaterialConfig() {
        return materialConfig;
    }

    private static void setMaterialConfig(FileConfiguration materialConfig) {
        ChestInit.materialConfig = materialConfig;
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

        registerMap.put(EVENT.toString(), new PluginRegisters().registerAll(getPlugin(), EVENT));
        registerMap.put(TASK.toString(), new PluginRegisters().registerAll(getPlugin(), TASK));

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

        ArrayList<String> chestCommands = new ArrayList<String>() {{
            add("");
            add("d");
            add("h");
            add("r");
            add("s");
            add("v");
            add("x");
        }};
        if (allowMoneyChest) chestCommands.add("m");
        for (String t : chestCommands) {
            getCommand(t + "chest").setExecutor(new ChestExecutor());
            chestTypeEnabled.put(t + "chest", getPlugin().getConfig().getBoolean(t + "chest.enabled"));
        }

        File materialFile = new File(getDataFolder(), "material.yml");
        if (!materialFile.exists()) saveResource("material.yml", false);
        setMaterialConfig(YamlConfiguration.loadConfiguration(materialFile));

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