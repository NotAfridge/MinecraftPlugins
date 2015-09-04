package com.ullarah.urocket.task;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

import static com.ullarah.urocket.RocketInit.getPlugin;
import static com.ullarah.urocket.RocketInit.rocketRepairStand;

public class StationStandParticles {

    public void task() {

        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(), new Runnable() {

            public void run() {

                Bukkit.getScheduler().runTask(getPlugin(), new Runnable() {

                    @Override
                    public void run() {

                        if (!rocketRepairStand.isEmpty())
                            for (Map.Entry<UUID, Location> repairStand : rocketRepairStand.entrySet()) {

                                Chunk chunk = repairStand.getValue().getWorld().getChunkAt(repairStand.getValue());

                                if (chunk.isLoaded()) {

                                    for (Entity entity : chunk.getEntities()) {

                                        if (entity instanceof ArmorStand) {

                                            LivingEntity stand = (LivingEntity) entity;

                                            float x = (float) (repairStand.getValue().getBlockX() + 0.5);
                                            float y = (float) (repairStand.getValue().getBlockY() + 0.5);
                                            float z = (float) (repairStand.getValue().getBlockZ() + 0.5);

                                            PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                                                    EnumParticle.PORTAL, false, x, y, z, 0, 0, 0, 1, 1, null);

                                            for (Player serverPlayer : stand.getWorld().getPlayers())
                                                ((CraftPlayer) serverPlayer).getHandle().playerConnection.sendPacket(packet);

                                        }

                                    }

                                }

                            }

                    }

                });

            }

        }, 0, 0);

    }

}
