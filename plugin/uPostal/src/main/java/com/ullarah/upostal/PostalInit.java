package com.ullarah.upostal;

import com.ullarah.ulib.function.PluginRegisters;
import com.ullarah.upostal.event.InboxClick;
import com.ullarah.upostal.event.InboxClose;
import com.ullarah.upostal.event.InboxDrag;
import com.ullarah.upostal.event.JoinRegister;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import static com.ullarah.ulib.function.PluginRegisters.RegisterType.EVENT;

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

    public static String getMsgPrefix() {
        return msgPrefix;
    }

    private static void setMsgPrefix(String msgPrefix) {
        PostalInit.msgPrefix = msgPrefix;
    }

    public static String getMsgPermDeny() {
        return msgPermDeny;
    }

    private static void setMsgPermDeny(String msgPermDeny) {
        PostalInit.msgPermDeny = msgPermDeny;
    }

    public static String getMsgNoConsole() {
        return msgNoConsole;
    }

    private static void setMsgNoConsole(String msgNoConsole) {
        PostalInit.msgNoConsole = msgNoConsole;
    }

    public static Boolean getMaintenanceCheck() {
        return maintenanceCheck;
    }

    public static void setMaintenanceCheck(Boolean maintenanceCheck) {
        PostalInit.maintenanceCheck = maintenanceCheck;
    }

    public static String getMaintenanceMessage() {
        return maintenanceMessage;
    }

    private static void setMaintenanceMessage(String maintenanceMessage) {
        PostalInit.maintenanceMessage = maintenanceMessage;
    }

    public void onEnable() {

        setPlugin(this);
        setInboxDataPath(getPlugin().getDataFolder() + File.separator + "inbox");

        setMsgPrefix(ChatColor.GOLD + "[uPostal] " + ChatColor.WHITE);

        getConfig().options().copyDefaults(true);
        saveConfig();

        PluginRegisters.register(getPlugin(), EVENT,
                new InboxClick(),
                new InboxClose(),
                new InboxDrag(),
                new JoinRegister()
        );

        getCommand("postal").setExecutor(new PostalExecutor());
        getCommand("post").setExecutor(new PostalExecutor());
        getCommand("inbox").setExecutor(new PostalExecutor());

        setMsgPermDeny(getMsgPrefix() + ChatColor.RED + "No permission.");
        setMsgNoConsole(getMsgPrefix() + ChatColor.RED + "No console usage.");

        setMaintenanceCheck(PostalInit.getPlugin().getConfig().getBoolean("maintenance"));
        setMaintenanceMessage(getMsgPrefix() + ChatColor.RED + "uPostal is currently unavailable.");

    }

    public void onDisable() {

        getPlugin().getLogger().log(Level.INFO, "Cancelling Active Postal Reminders...");
        if (inboxChanged.size() > 0)
            for (Map.Entry<UUID, BukkitTask> entry : inboxChanged.entrySet()) entry.getValue().cancel();

    }

}