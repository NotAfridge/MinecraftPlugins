package com.ullarah.utab;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static com.ullarah.utab.TabFunctions.*;

class TabEvents implements Listener {

    @SuppressWarnings("UnusedDeclaration")
    @EventHandler
    public void playerJoin(final PlayerJoinEvent event) {

        sendHeaderFooter(event.getPlayer(), getCurrentHeader(), getCurrentFooter());

    }

}
