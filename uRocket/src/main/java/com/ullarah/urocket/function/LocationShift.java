package com.ullarah.urocket.function;

import org.bukkit.Location;

public class LocationShift {

    /**
     * Given an existing location, return a new location shifted
     * @param locA Original location
     * @param x X to add
     * @param y Y to add
     * @param z Z to add
     * @return New location
     */
    public Location add(Location locA, double x, double y, double z) {
        Location locB = new Location(locA.getWorld(), locA.getX(), locA.getY(), locA.getZ());
        locB.add(x, y, z);
        return locB;
    }
}
