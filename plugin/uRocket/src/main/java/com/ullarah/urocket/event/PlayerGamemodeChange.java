package com.ullarah.urocket.event;

import com.ullarah.ulib.function.CommonString;
import com.ullarah.ulib.function.GamemodeCheck;
import com.ullarah.ulib.function.TitleSubtitle;
import com.ullarah.urocket.RocketFunctions;
import com.ullarah.urocket.RocketInit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import static com.ullarah.urocket.RocketInit.getPlugin;
import static com.ullarah.urocket.RocketInit.rocketUsage;
import static com.ullarah.urocket.RocketLanguage.RB_GAMEMODE_ERROR;

public class PlayerGamemodeChange implements Listener {

    @EventHandler
    public void gamemodeChange(PlayerGameModeChangeEvent event) {

        RocketFunctions rocketFunctions = new RocketFunctions();
        CommonString commonString = new CommonString();
        GamemodeCheck gamemodeCheck = new GamemodeCheck();
        TitleSubtitle titleSubtitle = new TitleSubtitle();

        Player player = event.getPlayer();

        if (!rocketUsage.isEmpty())
            if (RocketInit.rocketUsage.contains(player.getUniqueId()))
                if (gamemodeCheck.check(player, GameMode.CREATIVE, GameMode.SPECTATOR)) {

                    commonString.messageSend(getPlugin(), player, true, RB_GAMEMODE_ERROR);
                    titleSubtitle.subtitle(player, 1, RB_GAMEMODE_ERROR);

                    rocketFunctions.disableRocketBoots(player, false, false, false, false, false, true);

                }

    }

}
