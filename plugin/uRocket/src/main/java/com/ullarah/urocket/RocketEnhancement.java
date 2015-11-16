package com.ullarah.urocket;

import com.ullarah.ulib.function.PluginRegisters;
import com.ullarah.urocket.recipe.RocketEnhance;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Collections;
import java.util.List;

import static com.ullarah.ulib.function.PluginRegisters.RegisterType.RECIPE;
import static com.ullarah.urocket.RocketInit.getPlugin;
import static com.ullarah.urocket.RocketInit.registerMap;
import static org.bukkit.Material.*;

public class RocketEnhancement {

    public void init() {

        registerMap.put("enhancement", 0);
        for (Enhancement enhancement : Enhancement.values())
            registerMap.put("enhancement", registerMap.get("enhancement") +
                    new PluginRegisters().register(getPlugin(), RECIPE,
                            new RocketEnhance(enhancement.getName(), enhancement.getMaterial(), enhancement.getLore())));

    }

    public enum Enhancement {

        NOTHING(
                ChatColor.DARK_GRAY + "Nothing", BEDROCK,
                Collections.singletonList(ChatColor.DARK_GRAY + "Literally Nothing!")
        ),
        REPAIR(
                ChatColor.RED + "Self Repair", ANVIL,
                Collections.singletonList(ChatColor.YELLOW + "Repair your Rocket Boots as you fly!")
        ),
        FUEL(
                ChatColor.YELLOW + "Fuel Efficient", SPONGE,
                Collections.singletonList(ChatColor.YELLOW + "Stretch your rocket fuel further!")
        ),
        SOLAR(
                ChatColor.WHITE + "Solar Power", DAYLIGHT_DETECTOR,
                Collections.singletonList(ChatColor.YELLOW + "Fly until the sun goes down!")
        );

        private final String name;
        private final Material material;
        private final List<String> lore;

        Enhancement(String name, Material material, List<String> lore) {
            this.name = name;
            this.material = material;
            this.lore = lore;
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

        public List<String> getLore() {
            return lore;
        }

    }

}