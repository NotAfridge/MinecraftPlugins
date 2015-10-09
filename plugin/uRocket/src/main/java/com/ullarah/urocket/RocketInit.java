package com.ullarah.urocket;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.ullarah.ulib.function.PluginRegisters;
import com.ullarah.urocket.event.*;
import com.ullarah.urocket.recipe.*;
import com.ullarah.urocket.recipe.variant.*;
import com.ullarah.urocket.task.*;
import net.milkbowl.vault.economy.Economy;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import static com.ullarah.ulib.function.PluginRegisters.RegisterType.*;
import static com.ullarah.urocket.RocketFunctions.*;

public class RocketInit extends JavaPlugin {

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
    public static final ConcurrentHashMap<UUID, Location> rocketRepair = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<UUID, Location> rocketRepairStand = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<UUID, HashMap<Location, Location>> rocketZoneLocations = new ConcurrentHashMap<>();

    public static final HashMap<String, Integer> registerMap = new HashMap<>();

    private static String msgPrefix = null;
    private static Plugin plugin;
    private static WorldGuardPlugin worldGuard;
    private static Economy vaultEconomy;

    public static String getMsgPrefix() {
        return msgPrefix;
    }

    private static void setMsgPrefix(String msgPrefix) {
        RocketInit.msgPrefix = msgPrefix;
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    private static void setPlugin(Plugin plugin) {
        RocketInit.plugin = plugin;
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

        setPlugin(this);

        PluginManager pluginManager = getServer().getPluginManager();

        Plugin pluginWorldGuard = pluginManager.getPlugin("WorldGuard");
        Plugin pluginVault = pluginManager.getPlugin("Vault");

        registerMap.put(EVENT.toString(), PluginRegisters.register(getPlugin(), EVENT,
                new AnvilCreate(),
                new AnvilRename(),
                new BlockBreak(),
                new BlockPlace(),
                new InventoryClick(),
                new PlayerMove(),
                new CraftStandard(),
                new QuitJoinDeath(),
                new ToggleFlight(),
                new ToggleSprint(),
                new PlayerChat(),
                new PlayerCommand(),
                new PlayerInteract(),
                new PlayerConsume(),
                new PlayerWorldChange(),
                new StandChange(),
                new StandBreak(),
                new ZoneCheck(),
                new ZoneDamage(),
                new EntityFlying(),
                new PlayerInteractEntity(),
                new EntityDeath()
        ));

        registerMap.put(RECIPE.toString(), PluginRegisters.register(getPlugin(), RECIPE,
                new RocketBooster("I", Material.REDSTONE_BLOCK),
                new RocketBooster("II", Material.IRON_BLOCK),
                new RocketBooster("III", Material.GOLD_BLOCK),
                new RocketBooster("IV", Material.DIAMOND_BLOCK),
                new RocketBooster("V", Material.EMERALD_BLOCK),
                new RocketBoots(),
                new RocketControls(),
                new RepairStation(),
                new RepairTank(),
                new RepairStand(),
                new RocketFlyZone(),
                new RocketVariants(),
                new RocketSaddle()
        ));

        registerMap.put("variant", PluginRegisters.register(getPlugin(), RECIPE,
                new VariantEnder(),
                new VariantHealth(),
                new VariantKaboom(),
                new VariantRainbow(),
                new VariantWater(),
                new VariantZero(),
                new VariantNote(),
                new VariantStealth(),
                new VariantAgenda(),
                new VariantGlazed(),
                new VariantBoost(),
                new VariantCoal(),
                new VariantRedstone(),
                new VariantRunner(),
                new VariantGlow()
        ));

        registerMap.put(TASK.toString(), PluginRegisters.register(getPlugin(), TASK,
                new RocketFire(),
                new RocketFuel(),
                new RocketParticles(),
                new RocketLowFuel(),
                new NotePlaying(),
                new StationParticles(),
                new StationRepair(),
                new ActiveEffects(),
                new StationStandParticles(),
                new StationStandRepair()
        ));

        getCommand("rocket").setExecutor(new RocketExecutor());

        getConfig().options().copyDefaults(true);
        saveConfig();

        setMsgPrefix(ChatColor.GOLD + "[uRocket] " + ChatColor.RESET);

        Set<String> pluginList = new HashSet<>();

        if (pluginWorldGuard != null) {
            setWorldGuard((WorldGuardPlugin) pluginWorldGuard);
            pluginList.add("WorldGuard");
        }

        if (pluginVault != null) {
            RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(
                    net.milkbowl.vault.economy.Economy.class);
            if (economyProvider != null) {
                setVaultEconomy(economyProvider.getProvider());
                Integer currentRecipeCount = registerMap.get("recipe");
                PluginRegisters.register(getPlugin(), RECIPE, new VariantMoney());
                registerMap.put(RECIPE.toString(), currentRecipeCount + 1);
                pluginList.add("Vault");
            }
        }

        reloadFlyZones(true);
        
        int zoneList = 0;
        if (registerMap.get("zone") != null) zoneList = registerMap.get("zone");

        Bukkit.getLogger().log(Level.INFO, "[uRocket] "
                + "Events: " + registerMap.get("event") + " | "
                + "Recipes: " + registerMap.get("recipe") + " | "
                + "Variants: " + registerMap.get("variant") + " | "
                + "Tasks: " + registerMap.get("task") + " | "
                + "Zones: " + zoneList);

        if (pluginList.size() > 0)
            Bukkit.getLogger().log(Level.INFO, "[uRocket] Hooked: " + StringUtils.join(pluginList, ", "));

    }

    public void onDisable() {

        for (UUID uuid : rocketUsage)
            disableRocketBoots(Bukkit.getPlayer(uuid), false, false, false, false, false);

    }

}