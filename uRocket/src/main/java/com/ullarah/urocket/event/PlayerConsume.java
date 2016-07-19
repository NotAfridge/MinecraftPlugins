package com.ullarah.urocket.event;

import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.function.CommonString;
import com.ullarah.urocket.function.GamemodeCheck;
import com.ullarah.urocket.init.RocketLanguage;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class PlayerConsume implements Listener {

    @EventHandler
    public void playerEating(PlayerItemConsumeEvent event) {

        CommonString commonString = new CommonString();
        GamemodeCheck gamemodeCheck = new GamemodeCheck();

        Player player = event.getPlayer();

        if (gamemodeCheck.check(player, GameMode.SURVIVAL, GameMode.ADVENTURE)) {

            if (player.isFlying()) {

                if (RocketInit.rocketVariant.containsKey(player.getUniqueId())) {

                    switch (RocketInit.rocketVariant.get(player.getUniqueId())) {

                        case HEALTH:
                            event.setCancelled(true);
                            commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_CONSUME_FLY);
                            break;

                        case STEALTH:
                            event.setCancelled(true);
                            commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_CONSUME_HIDDEN);
                            break;

                        default:
                            break;

                    }

                }

            }

        }

    }

}
