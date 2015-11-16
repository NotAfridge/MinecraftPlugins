package com.ullarah.urocket;

import com.ullarah.urocket.recipe.RocketVariants;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Random;

import static com.ullarah.ulib.function.PluginRegisters.RegisterType.RECIPE;
import static com.ullarah.ulib.function.PluginRegisters.register;
import static com.ullarah.urocket.RocketInit.getPlugin;
import static com.ullarah.urocket.RocketInit.registerMap;

public class RocketVariant {

    public void init() {

        registerMap.put("variant", 0);
        for (Variant variant : Variant.values())
            registerMap.put("variant", registerMap.get("variant") +
                    register(getPlugin(), RECIPE, new RocketVariants(variant.getName(), variant.getMaterials())));

    }

    public enum Variant {

        ORIGINAL(
                ChatColor.YELLOW + "Original", new Material[]{Material.FIRE, Material.FIRE, Material.FIRE},
                false, true, EnumParticle.FLAME, 0, 5, null,
                Sound.FIREWORK_LAUNCH, 0.8f, 0.35f, new Vector(0, 1.25, 0)
        ),
        ENDER(
                ChatColor.DARK_AQUA + "Essence of Ender", new Material[]{Material.EYE_OF_ENDER, Material.IRON_BLOCK, Material.ENDER_STONE},
                false, true, EnumParticle.PORTAL, 0, 25,
                new PotionEffect[]{
                        new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 2, false, false),
                        new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, 4, false, false)
                }, Sound.ENDERMAN_STARE, 0.8f, 1.3f, new Vector(0, 0.25, 0)
        ),
        HEALTH(
                ChatColor.GREEN + "Health Zapper", new Material[]{Material.PUMPKIN_PIE, Material.REDSTONE_TORCH_ON, Material.TNT},
                true, false, EnumParticle.CRIT_MAGIC, 0, 25, null,
                Sound.SLIME_WALK, 0.8f, 0.5f, new Vector(0, 1.25, 0)
        ),
        KABOOM(
                ChatColor.RED + "TNT Overload", new Material[]{Material.BLAZE_POWDER, Material.BLAZE_ROD, Material.TNT},
                false, true, EnumParticle.EXPLOSION_NORMAL, 0, 10, null,
                Sound.EXPLODE, 1.0f, 0.35f, new Vector(0, 0, 0)
        ),
        RAINBOW(
                ChatColor.YELLOW + "Radical Rainbows", new Material[]{Material.BEACON, Material.REDSTONE_COMPARATOR, Material.STAINED_GLASS},
                false, true, EnumParticle.REDSTONE, new Random().nextInt(25), new Random().nextInt(25), null,
                Sound.HORSE_GALLOP, 0.8f, 0.5f, new Vector(0, 1.25, 0)
        ),
        WATER(
                ChatColor.BLUE + "Water Slider", new Material[]{Material.WATER_LILY, Material.PRISMARINE_CRYSTALS, Material.WATER_BUCKET},
                false, true, EnumParticle.WATER_BUBBLE, 0, 25, null,
                Sound.WATER, 0.8f, 0.5f, new Vector(0, 1.25, 0)
        ),
        ZERO(
                ChatColor.YELLOW + "Patient Zero", new Material[]{Material.BLAZE_POWDER, Material.EXP_BOTTLE, Material.ENCHANTED_BOOK},
                false, true, EnumParticle.ENCHANTMENT_TABLE, 0, 25,
                new PotionEffect[]{
                        new PotionEffect(PotionEffectType.CONFUSION, Integer.MAX_VALUE, 0, false, false),
                        new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1, false, false),
                        new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1, false, false)
                }, Sound.AMBIENCE_THUNDER, 0.8f, 2.25f, new Vector(0, 1.25, 0)
        ),
        NOTE(
                ChatColor.GOLD + "Musical Madness", new Material[]{Material.GREEN_RECORD, Material.JUKEBOX, Material.NOTE_BLOCK},
                false, true, EnumParticle.NOTE, new Random().nextInt(25), 1, null,
                Sound.NOTE_SNARE_DRUM, 0.8f, 0.5f, new Vector(0, 1.25, 0)
        ),
        STEALTH(
                ChatColor.WHITE + "Super Stealth", new Material[]{Material.SLIME_BALL, Material.PACKED_ICE, Material.SOUL_SAND},
                false, true, EnumParticle.MOB_APPEARANCE, 0, 1, null,
                Sound.ENDERMAN_TELEPORT, 1.25f, 0.25f, new Vector(0, 0.5, 0)
        ),
        AGENDA(
                ChatColor.LIGHT_PURPLE + "Gay Agenda", new Material[]{Material.GOLDEN_APPLE, Material.MAGMA_CREAM, Material.SPECKLED_MELON},
                false, true, EnumParticle.SPELL_WITCH, new Random().nextInt(25), new Random().nextInt(25), null,
                Sound.NOTE_BASS, 1.0f, 0.5f, new Vector(0, 1.25, 0)
        ),
        MONEY(
                ChatColor.GREEN + "Robin Hood", new Material[]{Material.EMERALD, Material.DIAMOND, Material.GOLD_INGOT},
                true, false, EnumParticle.VILLAGER_HAPPY, 0, 5, null,
                Sound.LEVEL_UP, 1.0f, 0.75f, new Vector(0, 1.25, 0)
        ),
        DRUNK(
                ChatColor.GRAY + "Glazed Over", new Material[]{Material.WEB, Material.CACTUS, Material.FISHING_ROD},
                false, true, EnumParticle.SPELL_MOB, 0, 1,
                new PotionEffect[]{
                        new PotionEffect(PotionEffectType.CONFUSION, Integer.MAX_VALUE, 0, false, false),
                        new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 2, false, false)
                }, Sound.BURP, 1.25f, 0.95f, new Vector(0, 1.25, 0)
        ),
        BOOST(
                ChatColor.AQUA + "Pole Vaulter", new Material[]{Material.RABBIT_FOOT, Material.SLIME_BLOCK, Material.FIREWORK_CHARGE},
                false, true, EnumParticle.SNOWBALL, 0, 5,
                new PotionEffect[]{
                        new PotionEffect(PotionEffectType.HEAL, Integer.MAX_VALUE, 1, false, false)
                }, Sound.MAGMACUBE_JUMP, 1.25f, 0.75f, new Vector(0, 20, 0)
        ),
        TREE(
                ChatColor.GRAY + "Tree Hugger", new Material[]{Material.NETHERRACK, Material.LOG, Material.FURNACE},
                true, false, EnumParticle.TOWN_AURA, 0, 50, null,
                Sound.ZOMBIE_REMEDY, 1.25f, 0.95f, new Vector(0, 1, 0)
        ),
        FURY(
                ChatColor.DARK_RED + "Red Fury", new Material[]{Material.REDSTONE, Material.REDSTONE_COMPARATOR, Material.BLAZE_POWDER},
                true, false, EnumParticle.REDSTONE, 0, 10, null,
                Sound.PISTON_EXTEND, 1.25f, 0.55f, new Vector(0, 1, 0)
        ),
        RUNNER(
                ChatColor.GOLD + "Rocket Runner", new Material[]{Material.SUGAR, Material.SUGAR, Material.PRISMARINE_CRYSTALS},
                false, false, EnumParticle.EXPLOSION_NORMAL, 0, 30, null,
                Sound.HORSE_GALLOP, 1.25f, 0.75f, new Vector(0, 0, 0)
        ),
        GLOW(
                ChatColor.YELLOW + "Shooting Star", new Material[]{Material.TORCH, Material.GLOWSTONE, Material.MAGMA_CREAM},
                false, true, EnumParticle.SUSPENDED_DEPTH, 0, 10, null,
                Sound.GLASS, 1.0f, 0.65f, new Vector(0, 1.50, 0)
        ),
        SOUND(
                ChatColor.WHITE + "Loud Silence", new Material[]{Material.GOLD_RECORD, Material.JUKEBOX, Material.NOTE_BLOCK},
                false, true, EnumParticle.NOTE, new Random().nextInt(25), 1, null,
                Sound.GHAST_SCREAM2, 0.85f, 0.90f, new Vector(0, 1.25, 0)
        );

        private final String name;
        private final Material[] materials;
        private final boolean alternateFuel;
        private final boolean enhancement;
        private final EnumParticle particleType;
        private final int particleSpeed;
        private final int particleAmount;
        private final PotionEffect[] potionEffects;
        private final Sound sound;
        private final float volume;
        private final float pitch;
        private final Vector vector;

        Variant(String name, Material[] materials, boolean altFuel, boolean enhancement, EnumParticle particle,
                int speed, int amount, PotionEffect[] effects, Sound sound, float volume, float pitch, Vector vector) {
            this.name = name;
            this.materials = materials;
            this.alternateFuel = altFuel;
            this.enhancement = enhancement;
            this.particleType = particle;
            this.particleSpeed = speed;
            this.particleAmount = amount;
            this.potionEffects = effects;
            this.sound = sound;
            this.volume = volume;
            this.pitch = pitch;
            this.vector = vector;
        }

        public static Variant getEnum(String name) {
            for (Variant v : values()) if (name.equals(ChatColor.stripColor(v.getName()))) return v;
            return null;
        }

        public static boolean isVariant(String name) {
            for (Variant v : values()) if (name.equals(ChatColor.stripColor(v.getName()))) return true;
            return false;
        }

        public String getName() {
            return name;
        }

        public Material[] getMaterials() {
            return materials;
        }

        public boolean isAlternateFuel() {
            return alternateFuel;
        }

        public boolean getEnhancementAllow() {
            return enhancement;
        }

        public EnumParticle getParticleType() {
            return particleType;
        }

        public int getParticleSpeed() {
            return particleSpeed;
        }

        public int getParticleAmount() {
            return particleAmount;
        }

        public PotionEffect[] getPotionEffects() {
            return potionEffects;
        }

        public Sound getSound() {
            return sound;
        }

        public float getVolume() {
            return volume;
        }

        public float getPitch() {
            return pitch;
        }

        public Vector getVector() {
            return vector;
        }

    }

}