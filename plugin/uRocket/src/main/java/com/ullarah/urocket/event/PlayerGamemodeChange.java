package com.ullarah.urocket.event;

import com.ullarah.ulib.function.TitleSubtitle;
import com.ullarah.urocket.RocketInit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import static com.ullarah.urocket.RocketFunctions.disableRocketBoots;
import static com.ullarah.urocket.RocketInit.getMsgPrefix;
import static com.ullarah.urocket.RocketInit.rocketUsage;

public class PlayerGamemodeChange implements Listener {

    @EventHandler
    public void gamemodeChange(PlayerGameModeChangeEvent event) {

        GameMode gamemode = event.getNewGameMode();
        Player player = event.getPlayer();

        if (!rocketUsage.isEmpty())
            if (RocketInit.rocketUsage.contains(player.getUniqueId())) {

                if (gamemode.equals(GameMode.CREATIVE) || gamemode.equals(GameMode.SPECTATOR)) {

                    player.sendMessage(getMsgPrefix() + "Rocket Boots do not work in this gamemode!");
                    TitleSubtitle.subtitle(player, 1, ChatColor.RED + "Wrong Gamemode! Rocket Boots Disabled!");

                    disableRocketBoots(player, false, false, false, false, false);

                }

            }

    }

}
