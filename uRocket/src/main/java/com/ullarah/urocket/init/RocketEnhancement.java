package com.ullarah.urocket.init;

import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.function.PluginRegisters;
import com.ullarah.urocket.recipe.RocketEnhance;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class RocketEnhancement {

    public void init() {

        RocketInit.registerMap.put("enhancement", 0);
        for (Enhancement enhancement : Enhancement.values())
            RocketInit.registerMap.put("enhancement", RocketInit.registerMap.get("enhancement") +
                    new PluginRegisters().register(RocketInit.getPlugin(), PluginRegisters.RegisterType.RECIPE,
                            new RocketEnhance(enhancement.getName(), enhancement.getMaterial())));

    }

    public enum Enhancement {

        NOTHING(ChatColor.DARK_GRAY + "Nothing", Material.BEDROCK),
        REPAIR(ChatColor.RED + "Self Repair", Material.ANVIL),
        FASTREP(ChatColor.GOLD + "Fast Repair", Material.FURNACE),
        FUEL(ChatColor.YELLOW + "Fuel Efficient", Material.SPONGE),
        SOLAR(ChatColor.WHITE + "Solar Power", Material.DAYLIGHT_DETECTOR),
        STABLE(ChatColor.BLUE + "Stabiliser", Material.SLIME_BLOCK),
        UNLIMITED(ChatColor.LIGHT_PURPLE + "Unlimited Flight", Material.BEDROCK);

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