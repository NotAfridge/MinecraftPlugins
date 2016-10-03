package com.ullarah.ulottery;

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
class LotteryEvents extends LotteryMessageInit implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void playerDeath(PlayerDeathEvent event) {

        Player player = event.getEntity();

        if (!getPause().isPaused()) {

            getRecentDeath().setName(player.getPlayerListName());
            getCountdown().setCount(getCountdown().getOriginal());

            getRecentDeath().getMap().put(player.getUniqueId(),
                    getRecentDeath().getMap().containsKey(player.getUniqueId())
                            ? getRecentDeath().getMap().get(player.getUniqueId()) + 5
                            : getSuspension().getTime());

            if (!getSuspension().getMap().containsKey(player.getUniqueId()))
                getBank().setAmount(LotteryInit.getEconomy() != null
                        ? getBank().getAmount() + getRecentWinner().getVaultAmount()
                        : getBank().getAmount() + getRecentWinner().getItemAmount());

            getSuspension().getMap().put(player.getUniqueId(),
                    getRecentDeath().getMap().get(player.getUniqueId()));

            String deathMessage = event.getDeathMessage() == null
                    ? "died" : event.getDeathMessage().split(" ", 2)[1];

            getRecentDeath().setReason(deathMessage.equals("died")
                    ? "Mysterious Forces..."
                    : deathMessage.substring(0, 1).toUpperCase() + deathMessage.substring(1));

        }

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void blockBreak(BlockBreakEvent event) {

        if (!getPause().isPaused()) if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {

            int blocks = getBlock().getAmount();
            getBlock().setTotal(getBlock().getTotal() + 1);

            if (blocks >= getBlock().getLimit()) {
                getBank().setAmount(getBank().getAmount() + 1);
                getBlock().setAmount(0);
            } else getBlock().setAmount(blocks + 1);

        }

    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {

        if (LotteryInit.getPlugin().getServer().getOnlinePlayers().size()
                < getPause().getTotal() && !getPause().isPaused())
            getPause().setPaused(true);

    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {

        if (LotteryInit.getPlugin().getServer().getOnlinePlayers().size()
                >= getPause().getTotal() && getPause().isPaused())
            getPause().setPaused(false);

    }

}
