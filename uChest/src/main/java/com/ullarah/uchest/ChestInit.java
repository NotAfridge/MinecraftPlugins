package com.ullarah.uchest;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.ullarah.uchest.data.MaterialData;
import com.ullarah.uchest.function.PluginRegisters;
import com.ullarah.uchest.init.ChestLanguage;
import net.milkbowl.vault.economy.Economy;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

public class ChestInit extends JavaPlugin {

    public static final HashMap<String, Boolean> chestTypeEnabled = new HashMap<>();
    public static final ConcurrentHashMap<String, ConcurrentHashMap<UUID, Integer>> chestLockoutMap = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<UUID, BukkitTask> chestRandomTask = new ConcurrentHashMap<>();
    public static final HashMap<ItemStack, MaterialData> materialMap = new HashMap<>();
    private static final ConcurrentHashMap<String, Integer> registerMap = new ConcurrentHashMap<>();
    public static Boolean chestSwapBusy;
    public static ItemStack[] chestSwapItemStack;
    public static Player chestSwapPlayer;
    public static Boolean chestDonateLock = false;
    public static Boolean displayClearMessage = false;
    private static Integer materialCount = 0;
    private static Plugin plugin;
    private static Economy vaultEconomy;
    private static WorldGuardPlugin worldGuard;
    private static Inventory chestDonationInventory;
    private static InventoryHolder chestDonationHolder = ChestInit::getChestDonationInventory;
    private static Inventory chestRandomInventory;
    private static InventoryHolder chestRandomHolder = ChestInit::getChestRandomInventory;
    private static Inventory chestShuffleInventory;
    private static Inventory chestSwapInventory;
    private static InventoryHolder chestSwapHolder = ChestInit::getChestSwapInventory;

    public static Plugin getPlugin() {
        return plugin;
    }

    private static void setPlugin(Plugin plugin) {
        ChestInit.plugin = plugin;
    }

    static Economy getVaultEconomy() {
        return vaultEconomy;
    }

    private static void setVaultEconomy(Economy vaultEconomy) {
        ChestInit.vaultEconomy = vaultEconomy;
    }

    public static WorldGuardPlugin getWorldGuard() {
        return worldGuard;
    }

    private static void setWorldGuard(WorldGuardPlugin worldGuard) {
        ChestInit.worldGuard = worldGuard;
    }

    private static InventoryHolder getChestDonationHolder() {
        return chestDonationHolder;
    }

    private static void setChestDonationHolder(InventoryHolder chestDonationHolder) {
        ChestInit.chestDonationHolder = chestDonationHolder;
    }

    private static InventoryHolder getChestRandomHolder() {
        return chestRandomHolder;
    }

    private static void setChestRandomHolder(InventoryHolder chestRandomHolder) {
        ChestInit.chestRandomHolder = chestRandomHolder;
    }

    private static InventoryHolder getChestSwapHolder() {
        return chestSwapHolder;
    }

    private static void setChestSwapHolder(InventoryHolder chestSwapHolder) {
        ChestInit.chestSwapHolder = chestSwapHolder;
    }

    public static Inventory getChestDonationInventory() {
        return chestDonationInventory;
    }

    private static void setChestDonationInventory(Inventory inventory) {
        chestDonationInventory = inventory;
    }

    static Inventory getChestRandomInventory() {
        return chestRandomInventory;
    }

    private static void setChestRandomInventory(Inventory inventory) {
        chestRandomInventory = inventory;
    }

    public static Inventory getChestSwapInventory() {
        return chestSwapInventory;
    }

    private static void setChestSwapInventory(Inventory inventory) {
        chestSwapInventory = inventory;
    }

    public static Inventory getChestShuffleInventory() {
        return chestShuffleInventory;
    }

    private static void setChestShuffleInventory(Inventory inventory) {
        chestShuffleInventory = inventory;
    }

    public void onEnable() {

        setPlugin(this);

        PluginManager pluginManager = getServer().getPluginManager();
        Plugin pluginVault = pluginManager.getPlugin("Vault");
        Plugin pluginWorldGuard = pluginManager.getPlugin("WorldGuard");

        Set<String> pluginList = new HashSet<>();

        if (!new File(getPlugin().getDataFolder(), "config.yml").exists())
            getPlugin().saveResource("config.yml", false);

        initMaterials();

        for (PluginRegisters.RegisterType register : PluginRegisters.RegisterType.values())
            registerMap.put(register.toString(), new PluginRegisters().registerAll(getPlugin(), register));

        setChestDonationInventory(Bukkit.createInventory(getChestDonationHolder(),
                getPlugin().getConfig().getInt("dchest.size"), ChestLanguage.N_DCHEST));

        setChestRandomInventory(Bukkit.createInventory(getChestRandomHolder(),
                getPlugin().getConfig().getInt("rchest.size"), ChestLanguage.N_RCHEST));

        setChestShuffleInventory(Bukkit.createInventory(null,
                getPlugin().getConfig().getInt("schest.size"), ChestLanguage.N_SCHEST));

        setChestSwapInventory(Bukkit.createInventory(getChestSwapHolder(),
                getPlugin().getConfig().getInt("wchest.size"), ChestLanguage.N_WCHEST));

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

        if (pluginWorldGuard != null) {
            setWorldGuard((WorldGuardPlugin) pluginWorldGuard);
            pluginList.add("WorldGuard");
        }

        for (String c : new String[]{"", "d", "e", "h", "m", "r", "s", "v", "w", "x"}) {
            chestLockoutMap.put(c + "chest", new ConcurrentHashMap<>());
            chestTypeEnabled.put(c + "chest", getPlugin().getConfig().getBoolean(c + "chest.enabled"));
            getCommand(c + "chest").setExecutor(new ChestExecutor());
        }

        chestLockoutMap.put("dchest_itemlock", new ConcurrentHashMap<>());

        getPlugin().getLogger().log(Level.INFO, "[" + plugin.getName() + "] "
                + "Events: " + registerMap.get(PluginRegisters.RegisterType.EVENT.toString()) + " | "
                + "Tasks: " + registerMap.get(PluginRegisters.RegisterType.TASK.toString()));

        if (pluginList.size() > 0)
            getPlugin().getLogger().log(Level.INFO,
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

            for (String f : materialFiles) {
                initMaterialFile(materialDir, f);
            }

            ChestInit.getPlugin().getLogger().log(Level.INFO, "[" + plugin.getName() + "] "
                    + "Loaded " + materialCount + " items from " + materialFiles.length + " files.");

        }

    }

    private void initMaterialFile(File materialDir, String f) {
        if (!new File(materialDir, f).exists())
            getPlugin().saveResource("material" + File.separator + f, false);

        File materialConfigFile = new File(materialDir + File.separator + f);

        if (!materialConfigFile.exists()) {
            return;
        }

        FileConfiguration materialConfig = YamlConfiguration.loadConfiguration(materialConfigFile);

        for (String m : new ArrayList<>(materialConfig.getKeys(false))) {

            try {

                Material material = Material.getMaterial(materialConfig.getString(m + ".m"));

                Set<Material> itemEffected = new HashSet<Material>() {{
                    add(Material.POTION);
                    add(Material.SPLASH_POTION);
                    add(Material.LINGERING_POTION);
                    add(Material.TIPPED_ARROW);
                }};

                MaterialData data = new MaterialData(materialConfig, m);


                if (itemEffected.contains(material)) {

                    for (PotionType potion : PotionType.values()) {

                        ItemStack newItemStack = new ItemStack(material, 1);

                        PotionMeta itemPotionMeta = (PotionMeta) newItemStack.getItemMeta();
                        itemPotionMeta.setBasePotionData(new PotionData(potion));
                        newItemStack.setItemMeta(itemPotionMeta);

                        addMaterial(newItemStack, data, material, f);

                    }

                } else {
                    addMaterial(new ItemStack(material, 1), data, material, f);
                }

            } catch (Exception e) {

                ChestInit.getPlugin().getLogger().log(Level.WARNING, "[" + plugin.getName() + "] "
                        + "Material Load Error: " + m + " (" + f + ")");

            }

        }
    }

    private void addMaterial(ItemStack materialItem, MaterialData data, Material materialType, String materialFile) {

        if (materialMap.containsKey(materialItem)) {
            ChestInit.getPlugin().getLogger().log(Level.WARNING, "[" + plugin.getName() + "] "
                    + "Material Duplicate: " + materialType + " (" + materialFile + ")");
        } else {
            materialMap.put(materialItem, data);
            materialCount++;
        }

    }

}