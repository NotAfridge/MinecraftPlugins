package com.ullarah.ulib.function;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

import java.security.CodeSource;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class PluginRegisters {

    /**
     * Registers different types for minecraft reference
     *
     * @param plugin  the current plugin used
     * @param type    the type of object that is being registered
     * @param objects the objects to register against the plugin
     * @return the number of valid registrations
     */
    public int register(Plugin plugin, RegisterType type, Object... objects) {

        int amount = 0;

        for (Object object : objects) {

            try {

                switch (type) {

                    case EVENT:
                        plugin.getServer().getPluginManager().registerEvents((Listener) object, plugin);
                        break;

                    case FURNACE:
                        FurnaceRecipe newFurnace = ((NewFurnace) object).furnace();
                        plugin.getServer().addRecipe(newFurnace);
                        break;

                    case RECIPE:
                        ShapedRecipe newRecipe = ((NewRecipe) object).recipe();
                        plugin.getServer().addRecipe(newRecipe);
                        break;

                    case TASK:
                        object.getClass().getMethod(type.toString()).invoke(object.getClass().newInstance());
                        break;

                }

                amount++;

            } catch (Exception e) {

                Bukkit.getLogger().log(Level.SEVERE, "[" + plugin.getName() + "] Register Error: "
                        + "[" + type.toString().toUpperCase() + "] " + object.getClass().getCanonicalName());

                e.printStackTrace();

            }

        }

        return amount;

    }

    /**
     * Registers all different types for minecraft reference
     * This will search the plugin source for valid entry points
     * <p>
     * Requires proper package names
     *
     * @param plugin the current plugin used
     * @param type   the type of object that is being registered
     * @return the number of valid registrations
     */
    public int registerAll(Plugin plugin, RegisterType type) {

        int amount = 0;

        try {

            CodeSource codeSource = plugin.getClass().getProtectionDomain().getCodeSource();

            if (codeSource != null) {

                ZipInputStream stream = new ZipInputStream(codeSource.getLocation().openStream());

                while (true) {

                    String pluginPackage = plugin.getClass().getPackage().getName().toLowerCase();
                    String classPackage = pluginPackage + "." + type.toString().toLowerCase();

                    ZipEntry entry = stream.getNextEntry();

                    if (entry == null) break;

                    String className = entry.getName();
                    String classPath = classPackage.replaceAll("\\.", "/") + "/";

                    if (className.startsWith(classPath) && className.endsWith(".class")) {

                        if (className.contains("$")) continue;

                        className = className.replace(classPath, "").replace(".class", "");
                        Object classInstance = Class.forName(classPackage + "." + className).newInstance();

                        switch (type) {

                            case EVENT:
                                plugin.getServer().getPluginManager().registerEvents((Listener) classInstance, plugin);
                                break;

                            case FURNACE:
                                FurnaceRecipe newFurnace = ((NewFurnace) classInstance).furnace();
                                plugin.getServer().addRecipe(newFurnace);
                                break;

                            case RECIPE:
                                ShapedRecipe newRecipe = ((NewRecipe) classInstance).recipe();
                                plugin.getServer().addRecipe(newRecipe);
                                break;

                            case TASK:
                                classInstance.getClass().getMethod(type.toString()).invoke(classInstance);
                                break;

                        }

                        amount++;

                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return amount;

    }

    public enum RegisterType {

        COMMAND("command"), EVENT("event"), FURNACE("furnace"), RECIPE("recipe"), TASK("task");

        private final String type;

        RegisterType(String getType) {
            type = getType;
        }

        public String toString() {
            return type;
        }

    }

}
