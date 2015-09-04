package com.ullarah.ulib.function;

import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Random;

public class RandomTeleport {

    public static void teleport(final Player player, int boundary) {

        World world = player.getWorld();
        int t = 0;

        while (t < 2) {

            int x = new Random().nextInt(boundary);
            int z = new Random().nextInt(boundary);

            player.teleport(world.getHighestBlockAt(x, z).getLocation());
            player.setVelocity(new Vector(0, 1.25, 0));

            t++;

        }

    }

    public static void teleport(final Player player, int boundary, Sound sound) {

        World world = player.getWorld();
        int t = 0;

        while (t < 2) {

            int x = new Random().nextInt(boundary);
            int z = new Random().nextInt(boundary);

            player.teleport(world.getHighestBlockAt(x, z).getLocation());
            player.getWorld().playSound(player.getEyeLocation(), sound, 0.8f, 0.95f);
            player.setVelocity(new Vector(0, 1.25, 0));

            t++;

        }

    }

}
