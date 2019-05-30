package com.ullarah.urocket.event;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import com.ullarah.urocket.RocketFunctions;
import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.function.AreaCheck;
import com.ullarah.urocket.function.CommonString;
import com.ullarah.urocket.function.IDTag;
import com.ullarah.urocket.function.LocationShift;
import com.ullarah.urocket.init.RocketLanguage;
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
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BlockPlace implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void repairTankStationPlacement(BlockPlaceEvent event) {

        RocketFunctions rocketFunctions = new RocketFunctions();
        CommonString commonString = new CommonString();
        AreaCheck areaCheck = new AreaCheck();

        if (!event.getItemInHand().hasItemMeta())
            return;

        if (!event.getItemInHand().getItemMeta().hasDisplayName())
            return;

        String rocketItemName = event.getItemInHand().getItemMeta().getDisplayName();

        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (player.getWorld().getName().equals("world")) {

            Location blockLocation = block.getLocation();
            Location belowLocation = new LocationShift().add(blockLocation, 0, -1, 0);

            switch (block.getType()) {

                case BEACON:
                    if (rocketItemName.equals(ChatColor.RED + "Rocket Boot Repair Station")) {

                        if (block.getRelative(BlockFace.DOWN).getType() == Material.FURNACE) {

                            List<String> tankList = RocketInit.getPlugin().getConfig().getStringList("tanks");
                            String tank = new IDTag().create(player, belowLocation);

                            if (!tankList.contains(tank)) {

                                event.setCancelled(true);
                                commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_STATION_PLACE_ERROR);

                            } else {

                                List<String> stationList = RocketInit.getPlugin().getConfig().getStringList("stations");
                                stationList.add(new IDTag().create(player, blockLocation));

                                RocketInit.getPlugin().getConfig().set("stations", stationList);
                                RocketInit.getPlugin().saveConfig();

                                commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_STATION_PLACE_SUCCESS);

                            }

                        } else {

                            event.setCancelled(true);
                            commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_STATION_PLACE_ERROR);

                        }

                    }
                    break;

                case FURNACE:
                    if (rocketItemName.equals(ChatColor.RED + "Rocket Boot Repair Tank")) {

                        List<String> tankList = RocketInit.getPlugin().getConfig().getStringList("tanks");
                        tankList.add(new IDTag().create(player, blockLocation));

                        RocketInit.getPlugin().getConfig().set("tanks", tankList);
                        RocketInit.getPlugin().saveConfig();

                    }
                    break;

                case END_PORTAL_FRAME:
                    if (rocketItemName.equals(ChatColor.RED + "Rocket Boot Fly Zone Controller")) {

                        boolean isFlyZone = false;

                        for (Map.Entry<UUID, ConcurrentHashMap<Location, Location>> rocketZone : RocketInit.rocketZoneLocations.entrySet()) {

                            for (Map.Entry<Location, Location> rocketLocation : rocketZone.getValue().entrySet()) {

                                Location location = player.getLocation();

                                Location zoneStart = rocketLocation.getKey();
                                Location zoneEnd = rocketLocation.getValue();

                                if (areaCheck.cuboid(location, zoneStart, zoneEnd)) isFlyZone = true;

                            }

                        }

                        if (!isFlyZone) {

                            if (RocketInit.getWorldGuard() != null) {
                                RegionQuery query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
                                boolean canBuild = query.testBuild(BukkitAdapter.adapt(blockLocation), RocketInit.getWorldGuard().wrapPlayer(player), Flags.BUILD);
                                if (canBuild)
                                    rocketFunctions.zoneCrystalCreation(player, blockLocation);
                            } else {
                                rocketFunctions.zoneCrystalCreation(player, blockLocation);
                            }

                        } else {

                            event.setCancelled(true);
                            commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_FZ_EXIST);

                        }

                    }
                    break;

                case NOTE_BLOCK:
                    if (rocketItemName.equals("Rocket Boot Variant")) {
                        event.setCancelled(true);
                        commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.PlacementDeny("Variants"));
                    }
                    break;

                case TNT:
                    if (rocketItemName.equals(ChatColor.RED + "Rocket Boot Booster")) {
                        event.setCancelled(true);
                        commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.PlacementDeny("Boosters"));
                    }
                    break;

            }

        } else {

            event.setCancelled(true);

            switch (block.getType()) {

                case BEACON:
                    if (rocketItemName.equals(ChatColor.RED + "Rocket Boot Repair Station"))
                        commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.WorldPlacementDeny("Repair Stations"));
                    break;

                case FURNACE:
                    if (rocketItemName.equals(ChatColor.RED + "Rocket Boot Repair Tank"))
                        commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.WorldPlacementDeny("Repair Tanks"));
                    break;

                case END_PORTAL_FRAME:
                    if (rocketItemName.equals(ChatColor.RED + "Rocket Boot Fly Zone Controller"))
                        commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.WorldPlacementDeny("Fly Zone Controllers"));
                    break;

                case NOTE_BLOCK:
                    if (rocketItemName.equals(ChatColor.AQUA + "Rocket Boot Variant"))
                        commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.PlacementDeny("Variants"));
                    break;

                case TNT:
                    if (rocketItemName.equals(ChatColor.RED + "Rocket Boot Booster"))
                        commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.PlacementDeny("Boosters"));
                    break;

            }

        }

    }

}
