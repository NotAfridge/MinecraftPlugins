package com.ullarah.urocket.task;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import static com.ullarah.urocket.RocketInit.getPlugin;
import static com.ullarah.urocket.RocketInit.rocketFire;

public class RocketFire {

    public void task() {

        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(), new Runnable() {

            public void run() {

                Bukkit.getScheduler().runTask(getPlugin(), new Runnable() {

                    @Override
                    public void run() {

                        if (!rocketFire.isEmpty()) for (Location fire : rocketFire)
                            if (fire.getBlock().getType() == Material.FIRE) fire.getBlock().setType(Material.AIR);

                    }

                });

            }

        }, 60, 60);

    }

}
