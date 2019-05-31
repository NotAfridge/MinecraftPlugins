package com.ullarah.ubeacon.function;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
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
    public int register(Plugin plugin, RegisterType type, Object... objects) {

        int amount = 0;

        for (Object object : objects) {

            try {

                switch (type) {

                    case EVENT:
                        plugin.getServer().getPluginManager().registerEvents((Listener) object, plugin);
                        break;

                    case RECIPE:
                        ArrayList<ShapedRecipe> recipes = ((NewRecipe) object).recipes();
                        for (ShapedRecipe newRecipe : recipes) {
                            addRecipe(plugin, newRecipe);
                        }
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

    public enum RegisterType {

        EVENT("event"), RECIPE("recipe"), TASK("task");

        private final String type;

        RegisterType(String getType) {
            type = getType;
        }

        public String toString() {
            return type;
        }

    }

    private void addRecipe(Plugin plugin, Recipe recipe) {
        try {
            plugin.getServer().addRecipe(recipe);
        } catch (IllegalStateException e) {
            if (!e.getMessage().startsWith("Duplicate recipe ignored")) {
                e.printStackTrace();
            }
        }
    }

}
