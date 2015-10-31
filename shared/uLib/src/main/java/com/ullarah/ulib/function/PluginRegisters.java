package com.ullarah.ulib.function;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class PluginRegisters {

    /**
     * Registers different types for minecraft reference
     *
     * @param plugin  the current plugin used
     * @param type    the type of object that is being registered
     * @param objects the objects to register against the plugin
     * @return the number of valid registrations
     */
    public static int register(Plugin plugin, RegisterType type, Object... objects) {

        int amount = 0;

        for (Object object : objects) {

            try {

                Class<?> classObject = object.getClass();

                switch (type) {

                    case EVENT:
                        Bukkit.getServer().getPluginManager().registerEvents((Listener) object, plugin);
                        break;

                    case FURNACE:
                        FurnaceRecipe newFurnace = ((NewFurnace) object).furnace();
                        Bukkit.getServer().addRecipe(newFurnace);
                        break;

                    case RECIPE:
                        ShapedRecipe newRecipe = ((NewRecipe) object).recipe();
                        Bukkit.getServer().addRecipe(newRecipe);
                        break;

                    case TASK:
                        classObject.getMethod(type.toString()).invoke(classObject.newInstance());
                        break;

                }

                amount++;

            } catch (Exception e) {

                Bukkit.getLogger().log(Level.SEVERE, "[" + plugin.getName() + "] Register Error: "
                        + "[" + type.toString().toUpperCase() + "] " + object.getClass().getSimpleName());

            }

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
