package com.ullarah.umagic.function;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.security.CodeSource;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class PluginRegisters {

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

        EVENT("event"), TASK("task");

        private final String type;

        RegisterType(String getType) {
            type = getType;
        }

        public String toString() {
            return type;
        }

    }

}
