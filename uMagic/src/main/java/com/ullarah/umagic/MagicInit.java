package com.ullarah.umagic;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.ullarah.umagic.database.SQLConnection;
import com.ullarah.umagic.function.PluginRegisters;
import com.ullarah.umagic.recipe.MagicHoeNormal;
import com.ullarah.umagic.recipe.MagicHoeSuper;
import com.ullarah.umagic.recipe.MagicHoeUber;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class MagicInit extends JavaPlugin {

    private static Plugin plugin;
    private static WorldEditPlugin worldEdit;
    private static WorldGuardPlugin worldGuard;
    private static SQLConnection sqlConnection;

    public static Plugin getPlugin() {
        return plugin;
    }

    private void setPlugin(Plugin plugin) {
        MagicInit.plugin = plugin;
    }

    static WorldEditPlugin getWorldEdit() {
        return worldEdit;
    }

    private void setWorldEdit(WorldEditPlugin worldEdit) {
        MagicInit.worldEdit = worldEdit;
    }

    static WorldGuardPlugin getWorldGuard() {
        return worldGuard;
    }

    private void setWorldGuard(WorldGuardPlugin worldGuard) {
        MagicInit.worldGuard = worldGuard;
    }

    static SQLConnection getSqlConnection() {
        return sqlConnection;
    }

    private void setSqlConnection(SQLConnection sqlConnection) {
        MagicInit.sqlConnection = sqlConnection;
    }

    static String getDatabaseName() {
        return "metadata";
    }

    public void onEnable() {

        setPlugin(this);

        PluginManager pluginManager = getServer().getPluginManager();
        Plugin pluginWorldGuard = pluginManager.getPlugin("WorldGuard");

        if (pluginWorldGuard != null) {

            setWorldGuard((WorldGuardPlugin) pluginWorldGuard);
            setWorldEdit((WorldEditPlugin) pluginManager.getPlugin("WorldEdit"));

            String create = "CREATE TABLE IF NOT EXISTS " + getDatabaseName() + "(" +
                    "`_id` INTEGER PRIMARY KEY,`data` TEXT NOT NULL,`world` TEXT NOT NULL," +
                    "`locX` INTEGER NOT NULL,`locY` INTEGER NOT NULL,`locZ` INTEGER NOT NULL);";

            setSqlConnection(new SQLConnection(getPlugin(), getDatabaseName(), create));

            new MagicFunctions(true);

            getServer().addRecipe(new MagicHoeNormal().recipe());
            getServer().addRecipe(new MagicHoeSuper().recipe());
            getServer().addRecipe(new MagicHoeUber().recipe());

            new PluginRegisters(getPlugin());

            getCommand("hoe").setExecutor(new MagicExecutor());

        } else {

            getPlugin().getLogger().log(Level.SEVERE, "WorldGuard plugin not found. Disabling uMagic.");
            pluginManager.disablePlugin(this);

        }

    }

    public void onDisable() {

        getSqlConnection().closeSQLConnection();

    }

}
