package com.ullarah.upostal;

import com.ullarah.upostal.function.EventRegister;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class PostalInit extends JavaPlugin {

    public static final ArrayList<UUID> inboxViewerBusy = new ArrayList<>();
    public static final ArrayList<UUID> inboxOwnerBusy = new ArrayList<>();
    public static final ArrayList<UUID> inboxModification = new ArrayList<>();
    public static final HashMap<UUID, BukkitTask> inboxChanged = new HashMap<>();

    private static Plugin plugin;
    private static String inboxDataPath;

    public static Plugin getPlugin() {
        return plugin;
    }

    private static void setPlugin(Plugin plugin) {
        PostalInit.plugin = plugin;
    }

    public static String getInboxDataPath() {
        return inboxDataPath;
    }

    private static void setInboxDataPath(String dataPath) {
        PostalInit.inboxDataPath = dataPath;
    }

    public void onEnable() {

        setPlugin(this);
        setInboxDataPath(getPlugin().getDataFolder() + File.separator + "inbox");

        getConfig().options().copyDefaults(true);
        saveConfig();

        new EventRegister().registerAll(getPlugin());

        getCommand("postal").setExecutor(new PostalExecutor());
        getCommand("post").setExecutor(new PostalExecutor());
        getCommand("inbox").setExecutor(new PostalExecutor());

    }

    public void onDisable() {

        getPlugin().getLogger().log(Level.INFO, "Cancelling Active Postal Reminders...");
        if (inboxChanged.size() > 0)
            for (Map.Entry<UUID, BukkitTask> entry : inboxChanged.entrySet()) entry.getValue().cancel();

    }

}