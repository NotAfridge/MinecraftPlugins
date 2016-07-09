package com.ullarah.uchest.event;

import com.ullarah.uchest.ChestFunctions;
import com.ullarah.uchest.command.ChestCreation;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    @EventHandler
    public void event(final PlayerJoinEvent event) {

        ChestCreation chestCreation = new ChestCreation();

        chestCreation.create(event.getPlayer(), ChestFunctions.ValidChest.HOLD, false);
        chestCreation.create(event.getPlayer(), ChestFunctions.ValidChest.VAULT, false);

    }

}
