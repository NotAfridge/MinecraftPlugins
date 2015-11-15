package com.ullarah.urocket;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.ullarah.urocket.recipe.*;
import net.milkbowl.vault.economy.Economy;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import static com.ullarah.ulib.function.PluginRegisters.RegisterType.*;
import static com.ullarah.ulib.function.PluginRegisters.register;
import static com.ullarah.ulib.function.PluginRegisters.registerAll;
import static com.ullarah.urocket.RocketEnhancement.Enhancement;
import static com.ullarah.urocket.RocketFunctions.disableRocketBoots;
import static com.ullarah.urocket.RocketFunctions.reloadFlyZones;
import static com.ullarah.urocket.RocketVariant.Variant;

public class RocketInit extends JavaPlugin {

    public static final String pluginName = "uRocket";

    public static final HashSet<UUID> rocketUsage = new HashSet<>();
    public static final HashSet<UUID> rocketWater = new HashSet<>();
    public static final HashSet<UUID> rocketZones = new HashSet<>();
    public static final HashSet<UUID> rocketLowFuel = new HashSet<>();
    public static final HashSet<UUID> rocketEffects = new HashSet<>();
    public static final HashSet<Location> rocketFire = new HashSet<>();
    public static final ConcurrentHashMap<Location, Material> rocketGlow = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<UUID, EntityType> rocketEntity = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<UUID, String> rocketSprint = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<UUID, Integer> rocketPower = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<UUID, Variant> rocketVariant = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<UUID, Enhancement> rocketEnhancement = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<UUID, Location> rocketRepair = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<UUID, Location> rocketRepairStand = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<UUID, ConcurrentHashMap<Location, Location>> rocketZoneLocations = new ConcurrentHashMap<>();

    public static final HashMap<String, Integer> registerMap = new HashMap<>();

    private static Plugin plugin;
    private static PluginManager pluginManager;
    private static WorldGuardPlugin worldGuard;
    private static Economy vaultEconomy;

    public static Plugin getPlugin() {
        return plugin;
    }

    private static void setPlugin(Plugin plugin) {
        RocketInit.plugin = plugin;
    }

    public static PluginManager getPluginManager() {
        return pluginManager;
    }

    private static void setPluginManager(PluginManager pluginManager) {
        RocketInit.pluginManager = pluginManager;
    }

    public static WorldGuardPlugin getWorldGuard() {
        return worldGuard;
    }

    private static void setWorldGuard(WorldGuardPlugin worldGuard) {
        RocketInit.worldGuard = worldGuard;
    }

    public static Economy getVaultEconomy() {
        return vaultEconomy;
    }

    private static void setVaultEconomy(Economy vaultEconomy) {
        RocketInit.vaultEconomy = vaultEconomy;
    }

    public void onEnable() {

        setPluginManager(Bukkit.getPluginManager());
        setPlugin(getPluginManager().getPlugin(pluginName));

        Plugin pluginWorldGuard = getPluginManager().getPlugin("WorldGuard");
        Plugin pluginVault = getPluginManager().getPlugin("Vault");

        registerMap.put(RECIPE.toString(), register(getPlugin(), RECIPE,
                new RocketBooster("I", Material.REDSTONE_BLOCK),
                new RocketBooster("II", Material.IRON_BLOCK),
                new RocketBooster("III", Material.GOLD_BLOCK),
                new RocketBooster("IV", Material.DIAMOND_BLOCK),
                new RocketBooster("V", Material.EMERALD_BLOCK),
                new RocketControls(),
                new RepairStation(),
                new RepairTank(),
                new RepairStand(),
                new RocketFlyZone(),
                new RocketSaddle()
        ));

        for (Material material : new ArrayList<Material>() {{
            add(Material.LEATHER);
            add(Material.IRON_INGOT);
            add(Material.GOLD_INGOT);
            add(Material.DIAMOND);
        }}) {

            for (Boolean bool : new ArrayList<Boolean>() {{
                add(true);
                add(false);
            }}) {

                registerMap.put(RECIPE.toString(), registerMap.get(RECIPE.toString()) +
                        register(getPlugin(), RECIPE,
                                new RocketBoots(material, bool, bool),
                                new RocketBoots(material, bool, !bool)
                        ));

            }

        }

        registerMap.put(EVENT.toString(), registerAll(getPlugin(), EVENT));
        registerMap.put(TASK.toString(), registerAll(getPlugin(), TASK));

        new RocketVariant().init();
        new RocketEnhancement().init();

        getCommand("rocket").setExecutor(new RocketExecutor());

        getConfig().options().copyDefaults(true);
        saveConfig();

        Set<String> pluginList = new HashSet<>();

        if (pluginWorldGuard != null) {
            setWorldGuard((WorldGuardPlugin) pluginWorldGuard);
            pluginList.add("WorldGuard");
        }

        if (pluginVault != null) {
            RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
            if (economyProvider != null) {
                setVaultEconomy(economyProvider.getProvider());
                pluginList.add("Vault");
            }
        }

        reloadFlyZones(true);
        int zoneList = registerMap.get("zone") != null ? registerMap.get("zone") : 0;

        Bukkit.getLogger().log(Level.INFO, "[" + pluginName + "] "
                + "Tasks: " + registerMap.get("task") + " | "
                + "Events: " + registerMap.get("event") + " | "
                + "Recipes: " + registerMap.get("recipe") + " | "
                + "Variants: " + registerMap.get("variant") + " | "
                + "Enhancements: " + registerMap.get("enhancement") + " | "
                + "Zones: " + zoneList);

        if (pluginList.size() > 0)
            Bukkit.getLogger().log(Level.INFO,
                    "[" + pluginName + "] Hooked: " + StringUtils.join(pluginList, ", "));

    }

    public void onDisable() {

        for (UUID uuid : rocketUsage)
            disableRocketBoots(Bukkit.getPlayer(uuid), false, false, false, false, false, false);

    }

}