package com.ullarah.urocket.function;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.Collection;
import java.util.HashSet;

public class EntityLocation {

    /**
     * Checks entities in a given location
     *
     * @param location the location of the players world
     * @param radius   the radius to check centered from the location
     * @return an array of entities in a chunk
     */
    public Collection<Entity> getNearbyEntities(Location location, double radius) {
        World world = location.getWorld();
        return world == null ? null : world.getNearbyEntities(location, radius, radius, radius);
    }

}
