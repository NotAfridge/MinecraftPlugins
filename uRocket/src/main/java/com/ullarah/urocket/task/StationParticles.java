package com.ullarah.urocket.task;

import com.ullarah.urocket.RocketInit;
import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
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

                            PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                                    EnumParticle.PORTAL, false, x, y, z, 0, 0, 0, 1, 1, null);

                            for (Player serverPlayer : player.getWorld().getPlayers())
                                ((CraftPlayer) serverPlayer).getHandle().playerConnection.sendPacket(packet);

                        }

                }), 0, 0);

    }

}
