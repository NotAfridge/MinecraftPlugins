package com.ullarah.urocket.task;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;

import static com.ullarah.urocket.RocketInit.pluginName;
import static com.ullarah.urocket.RocketInit.rocketFire;

public class RocketFire {

    public void task() {

        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);

        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin,
                () -> plugin.getServer().getScheduler().runTask(plugin, () -> {

                    if (!rocketFire.isEmpty()) for (HashSet<Location> set : rocketFire)
                        set.stream().filter(location -> location.getBlock().getType().equals(Material.FIRE))
                                .forEach(location -> location.getBlock().setType(Material.AIR));

                }), 60, 60);

    }

}
