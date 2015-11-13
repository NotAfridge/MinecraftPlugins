package com.ullarah.urocket.event;

import com.ullarah.ulib.function.GamemodeCheck;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import static com.ullarah.ulib.function.CommonString.messageSend;
import static com.ullarah.urocket.RocketInit.getPlugin;
import static com.ullarah.urocket.RocketInit.rocketVariant;
import static com.ullarah.urocket.RocketLanguage.RB_CONSUME_FLY;
import static com.ullarah.urocket.RocketLanguage.RB_CONSUME_HIDDEN;

public class PlayerConsume implements Listener {

    @EventHandler
    public void playerEating(PlayerItemConsumeEvent event) {

        Player player = event.getPlayer();

        if (GamemodeCheck.check(player, GameMode.SURVIVAL, GameMode.ADVENTURE)) {

            if (player.isFlying()) {

                if (rocketVariant.containsKey(player.getUniqueId())) {

                    switch (rocketVariant.get(player.getUniqueId())) {

                        case HEALTH:
                            event.setCancelled(true);
                            messageSend(getPlugin(), player, true, RB_CONSUME_FLY);
                            break;

                        case STEALTH:
                            event.setCancelled(true);
                            messageSend(getPlugin(), player, true, RB_CONSUME_HIDDEN);
                            break;

                        default:
                            break;

                    }

                }

            }

        }

    }

}
