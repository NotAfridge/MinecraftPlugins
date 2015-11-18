package com.ullarah.urocket.function;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;

public class GroundFire {

    /**
     * Sets blocks on fire given players location.
     *
     * @param player   the player object
     * @param type     the type of fire, single, or multiple centered around location
     * @param material the material type to set on fire
     * @return a set of fire locations
     */
    public HashSet<Location> setFire(Player player, String type, Material material) {

        HashSet<Location> fireBlock = new HashSet<>();

        if (!player.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {

            Location location = player.getLocation();

            final Location y = new Location(location.getWorld(), location.getX(), location.getY() - 1, location.getZ());

            switch (type.toUpperCase()) {

                case "BOOST":

                    Set<Location> fireLocation = new HashSet<Location>() {{
                        add(y);
                        add(new Location(y.getWorld(), y.getX() + 1, y.getY(), y.getZ()));
                        add(new Location(y.getWorld(), y.getX() - 1, y.getY(), y.getZ()));
                        add(new Location(y.getWorld(), y.getX(), y.getY(), y.getZ() + 1));
                        add(new Location(y.getWorld(), y.getX(), y.getY(), y.getZ() - 1));
                    }};

                    for (Location fireBoost : fireLocation) {

                        if (fireBoost.getBlock().getRelative(BlockFace.UP).getType() == Material.AIR &&
                                fireBoost.getBlock().getRelative(BlockFace.DOWN).getType() == material) {

                            fireBlock.add(fireBoost);
                            fireBoost.getBlock().setType(Material.FIRE);

                            return fireBlock;

                        }

                    }

                    break;

                case "SINGLE":

                    if (location.getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR &&
                            y.getBlock().getRelative(BlockFace.DOWN).getType() == material) {

                        fireBlock.add(y);
                        y.getBlock().setType(Material.FIRE);

                        return fireBlock;

                    }

                    break;

            }

        }

        return fireBlock;

    }

}

