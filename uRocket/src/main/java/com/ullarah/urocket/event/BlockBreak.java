package com.ullarah.urocket.event;

import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.recipe.RepairStation;
import com.ullarah.urocket.recipe.RepairTank;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;
import java.util.stream.Collectors;

public class BlockBreak implements Listener {

    @EventHandler
    public void destroyTankStation(BlockBreakEvent event) {

        Block block = event.getBlock();

        if (block.getType() == Material.BEACON) {

            Player player = event.getPlayer();

            World world = player.getWorld();
            Location blockLocation = block.getLocation();

            int bX = blockLocation.getBlockX();
            int bY = blockLocation.getBlockY();
            int bZ = blockLocation.getBlockZ();

            List<String> stationList = RocketInit.getPlugin().getConfig().getStringList("stations");
            List<String> newStationList = stationList.stream().map(station -> station.replaceFirst(".{37}", "")).collect(Collectors.toList());

            String stationOriginal = player.getUniqueId().toString() + "|" + world.getName() + "|" + bX + "|" + bY + "|" + bZ;
            String stationNew = world.getName() + "|" + bX + "|" + bY + "|" + bZ;

            removeRepairStation(world, player, blockLocation, stationList, newStationList, stationOriginal, stationNew);

        }

        if (block.getType() == Material.FURNACE) {

            Player player = event.getPlayer();

            World world = player.getWorld();
            Location blockLocation = block.getLocation();

            int bX = blockLocation.getBlockX();
            int bY = blockLocation.getBlockY();
            int bZ = blockLocation.getBlockZ();

            List<String> tankList = RocketInit.getPlugin().getConfig().getStringList("tanks");
            List<String> newTankList = tankList.stream().map(tank -> tank.replaceFirst(".{37}", "")).collect(Collectors.toList());

            String tankOriginal = player.getUniqueId().toString() + "|" + world.getName() + "|" + bX + "|" + bY + "|" + bZ;
            String tankNew = world.getName() + "|" + bX + "|" + bY + "|" + bZ;

            if (tankList.contains(tankOriginal) || player.hasPermission("rocket.remove")) {

                tankList.remove(newTankList.indexOf(tankNew));
                world.getBlockAt(blockLocation).setType(Material.AIR);

                RocketInit.getPlugin().getConfig().set("tanks", tankList);
                RocketInit.getPlugin().saveConfig();

                Location blockStation = new Location(player.getWorld(), blockLocation.getX(), blockLocation.getY() + 1, blockLocation.getZ());

                if (blockStation.getBlock().getType() == Material.BEACON) {

                    List<String> stationList = RocketInit.getPlugin().getConfig().getStringList("stations");
                    List<String> newStationList = stationList.stream().map(station -> station.replaceFirst(".{37}", "")).collect(Collectors.toList());

                    String stationOriginal = player.getUniqueId().toString() + "|" + world.getName() + "|" + bX + "|" + bY + "|" + bZ;
                    String stationNew = world.getName() + "|" + bX + "|" + bY + "|" + bZ;

                    removeRepairStation(world, player, blockLocation, stationList, newStationList, stationOriginal, stationNew);

                }

                world.createExplosion(blockLocation, 0.0f, false);
                world.dropItemNaturally(blockLocation, new RepairTank().tank());

            }

        }

    }

    private void removeRepairStation(World world, Player player, Location blockLocation, List<String> stationList,
                                     List<String> newStationList, String stationOriginal, String stationNew) {

        if (stationList.contains(stationOriginal) || player.hasPermission("rocket.remove")) {

            stationList.remove(newStationList.indexOf(stationNew));

            world.getBlockAt(blockLocation).setType(Material.AIR);
            RocketInit.getPlugin().getConfig().set("stations", stationList);
            RocketInit.getPlugin().saveConfig();

            world.createExplosion(blockLocation, 0.0f, false);
            world.dropItemNaturally(blockLocation, new RepairStation().station());

        }

    }

}
