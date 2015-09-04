package com.ullarah.ulib.function;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class PluginRegisters {

    public static int register(Plugin plugin, RegisterType type, Object... objects) {

        int amount = 0;

        for (Object object : objects) {

            try {

                Class<?> classObject = object.getClass();

                switch (type) {

                    case EVENT:
                        Bukkit.getServer().getPluginManager().registerEvents((Listener) object, plugin);
                        break;

                    case RECIPE:
                        ShapedRecipe newRecipe = (ShapedRecipe) classObject.getMethod(type.toString())
                                .invoke(classObject.newInstance());
                        Bukkit.getServer().addRecipe(newRecipe);
                        break;

                    case TASK:
                        classObject.getMethod(type.toString()).invoke(classObject.newInstance());
                        break;

                    case FURNACE:
                        FurnaceRecipe furnaceRecipe = (FurnaceRecipe) classObject.getMethod(type.toString())
                                .invoke(classObject.newInstance());
                        Bukkit.getServer().addRecipe(furnaceRecipe);
                        break;

                }

                amount++;

            } catch (Exception e) {

                Bukkit.getLogger().log(Level.SEVERE, "[uLib] Register Error: " + object.getClass().getSimpleName());

            }

        }

        return amount;

    }

    public enum RegisterType {

        EVENT("event"), RECIPE("recipe"), TASK("task"), FURNACE("furnace");

        private final String type;

        private RegisterType(String getType) {
            type = getType;
        }

        public String toString() {
            return type;
        }

    }

}
