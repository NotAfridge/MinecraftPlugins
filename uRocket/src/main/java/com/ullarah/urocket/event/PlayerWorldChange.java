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
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class PlayerWorldChange implements Listener {

    @EventHandler
    public void worldChange(PlayerChangedWorldEvent event) {

        RocketFunctions rocketFunctions = new RocketFunctions();
        CommonString commonString = new CommonString();
        GamemodeCheck gamemodeCheck = new GamemodeCheck();
        TitleSubtitle titleSubtitle = new TitleSubtitle();

        final Player player = event.getPlayer();

        if (gamemodeCheck.check(player, GameMode.SURVIVAL, GameMode.ADVENTURE)) {

            if (RocketInit.rocketPower.containsKey(player.getUniqueId())) {

                if (RocketInit.rocketUsage.contains(player.getUniqueId())) {
                    titleSubtitle.subtitle(player, 5, RocketLanguage.RB_WORLD_CHANGE);
                    commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_WORLD_CHANGE);
                }

                rocketFunctions.disableRocketBoots(player, false, false, false, false, false);

            }

        }

    }

}
