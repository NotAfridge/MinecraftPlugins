package com.ullarah.uchest;

import com.ullarah.uchest.function.PluginRegisters;
import net.milkbowl.vault.economy.Economy;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
    public static final ConcurrentHashMap<String, ConcurrentHashMap<UUID, Integer>> chestLockoutMap = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<UUID, BukkitTask> chestRandomTask = new ConcurrentHashMap<>();
    public static final HashMap<ItemStack, Object[]> materialMap = new HashMap<>();
    private static final ConcurrentHashMap<String, Integer> registerMap = new ConcurrentHashMap<>();
    public static Boolean chestSwapBusy;
    public static ItemStack[] chestSwapItemStack;
    public static Player chestSwapPlayer;
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

        new ArrayList<String>() {{
            add("config.yml");
        }}.stream().filter(r -> !new File(getPlugin().getDataFolder(), r).exists())
                .forEachOrdered(r -> getPlugin().saveResource(r, false));

        initMaterials();

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
                pluginList.add("Vault");
            }
        }

        for (String t : new ArrayList<String>() {{
            add("");
            add("d");
            add("e");
            add("h");
            add("m");
            add("r");
            add("s");
            add("v");
            add("x");
        }}) {
            chestLockoutMap.put(t + "chest", new ConcurrentHashMap<>());
            chestTypeEnabled.put(t + "chest", getPlugin().getConfig().getBoolean(t + "chest.enabled"));
            getCommand(t + "chest").setExecutor(new ChestExecutor());
        }

        chestLockoutMap.put("dchest_itemlock", new ConcurrentHashMap<>());

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

    private void initMaterials() {

        boolean materialFileCreation = true;

        File dataDir = getPlugin().getDataFolder();
        if (!dataDir.exists()) materialFileCreation = dataDir.mkdir();

        File materialDir = new File(dataDir + File.separator + "material");
        if (!materialDir.exists()) materialFileCreation = materialDir.mkdir();

        if (materialFileCreation) {

            String[] materialFiles = {"brewing.yml", "building.yml", "combat.yml", "decoration.yml", "foodstuffs.yml",
                    "materials.yml", "miscellaneous.yml", "redstone.yml", "tools.yml", "transportation.yml", "custom.yml"};

            int materialCount = 0;

            for (String f : materialFiles) {

                if (!new File(materialDir, f).exists())
                    getPlugin().saveResource("material" + File.separator + f, false);

                File materialConfigFile = new File(materialDir + File.separator + f);

                if (materialConfigFile.exists()) {

                    FileConfiguration materialConfig = YamlConfiguration.loadConfiguration(materialConfigFile);

                    for (String m : new ArrayList<>(materialConfig.getKeys(false))) {

                        Object[] materialObject = {
                                materialConfig.getBoolean(m + ".d"),
                                materialConfig.getBoolean(m + ".r"),
                                materialConfig.getDouble(m + ".e"),
                                materialConfig.getDouble(m + ".x")
                        };

                        materialMap.put(new ItemStack(
                                Material.getMaterial(materialConfig.getString(m + ".m")), 1,
                                (short) materialConfig.getInt(m + ".v")), materialObject
                        );

                        materialCount++;

                    }

                }

            }

            Bukkit.getLogger().log(Level.INFO, "[" + plugin.getName() + "] "
                    + "Loaded " + materialCount + " items from " + materialFiles.length + " files.");

        }

    }

}