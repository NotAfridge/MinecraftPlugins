package com.ullarah.utab;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

class TabEvents implements Listener {

    private final TabFunctions tabFunctions;

    TabEvents(TabFunctions functions) {
        tabFunctions = functions;
    }

    private TabFunctions getTabFunctions() {
        return tabFunctions;
    }

    @EventHandler
    public void playerJoin(final PlayerJoinEvent event) {

        getTabFunctions().sendHeaderFooter(
                event.getPlayer(), getTabFunctions().getCurrentHeader(), getTabFunctions().getCurrentFooter());

    }

}
