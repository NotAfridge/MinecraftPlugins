package com.ullarah.urocket.event;

import com.ullarah.urocket.RocketInit;
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
        UUID playerUUID = player.getUniqueId();

        if (RocketInit.rocketEffects.contains(playerUUID)) {

            if (RocketInit.rocketVariant.containsKey(playerUUID)) {

                switch (RocketInit.rocketVariant.get(playerUUID)) {

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

}
