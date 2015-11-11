package com.ullarah.uteleport;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.ullarah.uteleport.TeleportInit.*;
import static org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import static org.bukkit.event.player.PlayerTeleportEvent.TeleportCause.COMMAND;
import static org.bukkit.event.player.PlayerTeleportEvent.TeleportCause.PLUGIN;

public class TeleportEvents implements Listener {

    @EventHandler
    public void playerTeleport(final PlayerTeleportEvent event) {

        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        TeleportCause cause = event.getCause();
        Location locationFrom = event.getFrom();

        if (cause.equals(COMMAND) || cause.equals(PLUGIN)) {

            if (!historyBlock.contains(playerUUID)) {

                if (historyMap.containsKey(playerUUID)) {

                    ConcurrentHashMap<ArrayList<Location>, ArrayList<Date>> locationMap = historyMap.get(playerUUID);

                    ArrayList<Location> locations = new ArrayList<>();
                    ArrayList<Date> dates = new ArrayList<>();

                    for (Map.Entry<ArrayList<Location>, ArrayList<Date>> entry : locationMap.entrySet()) {

                        ArrayList<Location> historyLocation = entry.getKey();
                        ArrayList<Date> historyDate = entry.getValue();

                        if (historyLocation.size() >= getPlugin().getConfig().getInt("history")) {
                            historyLocation.remove(0);
                            historyDate.remove(0);
                        }

                        locations = historyLocation;
                        dates = historyDate;

                    }

                    double posX = (int) locationFrom.getX();
                    double posY = (int) locationFrom.getY();
                    double posZ = (int) locationFrom.getZ();

                    locations.add(new Location(locationFrom.getWorld(), posX, posY, posZ, 0, 0));
                    dates.add(Calendar.getInstance().getTime());

                    locationMap.put(locations, dates);

                    historyMap.put(playerUUID, locationMap);

                } else {

                    double posX = (int) locationFrom.getX();
                    double posY = (int) locationFrom.getY();
                    double posZ = (int) locationFrom.getZ();

                    historyMap.put(playerUUID, new ConcurrentHashMap<ArrayList<Location>, ArrayList<Date>>() {{
                        put(new ArrayList<Location>() {{
                            add(new Location(locationFrom.getWorld(), posX, posY, posZ, 0, 0));
                        }}, new ArrayList<Date>() {{
                            add(Calendar.getInstance().getTime());
                        }});
                    }});

                }

            }

        }

    }

}