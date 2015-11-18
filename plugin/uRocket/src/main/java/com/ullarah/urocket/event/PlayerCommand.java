package com.ullarah.urocket.event;

import com.ullarah.ulib.function.CommonString;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.UUID;

import static com.ullarah.urocket.RocketInit.*;
import static com.ullarah.urocket.RocketLanguage.RB_HIDDEN;

public class PlayerCommand implements Listener {

    @EventHandler
    public void variantCommandStop(PlayerCommandPreprocessEvent event) {

        CommonString commonString = new CommonString();

        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (rocketEffects.contains(playerUUID)) {

            if (rocketVariant.containsKey(playerUUID)) {

                switch (rocketVariant.get(playerUUID)) {

                    case STEALTH:
                        commonString.messageSend(getPlugin(), player, true, RB_HIDDEN);
                        event.setCancelled(true);
                        break;

                    default:
                        break;

                }

            }

        }

    }

}
