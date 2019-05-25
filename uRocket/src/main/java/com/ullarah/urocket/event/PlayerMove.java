package com.ullarah.urocket.event;

import com.ullarah.urocket.RocketFunctions;
import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.data.FlyLockout;
import com.ullarah.urocket.data.RocketPlayer;
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

        RocketFunctions rocketFunctions = new RocketFunctions();
        CommonString commonString = new CommonString();
        TitleSubtitle titleSubtitle = new TitleSubtitle();
        AreaCheck areaCheck = new AreaCheck();
        GroundFire groundFire = new GroundFire();

        final Player player = event.getPlayer();
        final RocketPlayer rp = RocketInit.getPlayer(player);
        FlyLockout locks = rp.getLockouts();

        if (!new GamemodeCheck().check(player, GameMode.SURVIVAL, GameMode.ADVENTURE)) {
            return;
        }

        final Location location = player.getLocation();

        if (player.isFlying() && !rp.isUsingBoots() && rocketFunctions.isValidRocketBoots(player.getEquipment().getBoots())) {
            player.setFlying(false);
            return;
        }

        if (locks.getSprintLock() == FlyLockout.Sprint.AIR) {

            Material blockUnder = location.getBlock().getRelative(BlockFace.DOWN).getType();
            player.setFlySpeed(0.01f);
            if (blockUnder != Material.AIR)
                locks.setSprintLock(FlyLockout.Sprint.NONE);

        } else if (locks.getSprintLock() == FlyLockout.Sprint.LAND) {

            player.setWalkSpeed(0);
            player.setSprinting(false);

            Bukkit.getScheduler().runTaskLater(RocketInit.getPlugin(), () -> {

                player.setWalkSpeed(0.2f);
                locks.setSprintLock(FlyLockout.Sprint.NONE);

            }, 0);

        }

        World world = player.getWorld();
        if (rp.getBootData() != null) {

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

                String tank = new IDTag().create(bTank);
                String station = new IDTag().create(bStation);
                String stand = new IDTag().create(bStand);

                if (newStationList.contains(station) && newTankList.contains(tank) && !newStandList.contains(stand)) {
                    if (!RocketInit.rocketRepair.containsKey(player.getUniqueId())) {
                        titleSubtitle.subtitle(player, 3, RocketLanguage.RB_STATION_START);
                        commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_STATION_START);
                        player.getWorld().playSound(player.getEyeLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.8f, 0.5f);
                        RocketInit.rocketRepair.put(player.getUniqueId(), bStation);
                    }
                }

            } else RocketInit.rocketRepair.remove(player.getUniqueId());

            RocketVariant.Variant variant = rp.getBootData().getVariant();
            if (variant != RocketVariant.Variant.WATER) {

                final Block blockMiddle = player.getLocation().getBlock().getRelative(BlockFace.SELF);
                final Block blockUnder = player.getLocation().getBlock().getRelative(BlockFace.DOWN);

                if (blockMiddle.isLiquid()) {

                    if (!locks.isInWater()) {
                        locks.setInWater(true);
                        titleSubtitle.subtitle(player, 3, RocketLanguage.RB_WATER_WARNING);
                        commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_WATER_WARNING);
                    }

                    player.setFlying(false);

                } else {

                    if (variant == RocketVariant.Variant.GLOW
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

                    rp.setUsingBoots(true);
                    locks.setInWater(false);

                }

            }

            if (variant == RocketVariant.Variant.ORIGINAL)
                if (player.isFlying() && player.getWorld().getName().equals("world_nether"))
                    RocketInit.rocketFire.add(groundFire.setFire(player, "SINGLE", Material.NETHERRACK));

            if (world.getName().equals("world") && (world.hasStorm()))
                if (new Random().nextInt(500) == 1) {
                    if (!player.getInventory().getBoots().getType().equals(Material.LEATHER_BOOTS)) {
                        if (player.isFlying()) {
                            if (!areaCheck.above(location, Material.AIR)) {
                                world.strikeLightning(location);
                                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1.5f, 0.75f);
                                locks.setSprintLock(FlyLockout.Sprint.AIR);
                                commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_STRIKE);
                            }
                        }
                    }
                }

        }

    }

}
