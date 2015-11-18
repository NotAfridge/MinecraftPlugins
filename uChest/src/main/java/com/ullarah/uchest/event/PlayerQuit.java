package com.ullarah.uchest.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static com.ullarah.uchest.ChestInit.chestSwapBusy;
import static com.ullarah.uchest.ChestInit.chestSwapPlayer;

public class PlayerQuit implements Listener {

    @EventHandler
    public void event(final PlayerQuitEvent event) {

        if (chestSwapPlayer == event.getPlayer()) {
            chestSwapPlayer = null;
            chestSwapBusy = false;
        }

    }

}
