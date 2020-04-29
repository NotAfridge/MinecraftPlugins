package com.ullarah.urocket.task;

import com.ullarah.urocket.RocketFunctions;
import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.data.RepairStandData;
import com.ullarah.urocket.function.LocationShift;
import com.ullarah.urocket.function.SignText;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.Furnace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class StationStandRepair {

    public void task() {

        Plugin plugin = Bukkit.getPluginManager().getPlugin(RocketInit.pluginName);

        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin,
                () -> plugin.getServer().getScheduler().runTask(plugin, () -> {

                    for (Map.Entry<UUID, RepairStandData> repairStand : RocketInit.rocketRepairStand.entrySet()) {
                        processStand(repairStand.getValue());
                    }

                }), 0, 600);
    }

    private void processStand(RepairStandData data) {
        if (!data.isRepairing()) {
            return;
        }

        RocketFunctions rocketFunctions = new RocketFunctions();

        Location location = data.getLocation();
        if (!location.getWorld().isChunkLoaded(location.getBlockX() >> 4, location.getBlockZ() >> 4)) {
            return;
        }

        ArmorStand stand = data.getStand();
        Location furnaceLoc = new LocationShift().add(stand.getLocation(), 0, -2, 0);
        BlockState furnace = stand.getWorld().getBlockAt(furnaceLoc).getState();

        if (furnace instanceof Furnace && ((Furnace) furnace).getBurnTime() > 0) {

            ItemStack standBoots = stand.getEquipment().getBoots();

            if (standBoots.hasItemMeta()) {

                int bootRepair = rocketFunctions.getBootRepairRate(standBoots);

                short bootDurability = standBoots.getDurability();
                int bootMaterialDurability = rocketFunctions.getBootDurability(standBoots);

                int bootHealthOriginal = (bootMaterialDurability - bootDurability);
                int bootHealthNew = ((bootMaterialDurability - bootDurability) + bootRepair);

                if (bootHealthOriginal >= bootMaterialDurability) {
                    data.stopRepairing(RepairStandData.StopReason.ALREADY_MAX);
                } else {
                    standBoots.setDurability((short) (bootDurability - bootRepair));
                    stand.getEquipment().setBoots(standBoots);

                    if (bootHealthNew >= bootMaterialDurability) {
                        data.stopRepairing(RepairStandData.StopReason.FULLY_REPAIRED);
                    } else {
                        data.startRepairing(standBoots);

                        float x = (float) stand.getLocation().getX();
                        float y = (float) stand.getLocation().getY();
                        float z = (float) stand.getLocation().getZ();

                        stand.getWorld().spawnParticle(Particle.PORTAL, x, y, z, 500, 0, 0, 0, 1);
                    }
                }
            }

        } else {
            data.stopRepairing(RepairStandData.StopReason.EMPTY_TANK);
        }
    }

}
