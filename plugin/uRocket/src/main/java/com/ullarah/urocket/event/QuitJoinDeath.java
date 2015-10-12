package com.ullarah.urocket.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static com.ullarah.urocket.RocketFunctions.disableRocketBoots;

public class QuitJoinDeath implements Listener {

    @EventHandler
    public void rocketRemoveOnQuit(PlayerQuitEvent event) {
        disableRocketBoots(event.getPlayer(), false, false, false, false, false, false);
    }

    @EventHandler
    public void rocketRemoveOnJoin(PlayerJoinEvent event) {
        disableRocketBoots(event.getPlayer(), false, false, false, false, false, false);
    }

    @EventHandler
    public void rocketRemoveOnDeath(PlayerDeathEvent event) {
        disableRocketBoots(event.getEntity(), false, false, false, false, false, false);
    }

}
