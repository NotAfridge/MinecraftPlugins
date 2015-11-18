package com.ullarah.upostal;

import com.ullarah.upostal.event.InboxClick;
import com.ullarah.upostal.event.InboxClose;
import com.ullarah.upostal.event.InboxDrag;
import com.ullarah.upostal.event.JoinRegister;
import com.ullarah.upostal.function.PluginRegisters;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import static com.ullarah.upostal.function.PluginRegisters.RegisterType.EVENT;

public class PostalInit extends JavaPlugin {

    public static final ArrayList<UUID> inboxViewerBusy = new ArrayList<>();
    public static final ArrayList<UUID> inboxOwnerBusy = new ArrayList<>();
    public static final HashMap<UUID, BukkitTask> inboxChanged = new HashMap<>();

    private static Plugin plugin;
    private static String msgPrefix = null;
    private static String msgPermDeny = null;
    private static String msgNoConsole = null;
    private static String inboxDataPath;
    private static Boolean maintenanceCheck;
    private static String maintenanceMessage;

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

    public static Boolean getMaintenanceCheck() {
        return maintenanceCheck;
    }

    public static void setMaintenanceCheck(Boolean maintenanceCheck) {
        PostalInit.maintenanceCheck = maintenanceCheck;
    }

    public void onEnable() {

        setPlugin(this);
        setInboxDataPath(getPlugin().getDataFolder() + File.separator + "inbox");

        getConfig().options().copyDefaults(true);
        saveConfig();

        new PluginRegisters().register(getPlugin(), EVENT,
                new InboxClick(),
                new InboxClose(),
                new InboxDrag(),
                new JoinRegister()
        );

        getCommand("postal").setExecutor(new PostalExecutor());
        getCommand("post").setExecutor(new PostalExecutor());
        getCommand("inbox").setExecutor(new PostalExecutor());

        setMaintenanceCheck(PostalInit.getPlugin().getConfig().getBoolean("maintenance"));

    }

    public void onDisable() {

        getPlugin().getLogger().log(Level.INFO, "Cancelling Active Postal Reminders...");
        if (inboxChanged.size() > 0)
            for (Map.Entry<UUID, BukkitTask> entry : inboxChanged.entrySet()) entry.getValue().cancel();

    }

}