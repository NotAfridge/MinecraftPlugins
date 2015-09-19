package com.ullarah.ulib.function;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.HashSet;

public class EntityLocation {

    public static Entity[] getNearbyEntities(Location location, int radius) {

        int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16)) / 16;

        HashSet<Entity> radiusEntities = new HashSet<>();

        for (int cX = 0 - chunkRadius; cX <= chunkRadius; cX++) {

            for (int cZ = 0 - chunkRadius; cZ <= chunkRadius; cZ++) {

                int eX = (int) location.getX(), eY = (int) location.getY(), eZ = (int) location.getZ();

                for (Entity entity : new Location(location.getWorld(), eX + (cX * 16), eY, eZ + (cZ * 16)).getChunk().getEntities()) {

                    if (entity.getLocation().distance(location) <= radius
                            && entity.getLocation().getBlock() != location.getBlock())

                        radiusEntities.add(entity);

                }

            }

        }

        return radiusEntities.toArray(new Entity[radiusEntities.size()]);

    }

}
