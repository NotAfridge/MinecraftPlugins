package com.ullarah.uchest.event;

import com.ullarah.uchest.command.ChestCreation;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static com.ullarah.uchest.ChestFunctions.ValidChest.HOLD;
import static com.ullarah.uchest.ChestFunctions.ValidChest.VAULT;

public class PlayerJoin implements Listener {

    @EventHandler
    public void event(final PlayerJoinEvent event) {

        ChestCreation chestCreation = new ChestCreation();

        chestCreation.create(event.getPlayer(), HOLD, false);
        chestCreation.create(event.getPlayer(), VAULT, false);

    }

}
