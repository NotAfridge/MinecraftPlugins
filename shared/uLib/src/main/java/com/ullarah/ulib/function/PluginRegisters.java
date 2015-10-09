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

                    case FURNACE:
                        FurnaceRecipe furnaceRecipe = (FurnaceRecipe) classObject.getMethod(type.toString())
                                .invoke(classObject.newInstance());
                        Bukkit.getServer().addRecipe(furnaceRecipe);
                        break;

                    case RECIPE:
                        if (object instanceof NewRecipe) {
                            ShapedRecipe testRecipe = ((NewRecipe) object).recipe();
                            Bukkit.getServer().addRecipe(testRecipe);
                        }
                        break;

                    case TASK:
                        classObject.getMethod(type.toString()).invoke(classObject.newInstance());
                        break;

                }

                amount++;

            } catch (Exception e) {

                e.printStackTrace();

                Bukkit.getLogger().log(Level.SEVERE, "[" + plugin.getName() + "] Register Error: "
                        + object.getClass().getSimpleName());

            }

        }

        return amount;

    }

    public enum RegisterType {

        COMMAND("command"), EVENT("event"), FURNACE("furnace"), RECIPE("recipe"), TASK("task");

        private final String type;

        private RegisterType(String getType) {
            type = getType;
        }

        public String toString() {
            return type;
        }

    }

}
