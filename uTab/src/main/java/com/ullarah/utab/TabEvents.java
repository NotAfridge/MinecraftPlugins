package com.ullarah.utab;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

class TabEvents implements Listener {

    @SuppressWarnings("UnusedDeclaration")
    @EventHandler
    public void playerJoin(final PlayerJoinEvent event) {

        TabFunctions tabFunctions = new TabFunctions();

        tabFunctions.sendHeaderFooter(event.getPlayer(), tabFunctions.getCurrentHeader(), tabFunctions.getCurrentFooter());

    }

}
