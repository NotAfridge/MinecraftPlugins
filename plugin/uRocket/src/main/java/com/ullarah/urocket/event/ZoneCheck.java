package com.ullarah.urocket.event;

import com.ullarah.ulib.function.CommonString;
import com.ullarah.ulib.function.InsideCuboid;
import com.ullarah.ulib.function.TitleSubtitle;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.ullarah.urocket.RocketInit.*;
import static com.ullarah.urocket.RocketLanguage.RB_DISABLE;
import static com.ullarah.urocket.RocketLanguage.RB_FZ_ENTRY;

public class ZoneCheck implements Listener {

    @EventHandler
    public void playerMovement(PlayerMoveEvent event) {

        for (Map.Entry<UUID, ConcurrentHashMap<Location, Location>> rocketZone : rocketZoneLocations.entrySet())
            for (Map.Entry<Location, Location> rocketLocation : rocketZone.getValue().entrySet()) {

                Player player = event.getPlayer();
                Location location = player.getLocation();

                Location zoneStart = rocketLocation.getKey();
                Location zoneEnd = rocketLocation.getValue();

                if (new InsideCuboid().check(location, zoneStart, zoneEnd)) {

                    if (!rocketZones.contains(player.getUniqueId())) {

                        if (!rocketZone.getKey().equals(player.getUniqueId())) {

                            if (rocketPower.containsKey(player.getUniqueId())) {

                                if (player.isFlying()) {

                                    if (!rocketSprint.containsKey(player.getUniqueId())) {

                                        rocketSprint.put(player.getUniqueId(), "AIR");
                                        rocketZones.add(player.getUniqueId());

                                        player.setFlySpeed(0.05f);

                                        new TitleSubtitle().subtitle(player, 3, RB_FZ_ENTRY);

                                        new CommonString().messageSend(getPlugin(), player, true, new String[]{
                                                RB_FZ_ENTRY, RB_DISABLE
                                        });

                                        player.getWorld().playSound(player.getLocation(), Sound.FIZZ, 0.5f, 0.75f);

                                    }

                                }

                            }

                        }

                    }

                } else rocketZones.remove(player.getUniqueId());

            }

    }

}
