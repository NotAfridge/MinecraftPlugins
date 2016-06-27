package com.ullarah.ulottery;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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

                        World world = player.getWorld();
                        Location location = player.getLocation();

                        String winnings;

                        if (economy != null) {
                            economy.depositPlayer(player, deathLotteryBank);
                            winnings = "$" + deathLotteryBank;
                        } else {
                            Bukkit.getWorld(world.getName()).dropItemNaturally(
                                    location, new ItemStack(winItemMaterial, deathLotteryBank));
                            String s = deathLotteryBank > 1 ? "s" : "";
                            winnings = deathLotteryBank + " " + winItemMaterial.name().replace("_"," ").toLowerCase() + s;
                        }

                        for (Object playerOnline : onlinePlayers) {
                            Player currentPlayer = (Player) playerOnline;
                            currentPlayer.sendMessage(ChatColor.GOLD + "[" + getPlugin().getName() + "] " +
                                    ChatColor.YELLOW + player.getPlayerListName()
                                    + ChatColor.RESET + " won " + ChatColor.GREEN + winnings
                                    + ChatColor.RESET + " from the Death Lottery!");
                        }

                        recentWinnerName = player.getPlayerListName();
                        recentWinnerAmount = deathLotteryBank;

                    }

                    deathCountdown = deathCountdownReset;
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

        }, deathCountdownReset * 20, deathCountdownReset * 20);

    }

}
