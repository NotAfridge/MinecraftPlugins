package com.ullarah.ulib.function;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;

public class GroundFire {

    public static Set setFire(Player player, String type, Material material) {

        Set<Location> fireBlock = new HashSet<>();

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

