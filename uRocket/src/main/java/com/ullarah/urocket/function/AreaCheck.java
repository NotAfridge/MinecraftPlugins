package com.ullarah.urocket.function;

import org.bukkit.Location;
import org.bukkit.Material;

public class AreaCheck {

    /**
     * Cuboid location check
     *
     * @param location the location of the object
     * @param start    the cuboid start location
     * @param end      the cuboid end location
     * @return is the location of the object within the cuboid
     */
    public boolean cuboid(Location location, Location start, Location end) {

        int x1 = Math.min(start.getBlockX(), end.getBlockX());
        int y1 = Math.min(start.getBlockY(), end.getBlockY());
        int z1 = Math.min(start.getBlockZ(), end.getBlockZ());

        int x2 = Math.max(start.getBlockX(), end.getBlockX());
        int y2 = Math.max(start.getBlockY(), end.getBlockY());
        int z2 = Math.max(start.getBlockZ(), end.getBlockZ());

        return location.getBlockX() >= x1 && location.getBlockX() <= x2
                && location.getBlockY() >= y1 && location.getBlockY() <= y2
                && location.getBlockZ() >= z1 && location.getBlockZ() <= z2;

    }

    /**
     * Check all blocks above for material
     *
     * @param location the location of the object
     * @param material the material of the block
     * @return returns true if block of material exists
     */
    public boolean above(Location location, Material material) {

        for (double l = location.getY() + 1; l < 256; l++) {

            new Location(location.getWorld(), location.getX(), l, location.getZ());
            if (location.getBlock().getType().equals(material)) return true;

        }

        return false;

    }

}
