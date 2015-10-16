package com.ullarah.ujoinquit.event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static com.ullarah.ujoinquit.JoinQuitFunctions.getMessage;
import static com.ullarah.ujoinquit.JoinQuitFunctions.messageType.QUIT;
import static com.ullarah.ujoinquit.JoinQuitFunctions.replacePlayerString;
import static com.ullarah.ujoinquit.JoinQuitInit.playerQuitMessage;

public class PlayerQuit implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void playerQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        if (playerQuitMessage.containsKey(player.getUniqueId())) {

            String message = getMessage(player, QUIT);

            if (message.contains("{player}")) message = replacePlayerString(player, message, 0);
            if (message.contains("{l_player}")) message = replacePlayerString(player, message, 1);
            if (message.contains("{u_player}")) message = replacePlayerString(player, message, 2);

            event.setQuitMessage(" «« " + ChatColor.translateAlternateColorCodes('&', message));

        }

    }

}
