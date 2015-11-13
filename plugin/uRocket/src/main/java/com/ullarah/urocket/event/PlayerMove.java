package com.ullarah.urocket.event;

import com.ullarah.ulib.function.GamemodeCheck;
import com.ullarah.ulib.function.GroundFire;
import com.ullarah.ulib.function.TitleSubtitle;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.ullarah.ulib.function.CommonString.messageSend;
import static com.ullarah.urocket.RocketInit.*;
import static com.ullarah.urocket.RocketLanguage.RB_STATION_START;
import static com.ullarah.urocket.RocketLanguage.RB_WATER_WARNING;
import static com.ullarah.urocket.RocketVariant.Variant;

public class PlayerMove implements Listener {

    @EventHandler
    public void playerMovement(PlayerMoveEvent event) {

        final Player player = event.getPlayer();

        if (GamemodeCheck.check(player, GameMode.SURVIVAL, GameMode.ADVENTURE)) {

            final Location location = player.getLocation();
            World world = player.getWorld();

            if (rocketSprint.containsKey(player.getUniqueId())) {

                if (rocketSprint.get(player.getUniqueId()).equals("AIR")) {

                    Material blockUnder = location.getBlock().getRelative(BlockFace.DOWN).getType();
                    player.setFlySpeed(0.01f);
                    if (blockUnder != Material.AIR) rocketSprint.remove(player.getUniqueId());

                } else if (rocketSprint.get(player.getUniqueId()).equals("LAND")) {

                    player.setWalkSpeed(0);
                    player.setSprinting(false);

                    Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {

                        player.setWalkSpeed(0.2f);
                        rocketSprint.remove(player.getUniqueId());

                    }, 0);

                }

            }

            if (rocketPower.containsKey(player.getUniqueId())) {

                Location bTank = new Location(player.getWorld(), location.getX(), location.getY() - 2, location.getZ());
                Location bStation = new Location(player.getWorld(), location.getX(), location.getY() - 1, location.getZ());
                Location bStand = new Location(player.getWorld(), location.getX(), location.getY(), location.getZ());

                if (bTank.getBlock().getType() == Material.BURNING_FURNACE && bStation.getBlock().getType() == Material.BEACON) {

                    List<String> tankList = getPlugin().getConfig().getStringList("tanks");
                    List<String> stationList = getPlugin().getConfig().getStringList("stations");
                    List<String> standList = getPlugin().getConfig().getStringList("stands");

                    List<String> newTankList = new ArrayList<>();
                    List<String> newStationList = new ArrayList<>();
                    List<String> newStandList = new ArrayList<>();

                    newTankList.addAll(tankList.stream().map(tank -> tank.replaceFirst(".{37}", "")).collect(Collectors.toList()));
                    newStationList.addAll(stationList.stream().map(station -> station.replaceFirst(".{37}", "")).collect(Collectors.toList()));
                    newStandList.addAll(standList.stream().map(stand -> stand.replaceFirst(".{37}", "")).collect(Collectors.toList()));

                    String tank = world.getName() + "|" + bTank.getBlockX() + "|" + bTank.getBlockY() + "|" + bTank.getBlockZ();
                    String station = world.getName() + "|" + bStation.getBlockX() + "|" + bStation.getBlockY() + "|" + bStation.getBlockZ();
                    String stand = world.getName() + "|" + bStand.getBlockX() + "|" + bStand.getBlockY() + "|" + bStand.getBlockZ();

                    if (newStationList.contains(station) && newTankList.contains(tank) && !newStandList.contains(stand))
                        if (!rocketRepair.containsKey(player.getUniqueId())) {
                            TitleSubtitle.subtitle(player, 3, RB_STATION_START);
                            messageSend(getPlugin(), player, true, RB_STATION_START);
                            player.getWorld().playSound(player.getEyeLocation(), Sound.ORB_PICKUP, 0.8f, 0.5f);
                            rocketRepair.put(player.getUniqueId(), bStation);
                        }

                } else rocketRepair.remove(player.getUniqueId());

                if (rocketVariant.containsKey(player.getUniqueId())) {

                    if (rocketVariant.get(player.getUniqueId()) != Variant.WATER) {

                        final Block blockMiddle = player.getLocation().getBlock().getRelative(BlockFace.SELF);
                        final Block blockUnder = player.getLocation().getBlock().getRelative(BlockFace.DOWN);

                        if (blockMiddle.isLiquid()) {

                            if (!rocketWater.contains(player.getUniqueId())) {
                                TitleSubtitle.subtitle(player, 3, RB_WATER_WARNING);
                                messageSend(getPlugin(), player, true, RB_WATER_WARNING);
                            }

                            rocketWater.add(player.getUniqueId());
                            player.setFlying(false);

                        } else {

                            if (rocketVariant.get(player.getUniqueId()) == Variant.GLOW
                                    && player.isFlying() && !player.isSneaking()
                                    && blockUnder.getType() == Material.AIR
                                    && blockMiddle.getType() == Material.AIR) {

                                if (!rocketGlow.containsKey(blockUnder.getLocation())) {

                                    rocketGlow.put(blockUnder.getLocation(), blockUnder.getType());
                                    blockUnder.setType(Material.GLOWSTONE);

                                }

                                Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(getPlugin(),
                                        () -> Bukkit.getServer().getScheduler().runTask(getPlugin(), () -> {

                                            blockUnder.setType(rocketGlow.get(blockUnder.getLocation()));
                                            rocketGlow.remove(blockUnder.getLocation());

                                        }), 20);

                            }

                            rocketUsage.add(player.getUniqueId());
                            rocketWater.remove(player.getUniqueId());

                        }

                    }

                    if (rocketVariant.get(player.getUniqueId()) == Variant.ORIGINAL)
                        if (player.isFlying() && player.getWorld().getName().equals("world_nether"))
                            GroundFire.setFire(player, "SINGLE", Material.NETHERRACK);

                    if (world.getName().equals("world") && (world.hasStorm() || world.isThundering()))
                        if (new Random().nextInt(10) == 5) {
                            world.strikeLightning(location);
                            player.getWorld().playSound(player.getLocation(), Sound.FIREWORK_BLAST, 1.5f, 0.75f);
                            rocketSprint.put(player.getUniqueId(), "AIR");
                        }

                }

            }

        }

    }

}
