package com.ullarah.ulib.function;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Particles {

    public static void show(ParticleType particle, Location location, Float[] offset, float data, int amount) {

        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                EnumParticle.a(particle.toInteger()), false,
                location.getBlockX(), location.getBlockY(), location.getBlockZ(),
                offset[0], offset[1], offset[2], data, amount, null);

        for (Player serverPlayer : location.getWorld().getPlayers())
            ((CraftPlayer) serverPlayer).getHandle().playerConnection.sendPacket(packet);

    }

    public enum ParticleType {

        EXPLOSION_NORMAL(0),
        EXPLOSION_LARGE(1),
        EXPLOSION_HUGE(2),
        FIREWORKS_SPARK(3),
        WATER_BUBBLE(4),
        WATER_SPLASH(5),
        WATER_WAKE(6),
        SUSPENDED(7),
        SUSPENDED_DEPTH(8),
        CRIT(9),
        CRIT_MAGIC(10),
        SMOKE_NORMAL(11),
        SMOKE_LARGE(12),
        SPELL(13),
        SPELL_INSTANT(14),
        SPELL_MOB(15),
        SPELL_MOB_AMBIENT(16),
        SPELL_WITCH(17),
        DRIP_WATER(18),
        DRIP_LAVA(19),
        VILLAGER_ANGRY(20),
        VILLAGER_HAPPY(21),
        TOWN_AURA(22),
        NOTE(23),
        PORTAL(24),
        ENCHANTMENT_TABLE(25),
        FLAME(26),
        LAVA(27),
        FOOTSTEP(28),
        CLOUD(29),
        REDSTONE(30),
        SNOWBALL(31),
        SNOW_SHOVEL(32),
        SLIME(33),
        HEART(34),
        BARRIER(35),
        ICONCRACK(36),
        BLOCKCRACK(37),
        BLOCKDUST(38),
        DROPLET(39),
        TAKE(40),
        MOBAPPEARANCE(41);

        private final Integer type;

        private ParticleType(Integer getType) {
            type = getType;
        }

        public Integer toInteger() {
            return type;
        }

    }

}
