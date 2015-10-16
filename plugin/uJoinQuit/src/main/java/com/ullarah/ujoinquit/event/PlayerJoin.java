package com.ullarah.ujoinquit.event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

import static com.ullarah.ujoinquit.JoinQuitFunctions.getMessage;
import static com.ullarah.ujoinquit.JoinQuitFunctions.messageType.JOIN;
import static com.ullarah.ujoinquit.JoinQuitFunctions.replacePlayerString;
import static com.ullarah.ujoinquit.JoinQuitInit.playerJoinLocation;
import static com.ullarah.ujoinquit.JoinQuitInit.playerJoinMessage;

public class PlayerJoin implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void event(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (playerJoinMessage.containsKey(playerUUID)) {

            String message = getMessage(player, JOIN);

            if (message.contains("{player}")) message = replacePlayerString(player, message, 0);
            if (message.contains("{l_player}")) message = replacePlayerString(player, message, 1);
            if (message.contains("{u_player}")) message = replacePlayerString(player, message, 2);

            event.setJoinMessage(" »» " + ChatColor.translateAlternateColorCodes('&', message));

        }

        if (playerJoinLocation.containsKey(playerUUID)) player.teleport(playerJoinLocation.get(playerUUID));

    }

}
