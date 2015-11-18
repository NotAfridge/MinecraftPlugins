package com.ullarah.urocket.event;

import com.ullarah.ulib.function.CommonString;
import com.ullarah.ulib.function.GamemodeCheck;
import com.ullarah.ulib.function.TitleSubtitle;
import com.ullarah.urocket.RocketFunctions;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import static com.ullarah.urocket.RocketInit.*;
import static com.ullarah.urocket.RocketLanguage.RB_WORLD_CHANGE;

public class PlayerWorldChange implements Listener {

    @EventHandler
    public void worldChange(PlayerChangedWorldEvent event) {

        RocketFunctions rocketFunctions = new RocketFunctions();
        CommonString commonString = new CommonString();
        GamemodeCheck gamemodeCheck = new GamemodeCheck();
        TitleSubtitle titleSubtitle = new TitleSubtitle();

        final Player player = event.getPlayer();

        if (gamemodeCheck.check(player, GameMode.SURVIVAL, GameMode.ADVENTURE)) {

            if (rocketPower.containsKey(player.getUniqueId())) {

                if (rocketUsage.contains(player.getUniqueId())) {
                    titleSubtitle.subtitle(player, 5, RB_WORLD_CHANGE);
                    commonString.messageSend(getPlugin(), player, true, RB_WORLD_CHANGE);
                }

                rocketFunctions.disableRocketBoots(player, false, false, false, false, false, true);

            }

        }

    }

}
