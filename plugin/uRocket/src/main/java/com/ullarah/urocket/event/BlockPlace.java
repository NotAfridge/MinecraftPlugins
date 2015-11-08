package com.ullarah.urocket.event;

import com.ullarah.ulib.function.InsideCuboid;
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

import static com.ullarah.urocket.RocketFunctions.zoneCrystalCreation;
import static com.ullarah.urocket.RocketInit.*;

public class BlockPlace implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void repairTankStationPlacement(BlockPlaceEvent event) {

        Player player = event.getPlayer();
        Block block = event.getBlock();

        World world = player.getWorld();
        Location blockLocation = block.getLocation();

        if (event.getItemInHand().hasItemMeta()) {

            if (event.getItemInHand().getItemMeta().hasDisplayName()) {

                String rocketItem = event.getItemInHand().getItemMeta().getDisplayName();

                if (player.getWorld().getName().equals("world")) {

                    switch (block.getType()) {

                        case BEACON:
                            if (rocketItem.equals(ChatColor.RED + "Rocket Repair Station")) {

                                if (block.getRelative(BlockFace.DOWN).getType() == Material.FURNACE) {

                                    List<String> tankList = getPlugin().getConfig().getStringList("tanks");

                                    String tank = player.getUniqueId().toString() + "|"
                                            + world.getName() + "|"
                                            + blockLocation.getBlockX() + "|"
                                            + (blockLocation.getBlockY() - 1) + "|"
                                            + blockLocation.getBlockZ();

                                    if (!tankList.contains(tank)) {

                                        event.setCancelled(true);
                                        player.sendMessage(getMsgPrefix() + ChatColor.RED
                                                + "You can only place this on top of a Repair Tank!");

                                    } else {

                                        List<String> stationList = getPlugin().getConfig().getStringList("stations");

                                        stationList.add(player.getUniqueId().toString() + "|"
                                                + world.getName() + "|"
                                                + blockLocation.getBlockX() + "|"
                                                + blockLocation.getBlockY() + "|"
                                                + blockLocation.getBlockZ());

                                        getPlugin().getConfig().set("stations", stationList);
                                        getPlugin().saveConfig();

                                        player.sendMessage(getMsgPrefix() + ChatColor.YELLOW
                                                + "Rocket Repair Station ready to use!");

                                    }

                                } else {

                                    event.setCancelled(true);
                                    player.sendMessage(getMsgPrefix() + ChatColor.RED
                                            + "You can only place this on top of a Repair Tank!");

                                }

                            }
                            break;

                        case FURNACE:
                            if (rocketItem.equals(ChatColor.RED + "Rocket Repair Tank")) {

                                List<String> tankList = getPlugin().getConfig().getStringList("tanks");

                                tankList.add(player.getUniqueId().toString() + "|"
                                        + world.getName() + "|"
                                        + blockLocation.getBlockX() + "|"
                                        + blockLocation.getBlockY() + "|"
                                        + blockLocation.getBlockZ());

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

                                        if (InsideCuboid.check(location, zoneStart, zoneEnd)) isFlyZone = true;

                                    }

                                }

                                if (!isFlyZone) {

                                    if (getWorldGuard() != null) {
                                        if (getWorldGuard().canBuild(player, blockLocation)) {
                                            zoneCrystalCreation(player, blockLocation);
                                        }
                                    } else zoneCrystalCreation(player, blockLocation);

                                } else {

                                    event.setCancelled(true);
                                    player.sendMessage(getMsgPrefix() + ChatColor.YELLOW
                                            + "This is already a No-Fly Zone!");

                                }

                            }
                            break;

                        case NOTE_BLOCK:
                            if (ChatColor.stripColor(rocketItem).equals("Variant Booster")) {

                                event.setCancelled(true);
                                player.sendMessage(getMsgPrefix() + ChatColor.YELLOW
                                        + "Rocket Variants cannot be placed down!");

                            }
                            break;

                        case TNT:
                            if (rocketItem.equals(ChatColor.RED + "Rocket Booster")) {

                                event.setCancelled(true);
                                player.sendMessage(getMsgPrefix() + ChatColor.YELLOW
                                        + "Rocket Boosters cannot be placed down!");

                            }
                            break;

                    }

                } else {

                    event.setCancelled(true);

                    switch (block.getType()) {

                        case BEACON:
                            if (rocketItem.equals(ChatColor.RED + "Rocket Repair Station"))
                                player.sendMessage(getMsgPrefix() + ChatColor.RED
                                        + "Repair Stations cannot be placed here!");
                            break;

                        case FURNACE:
                            if (rocketItem.equals(ChatColor.RED + "Rocket Repair Tank"))
                                player.sendMessage(getMsgPrefix() + ChatColor.RED
                                        + "Repair Tanks cannot be placed here!");
                            break;

                        case ENDER_PORTAL_FRAME:
                            if (rocketItem.equals(ChatColor.RED + "Rocket Fly Zone Controller"))
                                player.sendMessage(getMsgPrefix() + ChatColor.RED
                                        + "Fly Zone Controllers cannot be placed here!");
                            break;

                        case NOTE_BLOCK:
                            if (ChatColor.stripColor(rocketItem).equals("Variant Booster"))
                                player.sendMessage(getMsgPrefix() + ChatColor.YELLOW
                                        + "Variants cannot be placed down!");
                            break;

                        case TNT:
                            if (rocketItem.equals(ChatColor.RED + "Rocket Booster"))
                                player.sendMessage(getMsgPrefix() + ChatColor.YELLOW
                                        + "Boosters cannot be placed down!");
                            break;

                    }

                }

            }

        }

    }

}
