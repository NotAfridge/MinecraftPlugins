package com.ullarah.ulib.function;

import org.bukkit.Location;

public class InsideCuboid {

    public static boolean check(Location player, Location start, Location end) {

        int x1 = Math.min(start.getBlockX(), end.getBlockX());
        int y1 = Math.min(start.getBlockY(), end.getBlockY());
        int z1 = Math.min(start.getBlockZ(), end.getBlockZ());

        int x2 = Math.max(start.getBlockX(), end.getBlockX());
        int y2 = Math.max(start.getBlockY(), end.getBlockY());
        int z2 = Math.max(start.getBlockZ(), end.getBlockZ());

        return player.getBlockX() >= x1 && player.getBlockX() <= x2
                && player.getBlockY() >= y1 && player.getBlockY() <= y2
                && player.getBlockZ() >= z1 && player.getBlockZ() <= z2;

    }

}
