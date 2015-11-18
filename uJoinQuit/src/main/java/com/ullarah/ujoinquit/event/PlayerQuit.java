package com.ullarah.ujoinquit.event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

import static com.ullarah.ujoinquit.JoinQuitFunctions.getMessage;
import static com.ullarah.ujoinquit.JoinQuitFunctions.messageType.QUIT;
import static com.ullarah.ujoinquit.JoinQuitFunctions.replacePlayerString;
import static com.ullarah.ujoinquit.JoinQuitInit.playerQuitMessage;
import static com.ullarah.ujoinquit.JoinQuitInit.quitChar;

public class PlayerQuit implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void playerQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (playerQuitMessage.containsKey(playerUUID)) {

            String message = replacePlayerString(player, getMessage(player, QUIT));
            event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', quitChar + message));

        }

    }

}
