package com.ullarah.upostal.event;

import com.ullarah.upostal.command.Register;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinRegister implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void event(final PlayerJoinEvent event) {

        new Register().create(event.getPlayer());

    }

}
