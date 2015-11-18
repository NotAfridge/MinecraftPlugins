package com.ullarah.urocket.init;

import com.ullarah.urocket.function.PluginRegisters;
import com.ullarah.urocket.recipe.RocketEnhance;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import static com.ullarah.urocket.RocketInit.getPlugin;
import static com.ullarah.urocket.RocketInit.registerMap;
import static com.ullarah.urocket.function.PluginRegisters.RegisterType.RECIPE;
import static org.bukkit.Material.*;

public class RocketEnhancement {

    public void init() {

        registerMap.put("enhancement", 0);
        for (Enhancement enhancement : Enhancement.values())
            registerMap.put("enhancement", registerMap.get("enhancement") +
                    new PluginRegisters().register(getPlugin(), RECIPE,
                            new RocketEnhance(enhancement.getName(), enhancement.getMaterial())));

    }

    public enum Enhancement {

        NOTHING(ChatColor.DARK_GRAY + "Nothing", BEDROCK),
        REPAIR(ChatColor.RED + "Self Repair", ANVIL),
        FUEL(ChatColor.YELLOW + "Fuel Efficient", SPONGE),
        SOLAR(ChatColor.WHITE + "Solar Power", DAYLIGHT_DETECTOR),
        JUNK(ChatColor.GREEN + "Garbage Disposal", DROPPER);

        private final String name;
        private final Material material;

        Enhancement(String name, Material material) {
            this.name = name;
            this.material = material;
        }

        public static Enhancement getEnum(String name) {
            for (Enhancement e : values()) if (name.equals(ChatColor.stripColor(e.getName()))) return e;
            return null;
        }

        public static boolean isEnhancement(String name) {
            for (Enhancement e : values()) if (name.equals(ChatColor.stripColor(e.getName()))) return true;
            return false;
        }

        public String getName() {
            return name;
        }

        public Material getMaterial() {
            return material;
        }

    }

}