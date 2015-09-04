package com.ullarah.ulib.function;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class CenterBlock {

    public static Location exact(Player player, Location location) {

        World world = player.getWorld();

        Location center = new Location(world,
                location.getBlockX() + 0.5,
                location.getBlockY() + 0.5,
                location.getBlockZ() + 0.5);

        return new Location(world, center.getBlockX(), center.getBlockY(), center.getBlockZ());

    }

    public static Location variable(Player player, Location location, double height) {

        World world = player.getWorld();

        Location center = new Location(world,
                location.getBlockX() + 0.5,
                location.getBlockY() + height, //0.475 EnderCrystal
                location.getBlockZ() + 0.5);

        return new Location(world, center.getBlockX(), center.getBlockY(), center.getBlockZ());

    }

}
