package com.ullarah.urocket.event;

import com.ullarah.urocket.RocketFunctions;
import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.function.CommonString;
import com.ullarah.urocket.function.GamemodeCheck;
import com.ullarah.urocket.function.TitleSubtitle;
import com.ullarah.urocket.init.RocketLanguage;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class PlayerGamemodeChange implements Listener {

    @EventHandler
    public void gamemodeChange(PlayerGameModeChangeEvent event) {

        RocketFunctions rocketFunctions = new RocketFunctions();
        CommonString commonString = new CommonString();
        GamemodeCheck gamemodeCheck = new GamemodeCheck();
        TitleSubtitle titleSubtitle = new TitleSubtitle();

        Player player = event.getPlayer();

        if (!RocketInit.rocketUsage.isEmpty())
            if (RocketInit.rocketUsage.contains(player.getUniqueId()))
                if (gamemodeCheck.check(player, GameMode.CREATIVE, GameMode.SPECTATOR)) {

                    commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_GAMEMODE_ERROR);
                    titleSubtitle.subtitle(player, 1, RocketLanguage.RB_GAMEMODE_ERROR);

                    rocketFunctions.disableRocketBoots(player, false, false, false, false, false);

                }

    }

}
