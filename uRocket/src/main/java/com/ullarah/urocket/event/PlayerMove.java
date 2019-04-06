package com.ullarah.urocket.event;

import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.data.SprintLockout;
import com.ullarah.urocket.function.*;
import com.ullarah.urocket.init.RocketLanguage;
import com.ullarah.urocket.init.RocketVariant;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class PlayerMove implements Listener {

    @EventHandler
    public void playerMovement(PlayerMoveEvent event) {

        CommonString commonString = new CommonString();
        TitleSubtitle titleSubtitle = new TitleSubtitle();
        AreaCheck areaCheck = new AreaCheck();
        GroundFire groundFire = new GroundFire();

        final Player player = event.getPlayer();

        if (new GamemodeCheck().check(player, GameMode.SURVIVAL, GameMode.ADVENTURE)) {

            final Location location = player.getLocation();
            World world = player.getWorld();

            if (RocketInit.rocketSprint.containsKey(player.getUniqueId())) {

                if (RocketInit.rocketSprint.get(player.getUniqueId()) == SprintLockout.AIR) {

                    Material blockUnder = location.getBlock().getRelative(BlockFace.DOWN).getType();
                    player.setFlySpeed(0.01f);
                    if (blockUnder != Material.AIR) RocketInit.rocketSprint.remove(player.getUniqueId());

                } else if (RocketInit.rocketSprint.get(player.getUniqueId()) == SprintLockout.LAND) {

                    player.setWalkSpeed(0);
                    player.setSprinting(false);

                    Bukkit.getScheduler().runTaskLater(RocketInit.getPlugin(), () -> {

                        player.setWalkSpeed(0.2f);
                        RocketInit.rocketSprint.remove(player.getUniqueId());

                    }, 0);

                }

            }

            if (RocketInit.rocketPower.containsKey(player.getUniqueId())) {

                Location bTank = new Location(player.getWorld(), location.getX(), location.getY() - 2, location.getZ());
                Location bStation = new Location(player.getWorld(), location.getX(), location.getY() - 1, location.getZ());
                Location bStand = new Location(player.getWorld(), location.getX(), location.getY(), location.getZ());

                BlockState tankBlock = bTank.getBlock().getState();
                Material stationMaterial = bStation.getBlock().getType();
                if (tankBlock instanceof Furnace && ((Furnace) tankBlock).getBurnTime() > 0 && stationMaterial.equals(Material.BEACON)) {

                    List<String> tankList = RocketInit.getPlugin().getConfig().getStringList("tanks");
                    List<String> stationList = RocketInit.getPlugin().getConfig().getStringList("stations");
                    List<String> standList = RocketInit.getPlugin().getConfig().getStringList("stands");

                    List<String> newTankList = new ArrayList<>();
                    List<String> newStationList = new ArrayList<>();
                    List<String> newStandList = new ArrayList<>();

                    newTankList.addAll(tankList.stream().map(tank -> tank.replaceFirst(".{37}", "")).collect(Collectors.toList()));
                    newStationList.addAll(stationList.stream().map(station -> station.replaceFirst(".{37}", "")).collect(Collectors.toList()));
                    newStandList.addAll(standList.stream().map(stand -> stand.replaceFirst(".{37}", "")).collect(Collectors.toList()));

                    String tank = world.getName() + "|" + bTank.getBlockX() + "|" + bTank.getBlockY() + "|" + bTank.getBlockZ();
                    String station = world.getName() + "|" + bStation.getBlockX() + "|" + bStation.getBlockY() + "|" + bStation.getBlockZ();
                    String stand = world.getName() + "|" + bStand.getBlockX() + "|" + bStand.getBlockY() + "|" + bStand.getBlockZ();

                    if (newStationList.contains(station) && newTankList.contains(tank) && !newStandList.contains(stand)) {
                        if (!RocketInit.rocketRepair.containsKey(player.getUniqueId())) {
                            titleSubtitle.subtitle(player, 3, RocketLanguage.RB_STATION_START);
                            commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_STATION_START);
                            player.getWorld().playSound(player.getEyeLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.8f, 0.5f);
                            RocketInit.rocketRepair.put(player.getUniqueId(), bStation);
                        }
                    }

                } else RocketInit.rocketRepair.remove(player.getUniqueId());

                if (RocketInit.rocketVariant.containsKey(player.getUniqueId())) {

                    if (RocketInit.rocketVariant.get(player.getUniqueId()) != RocketVariant.Variant.WATER) {

                        final Block blockMiddle = player.getLocation().getBlock().getRelative(BlockFace.SELF);
                        final Block blockUnder = player.getLocation().getBlock().getRelative(BlockFace.DOWN);

                        if (blockMiddle.isLiquid()) {

                            if (!RocketInit.rocketWater.contains(player.getUniqueId())) {
                                titleSubtitle.subtitle(player, 3, RocketLanguage.RB_WATER_WARNING);
                                commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_WATER_WARNING);
                            }

                            RocketInit.rocketWater.add(player.getUniqueId());
                            player.setFlying(false);

                        } else {

                            if (RocketInit.rocketVariant.get(player.getUniqueId()).equals(RocketVariant.Variant.GLOW)
                                    && player.isFlying() && !player.isSneaking()
                                    && blockUnder.getType().equals(Material.AIR)
                                    && blockMiddle.getType().equals(Material.AIR)) {

                                if (!RocketInit.rocketGlow.containsKey(blockUnder.getLocation())) {

                                    RocketInit.rocketGlow.put(blockUnder.getLocation(), blockUnder.getType());
                                    blockUnder.setType(Material.GLOWSTONE);

                                }

                                Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(RocketInit.getPlugin(),
                                        () -> Bukkit.getServer().getScheduler().runTask(RocketInit.getPlugin(), () -> {

                                            blockUnder.setType(RocketInit.rocketGlow.get(blockUnder.getLocation()));
                                            RocketInit.rocketGlow.remove(blockUnder.getLocation());

                                        }), 20);

                            }

                            RocketInit.rocketUsage.add(player.getUniqueId());
                            RocketInit.rocketWater.remove(player.getUniqueId());

                        }

                    }

                    if (RocketInit.rocketVariant.get(player.getUniqueId()).equals(RocketVariant.Variant.ORIGINAL))
                        if (player.isFlying() && player.getWorld().getName().equals("world_nether"))
                            RocketInit.rocketFire.add(groundFire.setFire(player, "SINGLE", Material.NETHERRACK));

                    if (world.getName().equals("world") && (world.hasStorm()))
                        if (new Random().nextInt(500) == 1) {
                            if (!player.getInventory().getBoots().getType().equals(Material.LEATHER_BOOTS)) {
                                if (player.isFlying()) {
                                    if (!areaCheck.above(location, Material.AIR)) {
                                        world.strikeLightning(location);
                                        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1.5f, 0.75f);
                                        RocketInit.rocketSprint.put(player.getUniqueId(), SprintLockout.AIR);
                                        commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_STRIKE);
                                    }
                                }
                            }
                        }

                }

            }

        }

    }

}
