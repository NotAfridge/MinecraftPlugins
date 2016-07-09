package com.ullarah.uchest.event;

import com.ullarah.uchest.ChestInit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    @EventHandler
    public void event(final PlayerQuitEvent event) {

        if (ChestInit.chestSwapPlayer == event.getPlayer()) {
            ChestInit.chestSwapPlayer = null;
            ChestInit.chestSwapBusy = false;
        }

    }

}
