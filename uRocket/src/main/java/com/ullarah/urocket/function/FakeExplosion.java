package com.ullarah.urocket.function;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;

public class FakeExplosion {

    /**
     * Produces an explosion like particle, without block damage.
     *
     * @param location  the location of the players world
     * @param size      the size of explosion centered from the location
     * @param explosion the type of explosion from {@link ExplosionType}
     */
    public void create(Location location, Integer size, ExplosionType explosion) {

        location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 0.5f);

        location.getWorld().spawnParticle(explosion.type, location, size, 0, 0, 0, size, null);

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
        NORMAL(Particle.EXPLOSION_NORMAL),

        /**
         * Normal TNT like particles
         */
        LARGE(Particle.EXPLOSION_LARGE),

        /**
         * Massive particles, wither, enderdragon etc.
         */
        HUGE(Particle.EXPLOSION_HUGE);

        private final Particle type;

        ExplosionType(Particle getType) {
            type = getType;
        }

        public String toString() {
            return type.name();
        }

    }

}
