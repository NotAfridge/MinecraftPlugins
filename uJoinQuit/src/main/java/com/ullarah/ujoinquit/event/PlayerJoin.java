package com.ullarah.ujoinquit.event;

import com.ullarah.ujoinquit.JoinQuitFunctions;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

import static com.ullarah.ujoinquit.JoinQuitFunctions.Message.JOIN;
import static com.ullarah.ujoinquit.JoinQuitInit.*;

public class PlayerJoin implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void event(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (playerJoinMessage.containsKey(playerUUID)) {

            JoinQuitFunctions joinQuitFunctions = new JoinQuitFunctions();

            String message = joinQuitFunctions.replacePlayerString(player, joinQuitFunctions.getMessage(player, JOIN));
            event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', joinChar + message));

            lastPlayer = player.getPlayerListName();

        }

        if (playerJoinLocation.containsKey(playerUUID)) player.teleport(playerJoinLocation.get(playerUUID));

    }

}
