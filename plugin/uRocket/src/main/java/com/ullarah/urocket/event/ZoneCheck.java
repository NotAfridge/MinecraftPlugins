package com.ullarah.urocket.event;

import com.ullarah.ulib.function.InsideCuboid;
import com.ullarah.ulib.function.TitleSubtitle;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.ullarah.urocket.RocketInit.*;

public class ZoneCheck implements Listener {

    @EventHandler
    public void playerMovement(PlayerMoveEvent event) {

        for (Map.Entry<UUID, HashMap<Location, Location>> rocketZone : rocketZoneLocations.entrySet())
            for (Map.Entry<Location, Location> rocketLocation : rocketZone.getValue().entrySet()) {

                Player player = event.getPlayer();
                Location location = player.getLocation();

                Location zoneStart = rocketLocation.getKey();
                Location zoneEnd = rocketLocation.getValue();

                if (InsideCuboid.check(location, zoneStart, zoneEnd)) {

                    if (!rocketZones.contains(player.getUniqueId())) {

                        if (!rocketZone.getKey().equals(player.getUniqueId())) {

                            if (rocketPower.containsKey(player.getUniqueId())) {

                                if (player.isFlying()) {

                                    if (!rocketSprint.containsKey(player.getUniqueId())) {

                                        rocketSprint.put(player.getUniqueId(), "AIR");
                                        rocketZones.add(player.getUniqueId());

                                        player.setFlySpeed(0.05f);

                                        TitleSubtitle.subtitle(player, 3, ChatColor.RED + "You have entered a No-Fly Zone!");

                                        player.sendMessage(new String[]{
                                                getMsgPrefix() + ChatColor.RED + "You have entered a No-Fly Zone!",
                                                getMsgPrefix() + ChatColor.RED + "Disabling Rocket Boots!"
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
