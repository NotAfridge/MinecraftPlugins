package com.ullarah.urocket.task;

import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.data.RepairStandData;
import com.ullarah.urocket.function.LocationShift;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.BlockState;
import org.bukkit.block.Furnace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.UUID;

public class StationStandParticles {

    public void task() {

        Plugin plugin = Bukkit.getPluginManager().getPlugin(RocketInit.pluginName);

        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin,
                () -> plugin.getServer().getScheduler().runTask(plugin, () -> {

                    for (Map.Entry<UUID, RepairStandData> repairStand : RocketInit.rocketRepairStand.entrySet()) {
                        processStand(repairStand.getValue());
                    }

                }), 0, 1);
    }

    private void processStand(RepairStandData data) {
        if (!data.isRepairing()) {
            return;
        }

        Location location = data.getLocation();
        if (!location.getWorld().isChunkLoaded(location.getBlockX() >> 4, location.getBlockZ() >> 4)) {
            return;
        }

        ArmorStand stand = data.getStand();
        Location furnaceLoc = new LocationShift().add(stand.getLocation(), 0, -2, 0);
        BlockState furnace = stand.getWorld().getBlockAt(furnaceLoc).getState();

        if (furnace instanceof Furnace && ((Furnace) furnace).getBurnTime() > 0) {
            float x = (float) (location.getBlockX() + 0.5);
            float y = (float) (location.getBlockY() + 0.5);
            float z = (float) (location.getBlockZ() + 0.5);

            stand.getWorld().spawnParticle(Particle.PORTAL, x, y, z, 1, 0, 0, 0, 1);
        } else {
            data.stopRepairing(RepairStandData.StopReason.EMPTY_TANK);
        }
    }

}
