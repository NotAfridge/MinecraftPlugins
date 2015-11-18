package com.ullarah.urocket.event;

import com.ullarah.urocket.RocketFunctions;
import com.ullarah.urocket.function.CommonString;
import com.ullarah.urocket.function.InsideCuboid;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.ullarah.urocket.RocketInit.*;
import static com.ullarah.urocket.init.RocketLanguage.*;

public class BlockPlace implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void repairTankStationPlacement(BlockPlaceEvent event) {

        RocketFunctions rocketFunctions = new RocketFunctions();
        CommonString commonString = new CommonString();
        InsideCuboid insideCuboid = new InsideCuboid();

        if (event.getItemInHand().hasItemMeta()) {

            if (event.getItemInHand().getItemMeta().hasDisplayName()) {

                String rocketItem = event.getItemInHand().getItemMeta().getDisplayName();

                Player player = event.getPlayer();
                Block block = event.getBlock();

                if (player.getWorld().getName().equals("world")) {

                    World world = player.getWorld();
                    Location blockLocation = block.getLocation();

                    int bX = blockLocation.getBlockX();
                    int bY = blockLocation.getBlockY();
                    int bZ = blockLocation.getBlockZ();

                    switch (block.getType()) {

                        case BEACON:
                            if (rocketItem.equals(ChatColor.RED + "Rocket Repair Station")) {

                                if (block.getRelative(BlockFace.DOWN).getType() == Material.FURNACE) {

                                    List<String> tankList = getPlugin().getConfig().getStringList("tanks");
                                    String tank = player.getUniqueId().toString() + "|" + world.getName() + "|" + bX + "|" + (bY - 1) + "|" + bZ;

                                    if (!tankList.contains(tank)) {

                                        event.setCancelled(true);
                                        commonString.messageSend(getPlugin(), player, true, RB_STATION_PLACE_ERROR);

                                    } else {

                                        List<String> stationList = getPlugin().getConfig().getStringList("stations");
                                        stationList.add(player.getUniqueId().toString() + "|" + world.getName() + "|" + bX + "|" + bY + "|" + bZ);

                                        getPlugin().getConfig().set("stations", stationList);
                                        getPlugin().saveConfig();

                                        commonString.messageSend(getPlugin(), player, true, RB_STATION_PLACE_SUCCESS);

                                    }

                                } else {

                                    event.setCancelled(true);
                                    commonString.messageSend(getPlugin(), player, true, RB_STATION_PLACE_ERROR);

                                }

                            }
                            break;

                        case FURNACE:
                            if (rocketItem.equals(ChatColor.RED + "Rocket Repair Tank")) {

                                List<String> tankList = getPlugin().getConfig().getStringList("tanks");
                                tankList.add(player.getUniqueId().toString() + "|" + world.getName() + "|" + bX + "|" + bY + "|" + bZ);

                                getPlugin().getConfig().set("tanks", tankList);
                                getPlugin().saveConfig();

                            }
                            break;

                        case ENDER_PORTAL_FRAME:
                            if (rocketItem.equals(ChatColor.RED + "Rocket Fly Zone Controller")) {

                                Boolean isFlyZone = false;

                                for (Map.Entry<UUID, ConcurrentHashMap<Location, Location>> rocketZone : rocketZoneLocations.entrySet()) {

                                    for (Map.Entry<Location, Location> rocketLocation : rocketZone.getValue().entrySet()) {

                                        Location location = player.getLocation();

                                        Location zoneStart = rocketLocation.getKey();
                                        Location zoneEnd = rocketLocation.getValue();

                                        if (insideCuboid.check(location, zoneStart, zoneEnd)) isFlyZone = true;

                                    }

                                }

                                if (!isFlyZone) {

                                    if (getWorldGuard() != null) {
                                        if (getWorldGuard().canBuild(player, blockLocation))
                                            rocketFunctions.zoneCrystalCreation(player, blockLocation);
                                    } else rocketFunctions.zoneCrystalCreation(player, blockLocation);

                                } else {

                                    event.setCancelled(true);
                                    commonString.messageSend(getPlugin(), player, true, RB_FZ_EXIST);

                                }

                            }
                            break;

                        case NOTE_BLOCK:
                            if (ChatColor.stripColor(rocketItem).equals("Variant Booster")) {
                                event.setCancelled(true);
                                commonString.messageSend(getPlugin(), player, true, PlacementDeny("Variants"));
                            }
                            break;

                        case TNT:
                            if (rocketItem.equals(ChatColor.RED + "Rocket Booster")) {
                                event.setCancelled(true);
                                commonString.messageSend(getPlugin(), player, true, PlacementDeny("Boosters"));
                            }
                            break;

                    }

                } else {

                    event.setCancelled(true);

                    switch (block.getType()) {

                        case BEACON:
                            if (rocketItem.equals(ChatColor.RED + "Rocket Repair Station"))
                                commonString.messageSend(getPlugin(), player, true, WorldPlacementDeny("Repair Stations"));
                            break;

                        case FURNACE:
                            if (rocketItem.equals(ChatColor.RED + "Rocket Repair Tank"))
                                commonString.messageSend(getPlugin(), player, true, WorldPlacementDeny("Repair Tanks"));
                            break;

                        case ENDER_PORTAL_FRAME:
                            if (rocketItem.equals(ChatColor.RED + "Rocket Fly Zone Controller"))
                                commonString.messageSend(getPlugin(), player, true, WorldPlacementDeny("Fly Zone Controllers"));
                            break;

                        case NOTE_BLOCK:
                            if (ChatColor.stripColor(rocketItem).equals("Variant Booster"))
                                commonString.messageSend(getPlugin(), player, true, PlacementDeny("Variants"));
                            break;

                        case TNT:
                            if (rocketItem.equals(ChatColor.RED + "Rocket Booster"))
                                commonString.messageSend(getPlugin(), player, true, PlacementDeny("Boosters"));
                            break;

                    }

                }

            }

        }

    }

}
