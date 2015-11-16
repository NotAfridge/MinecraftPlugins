package com.ullarah.urocket.event;

import com.ullarah.ulib.function.CommonString;
import com.ullarah.ulib.function.GamemodeCheck;
import com.ullarah.ulib.function.TitleSubtitle;
import com.ullarah.urocket.RocketInit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import static com.ullarah.urocket.RocketFunctions.disableRocketBoots;
import static com.ullarah.urocket.RocketInit.getPlugin;
import static com.ullarah.urocket.RocketInit.rocketUsage;
import static com.ullarah.urocket.RocketLanguage.RB_GAMEMODE_ERROR;

public class PlayerGamemodeChange implements Listener {

    @EventHandler
    public void gamemodeChange(PlayerGameModeChangeEvent event) {

        Player player = event.getPlayer();

        if (!rocketUsage.isEmpty())
            if (RocketInit.rocketUsage.contains(player.getUniqueId())) {

                if (new GamemodeCheck().check(player, GameMode.CREATIVE, GameMode.SPECTATOR)) {

                    new CommonString().messageSend(getPlugin(), player, true, RB_GAMEMODE_ERROR);
                    new TitleSubtitle().subtitle(player, 1, RB_GAMEMODE_ERROR);

                    disableRocketBoots(player, false, false, false, false, false, true);

                }

            }

    }

}
