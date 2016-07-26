package com.ullarah.ulottery;

import com.ullarah.ulottery.message.*;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@SuppressWarnings("UnusedParameters")
class LotteryEvents implements Listener {

    private final Bank bank = LotteryInit.bank;
    private final Block block = LotteryInit.block;
    private final Countdown countdown = LotteryInit.countdown;
    private final Pause pause = LotteryInit.pause;
    private final RecentDeath recentDeath = LotteryInit.recentDeath;
    private final RecentWinner recentWinner = LotteryInit.recentWinner;
    private final Suspension suspension = LotteryInit.suspension;

    @EventHandler(priority = EventPriority.LOWEST)
    public void playerDeath(PlayerDeathEvent event) {

        Player player = event.getEntity();

        if (!pause.getPaused()) {

            recentDeath.setName(player.getPlayerListName());
            countdown.setCount(countdown.getOriginal());

            recentDeath.getMap().put(player.getUniqueId(),
                    recentDeath.getMap().containsKey(player.getUniqueId())
                            ? recentDeath.getMap().get(player.getUniqueId()) + 5
                            : suspension.getTime());

            if (!suspension.getMap().containsKey(player.getUniqueId())) bank.setAmount(LotteryInit.economy != null
                    ? bank.getAmount() + recentWinner.getVaultAmount()
                    : bank.getAmount() + recentWinner.getItemAmount());

            suspension.getMap().put(player.getUniqueId(),
                    recentDeath.getMap().get(player.getUniqueId()));

            String deathMessage = event.getDeathMessage().split(" ", 2)[1];

            recentDeath.setReason(deathMessage.equals("died")
                    ? "Mysterious Forces..."
                    : deathMessage.substring(0, 1).toUpperCase() + deathMessage.substring(1));

        }

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void blockBreak(BlockBreakEvent event) {

        if (!pause.getPaused()) if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {

            int blocks = block.getAmount();

            if (blocks >= block.getTotal()) {
                bank.setAmount(bank.getAmount() + 1);
                block.setAmount(0);
            } else block.setAmount(blocks + 1);

        }

    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {

        if (LotteryInit.getPlugin().getServer().getOnlinePlayers().size()
                < pause.getTotal() && !pause.getPaused())
            pause.setPaused(true);

    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {

        if (LotteryInit.getPlugin().getServer().getOnlinePlayers().size()
                >= pause.getTotal() && pause.getPaused())
            pause.setPaused(false);

    }

}
