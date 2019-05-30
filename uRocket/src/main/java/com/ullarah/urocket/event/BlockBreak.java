package com.ullarah.urocket.event;

import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.function.CommonString;
import com.ullarah.urocket.function.IDTag;
import com.ullarah.urocket.function.LocationShift;
import com.ullarah.urocket.init.RocketLanguage;
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
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location blockLocation = block.getLocation();
        Location aboveLocation = new LocationShift().add(blockLocation, 0, 1 ,0);

        if (block.getType() == Material.BEACON) {
            removeRepairStation(event, player, blockLocation, aboveLocation);
        }
        else if (block.getType() == Material.FURNACE) {
            removeRepairTank(event, player, blockLocation, aboveLocation);
        }
    }

    private void removeRepairStation(BlockBreakEvent event, Player player, Location blockLocation, Location aboveLocation) {
        List<String> stationList = RocketInit.getPlugin().getConfig().getStringList("stations");
        List<String> newStationList = stationList.stream().map(station -> station.replaceFirst(".{37}", "")).collect(Collectors.toList());

        String stationNew = new IDTag().create(blockLocation);

        int index = newStationList.indexOf(stationNew);
        if (index >= 0) {

            // Don't break if there is a stand above
            List<String> standList = RocketInit.getPlugin().getConfig().getStringList("stands");
            List<String> newStandList = standList.stream().map(stand -> stand.replaceFirst(".{37}", "")).collect(Collectors.toList());
            String standNew = new IDTag().create(aboveLocation);
            if (newStandList.contains(standNew)) {
                event.setCancelled(true);
                new CommonString().messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_STATION_BREAK_ERROR);
                return;
            }

            stationList.remove(index);

            World world = player.getWorld();
            world.getBlockAt(blockLocation).setType(Material.AIR);
            RocketInit.getPlugin().getConfig().set("stations", stationList);
            RocketInit.getPlugin().saveConfig();

            world.createExplosion(blockLocation, 0.0f, false);
            world.dropItemNaturally(blockLocation, new RepairStation().station());
        }
    }

    private void removeRepairTank(BlockBreakEvent event, Player player, Location blockLocation, Location aboveLocation) {
        List<String> tankList = RocketInit.getPlugin().getConfig().getStringList("tanks");
        List<String> newTankList = tankList.stream().map(tank -> tank.replaceFirst(".{37}", "")).collect(Collectors.toList());

        String tankNew = new IDTag().create(blockLocation);

        int index = newTankList.indexOf(tankNew);
        if (index >= 0) {

            // Don't break if there is a station above
            List<String> stationList = RocketInit.getPlugin().getConfig().getStringList("stations");
            List<String> newStationList = stationList.stream().map(station -> station.replaceFirst(".{37}", "")).collect(Collectors.toList());
            String stationNew = new IDTag().create(aboveLocation);
            if (newStationList.contains(stationNew)) {
                event.setCancelled(true);
                new CommonString().messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_TANK_BREAK_ERROR);
                return;
            }

            tankList.remove(index);

            World world = player.getWorld();
            world.getBlockAt(blockLocation).setType(Material.AIR);
            RocketInit.getPlugin().getConfig().set("tanks", tankList);
            RocketInit.getPlugin().saveConfig();

            world.createExplosion(blockLocation, 0.0f, false);
            world.dropItemNaturally(blockLocation, new RepairTank().tank());
        }
    }

}
