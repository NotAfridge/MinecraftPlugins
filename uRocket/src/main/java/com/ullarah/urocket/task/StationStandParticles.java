package com.ullarah.urocket.task;

import com.ullarah.urocket.RocketInit;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.UUID;

public class StationStandParticles {

    public void task() {

        Plugin plugin = Bukkit.getPluginManager().getPlugin(RocketInit.pluginName);

        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin,
                () -> plugin.getServer().getScheduler().runTask(plugin, () -> {

                    if (!RocketInit.rocketRepairStand.isEmpty())
                        for (Map.Entry<UUID, Location> repairStand : RocketInit.rocketRepairStand.entrySet()) {

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

                }), 0, 0);

    }

}
