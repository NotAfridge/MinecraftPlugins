package com.ullarah.urocket.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import static com.ullarah.urocket.RocketInit.*;

public class PlayerCommand implements Listener {

    @EventHandler
    public void variantCommandStop(PlayerCommandPreprocessEvent event) {

        Player player = event.getPlayer();

        if (rocketEffects.contains(player.getUniqueId())) {

            if (rocketVariant.containsKey(player.getUniqueId())) {

                switch (rocketVariant.get(player.getUniqueId())) {

                    case STEALTH:
                        player.sendMessage(getMsgPrefix() + "You are currently hidden!");
                        event.setCancelled(true);
                        break;

                    default:
                        break;

                }

            }

        }

    }

}
