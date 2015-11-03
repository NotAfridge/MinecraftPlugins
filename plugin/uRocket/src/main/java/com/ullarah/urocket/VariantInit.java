package com.ullarah.urocket;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.bukkit.ChatColor.*;
import static org.bukkit.Material.*;

public class VariantInit {

    public static Map returnVariantMap() {

        return new HashMap<String, ArrayList<Material>>() {{

            put(LIGHT_PURPLE + "Gay Agenda", new ArrayList<Material>() {{
                add(GOLDEN_APPLE);
                add(MAGMA_CREAM);
                add(SPECKLED_MELON);
            }});

            put(AQUA + "Pole Vaulter", new ArrayList<Material>() {{
                add(RABBIT_FOOT);
                add(SLIME_BLOCK);
                add(FIREWORK_CHARGE);
            }});

            put(GRAY + "Coal Miner", new ArrayList<Material>() {{
                add(NETHERRACK);
                add(COAL_BLOCK);
                add(FURNACE);
            }});

            put(DARK_AQUA + "Essence of Ender", new ArrayList<Material>() {{
                add(EYE_OF_ENDER);
                add(IRON_BLOCK);
                add(ENDER_STONE);
            }});

            put(GRAY + "Glazed Over", new ArrayList<Material>() {{
                add(WEB);
                add(CACTUS);
                add(FISHING_ROD);
            }});

            put(YELLOW + "Shooting Star", new ArrayList<Material>() {{
                add(TORCH);
                add(GLOWSTONE);
                add(MAGMA_CREAM);
            }});

            put(GREEN + "Health Zapper", new ArrayList<Material>() {{
                add(PUMPKIN_PIE);
                add(REDSTONE_TORCH_ON);
                add(TNT);
            }});

            put(RED + "TNT Overload", new ArrayList<Material>() {{
                add(BLAZE_POWDER);
                add(BLAZE_ROD);
                add(TNT);
            }});

            put(GOLD + "Musical Madness", new ArrayList<Material>() {{
                add(GREEN_RECORD);
                add(JUKEBOX);
                add(NOTE_BLOCK);
            }});

            put(YELLOW + "Radical Rainbows", new ArrayList<Material>() {{
                add(BEACON);
                add(REDSTONE_COMPARATOR);
                add(STAINED_GLASS);
            }});

            put(DARK_RED + "Red Fury", new ArrayList<Material>() {{
                add(REDSTONE);
                add(REDSTONE_COMPARATOR);
                add(BLAZE_POWDER);
            }});

            put(GOLD + "Rocket Runner", new ArrayList<Material>() {{
                add(SUGAR);
                add(SUGAR);
                add(PRISMARINE_CRYSTALS);
            }});

            put(WHITE + "Super Stealth", new ArrayList<Material>() {{
                add(SLIME_BALL);
                add(PACKED_ICE);
                add(SOUL_SAND);
            }});

            put(BLUE + "Water Slider", new ArrayList<Material>() {{
                add(WATER_LILY);
                add(PRISMARINE_CRYSTALS);
                add(WATER_BUCKET);
            }});

            put(YELLOW + "Patient Zero", new ArrayList<Material>() {{
                add(BLAZE_POWDER);
                add(EXP_BOTTLE);
                add(ENCHANTMENT_TABLE);
            }});

            put(WHITE + "Loud Silence", new ArrayList<Material>() {{
                add(GOLD_RECORD);
                add(JUKEBOX);
                add(NOTE_BLOCK);
            }});

        }};

    }

}
