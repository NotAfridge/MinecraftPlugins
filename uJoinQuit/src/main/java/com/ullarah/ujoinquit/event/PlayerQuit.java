package com.ullarah.ujoinquit.event;

import com.ullarah.ujoinquit.JoinQuitFunctions;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

import static com.ullarah.ujoinquit.JoinQuitFunctions.Message.QUIT;
import static com.ullarah.ujoinquit.JoinQuitInit.playerQuitMessage;
import static com.ullarah.ujoinquit.JoinQuitInit.quitChar;

public class PlayerQuit implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void playerQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (playerQuitMessage.containsKey(playerUUID)) {

            JoinQuitFunctions joinQuitFunctions = new JoinQuitFunctions();

            String message = joinQuitFunctions.replacePlayerString(player, joinQuitFunctions.getMessage(player, QUIT));
            event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', quitChar + message));

        }

    }

}
