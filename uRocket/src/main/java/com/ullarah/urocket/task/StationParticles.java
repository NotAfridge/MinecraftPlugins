package com.ullarah.urocket.task;

import com.ullarah.urocket.RocketInit;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.UUID;

public class StationParticles {

    public void task() {

        Plugin plugin = Bukkit.getPluginManager().getPlugin(RocketInit.pluginName);

        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin,
                () -> plugin.getServer().getScheduler().runTask(plugin, () -> {

                    if (!RocketInit.rocketRepair.isEmpty())
                        for (Map.Entry<UUID, Location> repairStation : RocketInit.rocketRepair.entrySet()) {

                            Player player = Bukkit.getPlayer(repairStation.getKey());

                            float x = (float) (repairStation.getValue().getBlockX() + 0.5);
                            float y = (float) (repairStation.getValue().getBlockY() + 0.5);
                            float z = (float) (repairStation.getValue().getBlockZ() + 0.5);

                            player.getWorld().spawnParticle(Particle.PORTAL, x, y, z, 1, 0, 0, 0, 1);
                        }

                }), 0, 0);

    }

}
