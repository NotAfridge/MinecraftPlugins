package com.ullarah.urocket.task;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import static com.ullarah.urocket.RocketInit.pluginName;
import static com.ullarah.urocket.RocketInit.rocketFire;

public class RocketFire {

    public void task() {

        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);

        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin,
                () -> plugin.getServer().getScheduler().runTask(plugin, () -> {

                    if (!rocketFire.isEmpty())
                        rocketFire.stream().filter(fire -> fire.getBlock().getType() == Material.FIRE)
                                .forEach(fire -> fire.getBlock().setType(Material.AIR));

                }), 60, 60);

    }

}
