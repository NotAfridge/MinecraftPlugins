package com.ullarah.uchest.event;

import com.ullarah.uchest.command.ChestCreation;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static com.ullarah.uchest.ChestFunctions.validStorage.HOLD;
import static com.ullarah.uchest.ChestFunctions.validStorage.VAULT;

public class PlayerJoin implements Listener {

    @EventHandler
    public void event(final PlayerJoinEvent event) {

        ChestCreation.create(event.getPlayer(), HOLD, false);
        ChestCreation.create(event.getPlayer(), VAULT, false);

    }

}
