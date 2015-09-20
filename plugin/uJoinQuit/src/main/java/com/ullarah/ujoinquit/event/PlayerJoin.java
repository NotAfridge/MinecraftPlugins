package com.ullarah.ujoinquit.event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static com.ullarah.ujoinquit.JoinQuitFunctions.getMessage;
import static com.ullarah.ujoinquit.JoinQuitFunctions.messageType.JOIN;
import static com.ullarah.ujoinquit.JoinQuitInit.playerJoinMessage;

public class PlayerJoin implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void event(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        if (playerJoinMessage.containsKey(player.getUniqueId()))
            event.setJoinMessage(" ▶▶ " + ChatColor.translateAlternateColorCodes('&',
                    getMessage(player, JOIN).replaceAll("\\{player\\}", player.getPlayerListName())));

    }

}
