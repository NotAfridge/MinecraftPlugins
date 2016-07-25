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

class LotteryTask {

    static void deathLotteryStart() {

        LotteryInit.getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(
                LotteryInit.getPlugin(), () -> {

                    if (!LotteryInit.deathLotteryPaused) {

                        LotteryInit.deathDuration++;

                        if (LotteryInit.deathCountdown > 0) LotteryInit.deathCountdown--;
                else {

                            if (LotteryInit.deathLotteryBank > 0) {

                                Collection onlinePlayers = LotteryInit.getPlugin().getServer().getOnlinePlayers();

                        Player player = (Player) onlinePlayers.toArray()[
                                new Random().nextInt(onlinePlayers.size())];

                        World world = player.getWorld();
                        Location location = player.getLocation();

                        String winnings;

                                if (LotteryInit.economy != null) {
                                    LotteryInit.economy.depositPlayer(player, LotteryInit.deathLotteryBank);
                                    winnings = "$" + LotteryInit.deathLotteryBank;
                        } else {
                            Bukkit.getWorld(world.getName()).dropItemNaturally(
                                    location, new ItemStack(LotteryInit.winItemMaterial, LotteryInit.deathLotteryBank));
                                    String s = LotteryInit.deathLotteryBank > 1 ? "s" : "";
                                    winnings = LotteryInit.deathLotteryBank + " " + LotteryInit.winItemMaterial.name().replace("_", " ").toLowerCase() + s;
                        }

                        for (Object playerOnline : onlinePlayers) {
                            Player currentPlayer = (Player) playerOnline;
                            currentPlayer.sendMessage(ChatColor.GOLD + "[" + LotteryInit.getPlugin().getName() + "] " +
                                    ChatColor.YELLOW + player.getPlayerListName()
                                    + ChatColor.RESET + " won " + ChatColor.GREEN + winnings
                                    + ChatColor.RESET + " from the Death Lottery!");
                        }

                                LotteryInit.recentWinnerName = player.getPlayerListName();
                                LotteryInit.recentWinnerAmount = LotteryInit.deathLotteryBank;

                    }

                            LotteryInit.deathCountdown = LotteryInit.deathCountdownReset;
                            LotteryInit.deathDuration = 0;
                            LotteryInit.deathLotteryBank = 0;
                            LotteryInit.deathRecent = "";

                            LotteryInit.playerDeathPrevious.clear();
                            LotteryInit.playerDeathSuspension.clear();

                }

            }

                    if (LotteryInit.playerDeathSuspension.size() > 0) {

                        for (Map.Entry<UUID, Integer> entry : LotteryInit.playerDeathSuspension.entrySet()) {

                    UUID uuid = entry.getKey();
                    Integer timeout = entry.getValue() - 1;

                            if (timeout <= 0) LotteryInit.playerDeathSuspension.remove(uuid);
                            else LotteryInit.playerDeathSuspension.put(uuid, timeout);

                }

            }

                }, LotteryInit.deathCountdownReset * 20, LotteryInit.deathCountdownReset * 20);

    }

}
