package com.ullarah.urocket.function;

import net.minecraft.server.v1_10_R1.EnumParticle;
import net.minecraft.server.v1_10_R1.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class FakeExplosion {

    /**
     * Produces an explosion like particle, without block damage.
     *
     * @param location  the location of the players world
     * @param size      the size of explosion centered from the location
     * @param explosion the type of explosion from {@link ExplosionType}
     */
    public void create(Location location, Integer size, ExplosionType explosion) {

        float eX = (float) location.getX();
        float eY = (float) location.getY();
        float eZ = (float) location.getZ();

        location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 0.5f);

        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                EnumParticle.valueOf(explosion.type), false, eX, eY, eZ, 0, 0, 0, size, size, null);

        for (Player serverPlayer : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) serverPlayer).getHandle().playerConnection.sendPacket(packet);
        }

    }

    /**
     * Returns the particle type of explosions
     * {@link #NORMAL}
     * {@link #LARGE}
     * {@link #HUGE}
     */
    public enum ExplosionType {

        /**
         * Smaller white particles
         */
        NORMAL("EXPLOSION_NORMAL"),

        /**
         * Normal TNT like particles
         */
        LARGE("EXPLOSION_LARGE"),

        /**
         * Massive particles, wither, enderdragon etc.
         */
        HUGE("EXPLOSION_HUGE");

        private final String type;

        ExplosionType(String getType) {
            type = getType;
        }

        public String toString() {
            return type;
        }

    }

}
