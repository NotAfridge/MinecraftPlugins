package com.ullarah.ujoinquit.event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static com.ullarah.ujoinquit.JoinQuitFunctions.getMessage;
import static com.ullarah.ujoinquit.JoinQuitFunctions.messageType.QUIT;
import static com.ullarah.ujoinquit.JoinQuitInit.playerQuitMessage;

public class PlayerQuit implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void playerQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        if (playerQuitMessage.containsKey(player.getUniqueId()))
            event.setQuitMessage(" ◀◀ " + ChatColor.translateAlternateColorCodes('&',
                    getMessage(player, QUIT).replaceAll("\\{player\\}", player.getPlayerListName())));

    }

}
