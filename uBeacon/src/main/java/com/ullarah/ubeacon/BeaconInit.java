package com.ullarah.ubeacon;

import com.ullarah.ubeacon.event.BeaconDestroy;
import com.ullarah.ubeacon.event.BeaconPlace;
import com.ullarah.ubeacon.function.PluginRegisters;
import com.ullarah.ubeacon.recipe.BeaconRainbow;
import com.ullarah.ubeacon.task.BeaconChange;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;

public class BeaconInit extends JavaPlugin {

    private static final ArrayList<Material> materials = new ArrayList<>();

    private static String msgPrefix = null;
    private static Plugin plugin;

    public static String getMsgPrefix() {
        return msgPrefix;
    }

    private static void setMsgPrefix(String msgPrefix) {
        BeaconInit.msgPrefix = msgPrefix;
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    private static void setPlugin(Plugin plugin) {
        BeaconInit.plugin = plugin;
    }

    public void onEnable() {

        setPlugin(this);

        Collections.addAll(materials,
                Material.LIGHT_BLUE_STAINED_GLASS,
                Material.LIME_STAINED_GLASS,
                Material.MAGENTA_STAINED_GLASS,
                Material.ORANGE_STAINED_GLASS,
                Material.WHITE_STAINED_GLASS,
                Material.YELLOW_STAINED_GLASS);

        new PluginRegisters().register(getPlugin(), PluginRegisters.RegisterType.TASK,
                new BeaconChange()
        );

        new PluginRegisters().register(getPlugin(), PluginRegisters.RegisterType.EVENT,
                new BeaconDestroy(),
                new BeaconPlace()
        );

        new PluginRegisters().register(getPlugin(), PluginRegisters.RegisterType.RECIPE,
                new BeaconRainbow()
        );

        setMsgPrefix(ChatColor.GOLD + "[uBeacon] " + ChatColor.RESET);

        getConfig().options().copyDefaults(true);
        saveConfig();

    }

    public void onDisable() {
    }

    public static ArrayList<Material> getMaterials() {
        return materials;
    }
}
