package com.ullarah.urocket.task;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import static com.ullarah.urocket.RocketInit.getPlugin;
import static com.ullarah.urocket.RocketInit.rocketFire;

public class RocketFire {

    public void task() {

        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(),
                () -> Bukkit.getScheduler().runTask(getPlugin(), () -> {

                    if (!rocketFire.isEmpty())
                        rocketFire.stream().filter(fire -> fire.getBlock().getType() == Material.FIRE)
                                .forEach(fire -> fire.getBlock().setType(Material.AIR));

                }), 60, 60);

    }

}
