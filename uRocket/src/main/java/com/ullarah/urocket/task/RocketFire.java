package com.ullarah.urocket.task;

import com.ullarah.urocket.RocketInit;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;

public class RocketFire {

    public void task() {

        Plugin plugin = Bukkit.getPluginManager().getPlugin(RocketInit.pluginName);

        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin,
                () -> plugin.getServer().getScheduler().runTask(plugin, () -> {

                    if (!RocketInit.rocketFire.isEmpty()) for (HashSet<Location> set : RocketInit.rocketFire)
                        set.stream().filter(location -> location.getBlock().getType().equals(Material.FIRE))
                                .forEach(location -> location.getBlock().setType(Material.AIR));

                }), 60, 60);

    }

}
