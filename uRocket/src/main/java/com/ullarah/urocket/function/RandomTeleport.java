package com.ullarah.urocket.function;

import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Random;

public class RandomTeleport {

    /**
     * Teleport a player randomly with a radius
     *
     * @param player the player object
     * @param radius the are of which to teleport from players location
     */
    public void teleport(final Player player, int radius) {

        World world = player.getWorld();
        int t = 0;

        while (t < 2) {

            int x = new Random().nextInt(radius);
            int z = new Random().nextInt(radius);

            player.teleport(world.getHighestBlockAt(x, z).getLocation());
            player.setVelocity(new Vector(0, 1.25, 0));

            t++;

        }

    }

    /**
     * Teleport a player randomly with a radius, with added sound
     *
     * @param player the player object
     * @param radius the are of which to teleport from players location
     * @param sound  the sound which is played before teleport
     */
    public void teleport(final Player player, int radius, Sound sound) {

        player.getWorld().playSound(player.getEyeLocation(), sound, 0.8f, 0.95f);
        teleport(player, radius);

    }

}
