package com.ullarah.ujoinquit.event;

import com.ullarah.ujoinquit.JoinQuitFunctions;
import com.ullarah.ujoinquit.JoinQuitInit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerQuit implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void playerQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (JoinQuitInit.playerQuitMessage.containsKey(playerUUID) && !event.getQuitMessage().isEmpty()) {

            JoinQuitFunctions joinQuitFunctions = new JoinQuitFunctions();

            String message = joinQuitFunctions.replacePlayerString(player,
                    joinQuitFunctions.getMessage(player, JoinQuitFunctions.Message.QUIT));

            event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', JoinQuitInit.quitChar + message));

        }

    }

}
