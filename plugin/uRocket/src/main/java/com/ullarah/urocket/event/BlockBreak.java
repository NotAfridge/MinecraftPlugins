package com.ullarah.urocket.event;

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

import java.util.ArrayList;
import java.util.List;

import static com.ullarah.urocket.RocketInit.getPlugin;

public class BlockBreak implements Listener {

    @EventHandler
    public void destroyTankStation(BlockBreakEvent event) {

        Player player = event.getPlayer();
        Block block = event.getBlock();

        World world = player.getWorld();
        Location blockLocation = block.getLocation();

        int bX = blockLocation.getBlockX();
        int bY = blockLocation.getBlockY();
        int bZ = blockLocation.getBlockZ();

        if (block.getType() == Material.BEACON) {

            List<String> stationList = getPlugin().getConfig().getStringList("stations");
            List<String> newStationList = new ArrayList<>();

            for (String station : stationList) newStationList.add(station.replaceFirst(".{37}", ""));

            String stationOriginal = player.getUniqueId().toString() + "|"
                    + world.getName() + "|" + bX + "|" + bY + "|" + bZ;

            String stationNew = world.getName() + "|" + bX + "|" + bY + "|" + bZ;

            if (stationList.contains(stationOriginal) || player.hasPermission("rocket.remove")) {

                stationList.remove(newStationList.indexOf(stationNew));

                world.getBlockAt(blockLocation).setType(Material.AIR);
                getPlugin().getConfig().set("stations", stationList);
                getPlugin().saveConfig();

                world.createExplosion(blockLocation, 0.0f, false);
                world.dropItemNaturally(blockLocation, RepairStation.station());

            }

        }

        if (block.getType() == Material.FURNACE) {

            List<String> tankList = getPlugin().getConfig().getStringList("tanks");
            List<String> newTankList = new ArrayList<>();

            for (String tank : tankList) newTankList.add(tank.replaceFirst(".{37}", ""));

            String tankOriginal = player.getUniqueId().toString() + "|"
                    + world.getName() + "|" + bX + "|" + bY + "|" + bZ;

            String tankNew = world.getName() + "|" + bX + "|" + bY + "|" + bZ;

            if (tankList.contains(tankOriginal) || player.hasPermission("rocket.remove")) {

                tankList.remove(newTankList.indexOf(tankNew));
                world.getBlockAt(blockLocation).setType(Material.AIR);

                getPlugin().getConfig().set("tanks", tankList);
                getPlugin().saveConfig();

                Location blockStation = new Location(player.getWorld(),
                        blockLocation.getX(), blockLocation.getY() + 1, blockLocation.getZ());

                if (blockStation.getBlock().getType() == Material.BEACON) {

                    List<String> stationList = getPlugin().getConfig().getStringList("stations");
                    List<String> newStationList = new ArrayList<>();

                    for (String station : stationList) newStationList.add(station.replaceFirst(".{37}", ""));

                    String stationOriginal = player.getUniqueId().toString() + "|"
                            + world.getName() + "|" + bX + "|" + bY + "|" + bZ;

                    String stationNew = world.getName() + "|" + bX + "|" + bY + "|" + bZ;

                    if (stationList.contains(stationOriginal) || player.hasPermission("rocket.remove")) {

                        stationList.remove(newStationList.indexOf(stationNew));

                        world.getBlockAt(blockStation).setType(Material.AIR);
                        getPlugin().getConfig().set("stations", stationList);
                        getPlugin().saveConfig();

                        world.createExplosion(blockLocation, 0.0f, false);
                        world.dropItemNaturally(blockStation, RepairStation.station());

                    }

                }

                world.createExplosion(blockLocation, 0.0f, false);
                world.dropItemNaturally(blockLocation, RepairTank.tank());

            }

        }

    }

}
