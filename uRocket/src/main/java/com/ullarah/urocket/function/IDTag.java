package com.ullarah.urocket.function;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class IDTag {

    /**
     * Creates an ID tag from a player and a location
     * @param player   Player
     * @param location Location
     * @return ID tag
     */
    public String create(Player player, Location location) {
        int bX = location.getBlockX();
        int bY = location.getBlockY();
        int bZ = location.getBlockZ();
        World world = location.getWorld();

        return player.getUniqueId().toString() + "|" + world.getName() + "|" + bX + "|" + bY + "|" + bZ;
    }

    /**
     * Creates an ID tag from a location
     * @param location Location
     * @return ID tag
     */
    public String create(Location location) {
        int bX = location.getBlockX();
        int bY = location.getBlockY();
        int bZ = location.getBlockZ();
        World world = location.getWorld();

        return world.getName() + "|" + bX + "|" + bY + "|" + bZ;
    }

}
