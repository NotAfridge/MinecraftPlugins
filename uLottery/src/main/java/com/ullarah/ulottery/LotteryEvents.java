package com.ullarah.ulottery;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

class LotteryEvents implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void playerDeath(final PlayerDeathEvent event) {

        Player player = event.getEntity();

        if (!LotteryInit.deathLotteryPaused) {

            LotteryInit.recentDeathName = player.getPlayerListName();
            LotteryInit.deathCountdown = LotteryInit.deathCountdownReset;

            LotteryInit.playerDeathPrevious.put(player.getUniqueId(),
                    LotteryInit.playerDeathPrevious.containsKey(player.getUniqueId())
                            ? LotteryInit.playerDeathPrevious.get(player.getUniqueId()) + 5 : LotteryInit.deathSuspension);

            if (!LotteryInit.playerDeathSuspension.containsKey(player.getUniqueId()))
                LotteryInit.deathLotteryBank += LotteryInit.economy != null
                        ? LotteryInit.winVaultAmount : LotteryInit.winItemAmount;

            LotteryInit.playerDeathSuspension.put(player.getUniqueId(),
                    LotteryInit.playerDeathPrevious.get(player.getUniqueId()));

            String deathMessage = event.getDeathMessage().split(" ", 2)[1];
            String deathMessageFix = deathMessage.substring(0, 1).toUpperCase() + deathMessage.substring(1);

            if (deathMessage.equals("died")) deathMessageFix = "Mysterious Forces...";
            LotteryInit.recentDeathReason = deathMessageFix;

        }

    }

    @EventHandler
    public void playerQuit(final PlayerQuitEvent event) {

        if (LotteryInit.getPlugin().getServer().getOnlinePlayers().size()
                < LotteryInit.totalPlayerPause && !LotteryInit.deathLotteryPaused)
            LotteryInit.deathLotteryPaused = true;

    }

    @EventHandler
    public void playerJoin(final PlayerJoinEvent event) {

        if (LotteryInit.getPlugin().getServer().getOnlinePlayers().size()
                >= LotteryInit.totalPlayerPause && LotteryInit.deathLotteryPaused)
            LotteryInit.deathLotteryPaused = false;

    }

}
