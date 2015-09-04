package com.ullarah.urocket.event;

import com.ullarah.ulib.function.GamemodeCheck;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import static com.ullarah.urocket.RocketFunctions.Variant;
import static com.ullarah.urocket.RocketInit.getMsgPrefix;
import static com.ullarah.urocket.RocketInit.rocketVariant;

public class PlayerConsume implements Listener {

    @EventHandler
    public void playerEating(PlayerItemConsumeEvent event) {

        Player player = event.getPlayer();

        if (GamemodeCheck.check(player, GameMode.SURVIVAL, GameMode.ADVENTURE)) {

            if (player.isFlying()) {

                if (rocketVariant.containsKey(player.getUniqueId())) {

                    Variant bootVariant = rocketVariant.get(player.getUniqueId());

                    if (bootVariant != null) {

                        switch (bootVariant) {

                            case HEALTH:
                                event.setCancelled(true);
                                player.sendMessage(getMsgPrefix() + "You cannot eat and fly at the same time!");
                                break;

                            default:
                                break;

                        }

                    }

                }

            }

        }

    }

}
