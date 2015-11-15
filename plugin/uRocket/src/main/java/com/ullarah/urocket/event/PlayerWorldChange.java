package com.ullarah.urocket.event;

import com.ullarah.ulib.function.GamemodeCheck;
import com.ullarah.ulib.function.TitleSubtitle;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import static com.ullarah.ulib.function.CommonString.messageSend;
import static com.ullarah.urocket.RocketFunctions.disableRocketBoots;
import static com.ullarah.urocket.RocketInit.*;
import static com.ullarah.urocket.RocketLanguage.RB_WORLD_CHANGE;

public class PlayerWorldChange implements Listener {

    @EventHandler
    public void worldChange(PlayerChangedWorldEvent event) {

        final Player player = event.getPlayer();

        if (GamemodeCheck.check(player, GameMode.SURVIVAL, GameMode.ADVENTURE)) {

            if (rocketPower.containsKey(player.getUniqueId())) {

                if (rocketUsage.contains(player.getUniqueId())) {
                    TitleSubtitle.subtitle(player, 5, RB_WORLD_CHANGE);
                    messageSend(getPlugin(), player, true, RB_WORLD_CHANGE);
                }

                disableRocketBoots(player, false, false, false, false, false, true);

            }

        }

    }

}
