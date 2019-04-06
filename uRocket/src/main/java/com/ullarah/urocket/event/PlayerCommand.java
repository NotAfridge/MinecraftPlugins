package com.ullarah.urocket.event;

import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.data.RocketPlayer;
import com.ullarah.urocket.function.CommonString;
import com.ullarah.urocket.init.RocketLanguage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.UUID;

public class PlayerCommand implements Listener {

    @EventHandler
    public void variantCommandStop(PlayerCommandPreprocessEvent event) {

        CommonString commonString = new CommonString();

        Player player = event.getPlayer();
        RocketPlayer rp = RocketInit.getPlayer(player);

        if (rp.isEffected()) {
            // Player is effected, guaranteed to be wearing boots
            switch (rp.getBootData().getVariant()) {

                case STEALTH:
                    commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_HIDDEN);
                    event.setCancelled(true);
                    break;

                default:
                    break;

            }

        }

    }

}
