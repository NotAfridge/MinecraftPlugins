package com.ullarah.urocket.init;

import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.function.PluginRegisters;
import com.ullarah.urocket.recipe.RocketVariants;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Random;

public class RocketVariant {

    public void init() {

        RocketInit.registerMap.put("variant", 0);
        for (Variant variant : Variant.values())
            RocketInit.registerMap.put("variant", RocketInit.registerMap.get("variant") +
                    new PluginRegisters().register(RocketInit.getPlugin(), PluginRegisters.RegisterType.RECIPE,
                            new RocketVariants(variant.getName(), variant.getMaterials())));

    }

    public enum Variant {

        ORIGINAL(
                ChatColor.YELLOW + "Original", new Material[]{Material.FIRE, Material.FIRE, Material.FIRE},
                EnumParticle.FLAME, 0, 5, null,
                Sound.ENTITY_FIREWORK_LAUNCH, 0.8f, 0.35f, new Vector(0, 1.25, 0),
                Material.COAL, Material.COAL_BLOCK
        ),
        ENDER(
                ChatColor.DARK_AQUA + "Essence of Ender", new Material[]{Material.EYE_OF_ENDER, Material.IRON_BLOCK, Material.ENDER_STONE},
                EnumParticle.PORTAL, 0, 25,
                new PotionEffect[]{
                        new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 2, false, false),
                        new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, 4, false, false)
                }, Sound.ENTITY_ENDERMEN_STARE, 0.8f, 1.3f, new Vector(0, 0.25, 0),
                Material.COAL, Material.COAL_BLOCK
        ),
        HEALTH(
                ChatColor.GREEN + "Health Zapper", new Material[]{Material.PUMPKIN_PIE, Material.REDSTONE_TORCH_ON, Material.TNT},
                EnumParticle.CRIT_MAGIC, 0, 25, null,
                Sound.ENTITY_SLIME_JUMP, 0.8f, 0.5f, new Vector(0, 1.25, 0),
                null, null
        ),
        KABOOM(
                ChatColor.RED + "TNT Overload", new Material[]{Material.BLAZE_POWDER, Material.BLAZE_ROD, Material.TNT},
                EnumParticle.EXPLOSION_NORMAL, 0, 10, null,
                Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 0.35f, new Vector(0, 0, 0),
                Material.COAL, Material.COAL_BLOCK
        ),
        RAINBOW(
                ChatColor.YELLOW + "Radical Rainbows", new Material[]{Material.BEACON, Material.REDSTONE_COMPARATOR, Material.STAINED_GLASS},
                EnumParticle.REDSTONE, new Random().nextInt(25), new Random().nextInt(25), null,
                Sound.ENTITY_HORSE_GALLOP, 0.8f, 0.5f, new Vector(0, 1.25, 0),
                Material.COAL, Material.COAL_BLOCK
        ),
        WATER(
                ChatColor.BLUE + "Water Slider", new Material[]{Material.WATER_LILY, Material.PRISMARINE_CRYSTALS, Material.WATER_BUCKET},
                EnumParticle.WATER_BUBBLE, 0, 25, null,
                Sound.BLOCK_WATER_AMBIENT, 0.8f, 0.5f, new Vector(0, 1.25, 0),
                Material.COAL, Material.COAL_BLOCK
        ),
        ZERO(
                ChatColor.YELLOW + "Patient Zero", new Material[]{Material.BLAZE_POWDER, Material.EXP_BOTTLE, Material.ENCHANTED_BOOK},
                EnumParticle.ENCHANTMENT_TABLE, 0, 25,
                new PotionEffect[]{
                        new PotionEffect(PotionEffectType.CONFUSION, Integer.MAX_VALUE, 0, false, false),
                        new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1, false, false),
                        new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1, false, false)
                }, Sound.ENTITY_LIGHTNING_THUNDER, 0.8f, 2.25f, new Vector(0, 1.25, 0),
                Material.COAL, Material.COAL_BLOCK
        ),
        NOTE(
                ChatColor.GOLD + "Musical Madness", new Material[]{Material.GREEN_RECORD, Material.JUKEBOX, Material.NOTE_BLOCK},
                EnumParticle.NOTE, new Random().nextInt(25), 1, null,
                Sound.BLOCK_NOTE_SNARE, 0.8f, 0.5f, new Vector(0, 1.25, 0),
                Material.COAL, Material.COAL_BLOCK
        ),
        STEALTH(
                ChatColor.WHITE + "Super Stealth", new Material[]{Material.SLIME_BALL, Material.PACKED_ICE, Material.SOUL_SAND},
                EnumParticle.MOB_APPEARANCE, 0, 1, null,
                Sound.ENTITY_ENDERMEN_TELEPORT, 1.25f, 0.25f, new Vector(0, 0.5, 0),
                Material.COAL, Material.COAL_BLOCK
        ),
        MONEY(
                ChatColor.GREEN + "Robin Hood", new Material[]{Material.EMERALD, Material.DIAMOND, Material.GOLD_INGOT},
                EnumParticle.VILLAGER_HAPPY, 0, 5, null,
                Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 0.75f, new Vector(0, 1.25, 0),
                null, null
        ),
        DRUNK(
                ChatColor.GRAY + "Glazed Over", new Material[]{Material.WEB, Material.CACTUS, Material.FISHING_ROD},
                EnumParticle.SPELL_MOB, 0, 1,
                new PotionEffect[]{
                        new PotionEffect(PotionEffectType.CONFUSION, Integer.MAX_VALUE, 0, false, false),
                        new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 2, false, false)
                }, Sound.ENTITY_PLAYER_BURP, 1.25f, 0.95f, new Vector(0, 1.25, 0),
                Material.COAL, Material.COAL_BLOCK
        ),
        BOOST(
                ChatColor.AQUA + "Pole Vaulter", new Material[]{Material.RABBIT_FOOT, Material.SLIME_BLOCK, Material.FIREWORK_CHARGE},
                EnumParticle.SNOWBALL, 0, 5,
                new PotionEffect[]{
                        new PotionEffect(PotionEffectType.HEAL, Integer.MAX_VALUE, 1, false, false)
                }, Sound.ENTITY_MAGMACUBE_JUMP, 1.25f, 0.75f, new Vector(0, 20, 0),
                Material.COAL, Material.COAL_BLOCK
        ),
        TREE(
                ChatColor.GRAY + "Tree Hugger", new Material[]{Material.NETHERRACK, Material.LOG, Material.FURNACE},
                EnumParticle.TOWN_AURA, 0, 50, null,
                Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 1.25f, 0.95f, new Vector(0, 1, 0),
                Material.WOOD, Material.LOG
        ),
        FURY(
                ChatColor.DARK_RED + "Red Fury", new Material[]{Material.REDSTONE, Material.REDSTONE_COMPARATOR, Material.BLAZE_POWDER},
                EnumParticle.REDSTONE, 0, 10, null,
                Sound.BLOCK_PISTON_EXTEND, 1.25f, 0.55f, new Vector(0, 1, 0),
                Material.REDSTONE, Material.REDSTONE_BLOCK
        ),
        RUNNER(
                ChatColor.GOLD + "Rocket Runner", new Material[]{Material.SUGAR, Material.SUGAR, Material.PRISMARINE_CRYSTALS},
                EnumParticle.EXPLOSION_NORMAL, 0, 30, null,
                Sound.ENTITY_HORSE_GALLOP, 1.25f, 0.75f, new Vector(0, 0, 0),
                Material.COAL, Material.COAL_BLOCK
        ),
        GLOW(
                ChatColor.YELLOW + "Shooting Star", new Material[]{Material.TORCH, Material.GLOWSTONE, Material.MAGMA_CREAM},
                EnumParticle.SUSPENDED_DEPTH, 0, 10, null,
                Sound.BLOCK_GLASS_BREAK, 1.0f, 0.65f, new Vector(0, 1.50, 0),
                Material.GLOWSTONE_DUST, Material.GLOWSTONE
        ),
        SOUND(
                ChatColor.WHITE + "Loud Silence", new Material[]{Material.GOLD_RECORD, Material.JUKEBOX, Material.NOTE_BLOCK},
                EnumParticle.NOTE, new Random().nextInt(25), 1, null,
                Sound.ENTITY_GHAST_SCREAM, 0.85f, 0.90f, new Vector(0, 1.25, 0),
                Material.COAL, Material.COAL_BLOCK
        );

        private final String name;
        private final Material[] materials;
        private final EnumParticle particleType;
        private final int particleSpeed;
        private final int particleAmount;
        private final PotionEffect[] potionEffects;
        private final Sound sound;
        private final float volume;
        private final float pitch;
        private final Vector vector;
        private final Material fuelSingle;
        private final Material fuelBlock;

        Variant(String variantName, Material[] variantMaterial, EnumParticle variantParticle, int particleSpeed,
                int particleAmount, PotionEffect[] variantEffects, Sound launchSound, float soundVolume,
                float soundPitch, Vector launchVector, Material fuelSingle, Material fuelBlock) {
            this.name = variantName;
            this.materials = variantMaterial;
            this.particleType = variantParticle;
            this.particleSpeed = particleSpeed;
            this.particleAmount = particleAmount;
            this.potionEffects = variantEffects;
            this.sound = launchSound;
            this.volume = soundVolume;
            this.pitch = soundPitch;
            this.vector = launchVector;
            this.fuelSingle = fuelSingle;
            this.fuelBlock = fuelBlock;
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

        public Material getFuelSingle() {
            return fuelSingle;
        }

        public Material getFuelBlock() {
            return fuelBlock;
        }

    }

}