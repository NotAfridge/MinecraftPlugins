package com.ullarah.urocket.task;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

import static com.ullarah.urocket.RocketInit.getPlugin;
import static com.ullarah.urocket.RocketInit.rocketRepair;

public class StationParticles {

    public void task() {

        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(),
                () -> Bukkit.getScheduler().runTask(getPlugin(), () -> {

                    if (!rocketRepair.isEmpty())
                        for (Map.Entry<UUID, Location> repairStation : rocketRepair.entrySet()) {

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
