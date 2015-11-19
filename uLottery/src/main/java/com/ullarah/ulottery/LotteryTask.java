package com.ullarah.ulottery;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import static com.ullarah.ulottery.LotteryInit.*;

class LotteryTask {

    public static void deathLotteryStart() {

        getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(), () -> {

            if (!deathLotteryPaused) {

                deathDuration++;

                if (deathCountdown > 0) deathCountdown--;
                else {

                    if (deathLotteryBank > 0) {

                        Collection onlinePlayers = getPlugin().getServer().getOnlinePlayers();

                        Player player = (Player) onlinePlayers.toArray()[
                                new Random().nextInt(onlinePlayers.size())];

                        economy.depositPlayer(player, deathLotteryBank);

                        getPlugin().getServer().broadcastMessage(ChatColor.YELLOW + player.getPlayerListName()
                                + ChatColor.RESET + " won " + ChatColor.GREEN + "$" + deathLotteryBank
                                + ChatColor.RESET + " from the Death Lottery!");

                        recentWinnerName = player.getPlayerListName();
                        recentWinnerAmount = deathLotteryBank;

                    }

                    deathCountdown = 60;
                    deathDuration = 0;
                    deathLotteryBank = 0;

                    playerDeathPrevious.clear();
                    playerDeathSuspension.clear();

                }

            }

            if (playerDeathSuspension.size() > 0) {

                for (Map.Entry<UUID, Integer> entry : playerDeathSuspension.entrySet()) {

                    UUID uuid = entry.getKey();
                    Integer timeout = entry.getValue() - 1;

                    if (timeout <= 0) playerDeathSuspension.remove(uuid);
                    else playerDeathSuspension.put(uuid, timeout);

                }

            }

        }, 1200, 1200);

    }

}
