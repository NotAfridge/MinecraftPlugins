package com.ullarah.ulottery;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static com.ullarah.ulottery.LotteryInit.*;

class LotteryEvents implements Listener {

    @EventHandler
    public void playerDeath(final PlayerDeathEvent event) {

        Player player = event.getEntity();

        if (!deathLotteryPaused) {

            recentDeathName = player.getPlayerListName();
            deathCountdown = 60;

            playerDeathPrevious.put(player.getUniqueId(), playerDeathPrevious.containsKey(player.getUniqueId())
                    ? playerDeathPrevious.get(player.getUniqueId()) + 5 : 30);

            if (!playerDeathSuspension.containsKey(player.getUniqueId())) deathLotteryBank = deathLotteryBank + 5;
            playerDeathSuspension.put(player.getUniqueId(), playerDeathPrevious.get(player.getUniqueId()));

            String deathMessage = event.getDeathMessage().split(" ", 2)[1];
            String deathMessageFix = deathMessage.substring(0, 1).toUpperCase() + deathMessage.substring(1);

            if (deathMessage.equals("died")) deathMessageFix = "Mysterious Forces...";
            recentDeathReason = deathMessageFix;

        }

    }

    @EventHandler
    public void playerQuit(final PlayerQuitEvent event) {

        if (getPlugin().getServer().getOnlinePlayers().size() < totalPlayerPause && !deathLotteryPaused)
            deathLotteryPaused = true;

    }

    @EventHandler
    public void playerJoin(final PlayerJoinEvent event) {

        if (getPlugin().getServer().getOnlinePlayers().size() >= totalPlayerPause && deathLotteryPaused)
            deathLotteryPaused = false;

    }

}
