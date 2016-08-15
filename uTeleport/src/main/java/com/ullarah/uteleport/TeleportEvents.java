package com.ullarah.uteleport;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class TeleportEvents implements Listener {

    private final Plugin plugin;
    private final TeleportFunctions teleportFunctions;

    TeleportEvents(Plugin instance, TeleportFunctions functions) {
        plugin = instance;
        teleportFunctions = functions;
    }

    private Plugin getPlugin() {
        return plugin;
    }

    private TeleportFunctions getTeleportFunctions() {
        return teleportFunctions;
    }

    @EventHandler
    public void playerTeleport(final PlayerTeleportEvent event) {

        PlayerTeleportEvent.TeleportCause cause = event.getCause();

        List<PlayerTeleportEvent.TeleportCause> teleportCauses = new ArrayList<PlayerTeleportEvent.TeleportCause>() {{
            add(PlayerTeleportEvent.TeleportCause.COMMAND);
            add(PlayerTeleportEvent.TeleportCause.PLUGIN);
            add(PlayerTeleportEvent.TeleportCause.UNKNOWN);
        }};

        if (teleportCauses.contains(cause)) {

            UUID playerUUID = event.getPlayer().getUniqueId();

            if (!getTeleportFunctions().getHistoryBlock().contains(playerUUID)) {

                Location locationFrom = event.getFrom(), locationTo = event.getTo();

                if (!locationFrom.getWorld().equals(locationTo.getWorld())
                        || locationFrom.distance(locationTo) > getPlugin().getConfig().getDouble("distance")) {

                    if (getTeleportFunctions().getHistoryMap().containsKey(playerUUID)) {
                        getTeleportFunctions().addLocation(playerUUID, locationFrom,
                                getTeleportFunctions().getHistoryMap().get(playerUUID));
                        return;
                    }

                    getTeleportFunctions().addLocation(playerUUID, locationFrom);

                }

            }

        }

    }

}