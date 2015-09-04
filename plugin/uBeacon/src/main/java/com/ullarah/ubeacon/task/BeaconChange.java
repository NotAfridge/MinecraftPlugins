package com.ullarah.ubeacon.task;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.List;
import java.util.Random;

import static com.ullarah.ubeacon.BeaconInit.getPlugin;

public class BeaconChange {

    private static final long beaconInterval = getPlugin().getConfig().getLong("interval") * 20;

    public static void task() {

        Bukkit.getServer().getScheduler().runTaskTimer(getPlugin(), () -> {

            List<String> beaconList = getPlugin().getConfig().getStringList("beacons");

            for (String beacon : beaconList) {

                String[] beaconParts = beacon.split("\\|");

                World world = Bukkit.getWorld(beaconParts[1]);

                Location location = new Location(world,
                        Integer.parseInt(beaconParts[2]),
                        Integer.parseInt(beaconParts[3]) + 1,
                        Integer.parseInt(beaconParts[4]));

                world.getBlockAt(location).setType(Material.STAINED_GLASS);
                world.getBlockAt(location).setData((byte) new Random().nextInt(6)); // Is there another way?

            }

        }, beaconInterval, beaconInterval);

    }

}
