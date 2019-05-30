package com.ullarah.urocket.event;

import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.data.FlyLockout;
import com.ullarah.urocket.data.RocketPlayer;
import com.ullarah.urocket.function.AreaCheck;
import com.ullarah.urocket.function.CommonString;
import com.ullarah.urocket.function.TitleSubtitle;
import com.ullarah.urocket.init.RocketLanguage;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ZoneCheck implements Listener {

    @EventHandler
    public void playerMovement(PlayerMoveEvent event) {

        CommonString commonString = new CommonString();
        TitleSubtitle titleSubtitle = new TitleSubtitle();
        AreaCheck areaCheck = new AreaCheck();

        for (Map.Entry<UUID, ConcurrentHashMap<Location, Location>> rocketZone : RocketInit.rocketZoneLocations.entrySet())
            for (Map.Entry<Location, Location> rocketLocation : rocketZone.getValue().entrySet()) {

                Player player = event.getPlayer();
                RocketPlayer rp = RocketInit.getPlayer(player);
                FlyLockout locks = rp.getLockouts();

                Location location = player.getLocation();

                Location zoneStart = rocketLocation.getKey();
                Location zoneEnd = rocketLocation.getValue();

                // If they're not in a no-fly zone
                if (!areaCheck.cuboid(location, zoneStart, zoneEnd)) {
                    locks.setInNoFlyZone(false);
                }
                else {
                    // If they're not already flagged
                    if (!locks.isInNoFlyZone()) {
                        // If they're not the zone owner
                        if (!rocketZone.getKey().equals(player.getUniqueId())) {
                            // Only players using rocket boots
                            if (rp.getBootData() != null) {

                                if (player.isFlying()) {

                                    if (locks.getSprintLock() == FlyLockout.Sprint.NONE) {

                                        locks.setSprintLock(FlyLockout.Sprint.AIR);
                                        locks.setInNoFlyZone(true);

                                        player.setFlySpeed(0.05f);

                                        titleSubtitle.subtitle(player, 3, RocketLanguage.RB_FZ_ENTRY);

                                        commonString.messageSend(RocketInit.getPlugin(), player, true, new String[]{
                                                RocketLanguage.RB_FZ_ENTRY, RocketLanguage.RB_DISABLE
                                        });

                                        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 0.5f, 0.75f);

                                    }

                                }

                            }

                        }

                    }

                }

            }

    }

}
