package com.ullarah.urocket.event;

import com.ullarah.urocket.function.CommonString;
import com.ullarah.urocket.function.GamemodeCheck;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import static com.ullarah.urocket.RocketInit.getPlugin;
import static com.ullarah.urocket.RocketInit.rocketVariant;
import static com.ullarah.urocket.init.RocketLanguage.RB_CONSUME_FLY;
import static com.ullarah.urocket.init.RocketLanguage.RB_CONSUME_HIDDEN;

public class PlayerConsume implements Listener {

    @EventHandler
    public void playerEating(PlayerItemConsumeEvent event) {

        CommonString commonString = new CommonString();
        GamemodeCheck gamemodeCheck = new GamemodeCheck();

        Player player = event.getPlayer();

        if (gamemodeCheck.check(player, GameMode.SURVIVAL, GameMode.ADVENTURE)) {

            if (player.isFlying()) {

                if (rocketVariant.containsKey(player.getUniqueId())) {

                    switch (rocketVariant.get(player.getUniqueId())) {

                        case HEALTH:
                            event.setCancelled(true);
                            commonString.messageSend(getPlugin(), player, true, RB_CONSUME_FLY);
                            break;

                        case STEALTH:
                            event.setCancelled(true);
                            commonString.messageSend(getPlugin(), player, true, RB_CONSUME_HIDDEN);
                            break;

                        default:
                            break;

                    }

                }

            }

        }

    }

}
