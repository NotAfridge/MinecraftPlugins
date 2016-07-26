package com.ullarah.ulottery;

import com.ullarah.ulottery.message.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

class LotteryTask {

    static void deathLotteryStart() {

        Bank bank = LotteryInit.bank;
        Block block = LotteryInit.block;
        Countdown countdown = LotteryInit.countdown;
        Duration duration = LotteryInit.duration;
        Pause pause = LotteryInit.pause;
        RecentDeath recentDeath = LotteryInit.recentDeath;
        RecentWinner recentWinner = LotteryInit.recentWinner;
        Suspension suspension = LotteryInit.suspension;

        LotteryInit.getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(
                LotteryInit.getPlugin(), () -> {

                    if (!pause.getPaused()) {

                        duration.setCount(duration.getCount() + 1);

                        if (countdown.getCount() > 0) countdown.setCount(countdown.getCount() - 1);
                        else {

                            if (bank.getAmount() > 0) {

                                Collection onlinePlayers = LotteryInit.getPlugin().getServer().getOnlinePlayers();

                                Player player = (Player) onlinePlayers.toArray()[
                                        new Random().nextInt(onlinePlayers.size())];

                                String winnings;

                                if (LotteryInit.economy != null) {

                                    LotteryInit.economy.depositPlayer(player, bank.getAmount());
                                    winnings = "$" + bank.getAmount();

                                } else {

                                    LotteryInit.getPlugin().getServer().getScheduler().runTask(
                                            LotteryInit.getPlugin(), () ->
                                                    player.getWorld().dropItemNaturally(player.getEyeLocation(),
                                                            new ItemStack(bank.getItemMaterial(), bank.getAmount())));

                                    winnings = bank.getAmount() + " " + bank.getItemMaterial().name()
                                            .replace("_", " ").toLowerCase() + (bank.getAmount() > 1 ? "s" : "");
                                }

                                for (Object playerOnline : onlinePlayers) {

                                    Player currentPlayer = (Player) playerOnline;

                                    currentPlayer.sendMessage(ChatColor.GOLD + "[" + LotteryInit.getPlugin().getName() + "] " +
                                            ChatColor.YELLOW + player.getPlayerListName()
                                            + ChatColor.RESET + " won " + ChatColor.GREEN + winnings
                                            + ChatColor.RESET + " from the Lottery!");

                                }

                                recentWinner.setName(player.getPlayerListName());
                                recentWinner.setAmount(bank.getAmount());

                            }

                            countdown.setCount(countdown.getOriginal());
                            duration.setCount(0);
                            bank.setAmount(0);
                            block.setAmount(0);
                            recentDeath.setName("");
                            recentDeath.setReason("");

                            recentDeath.getMap().clear();
                            suspension.getMap().clear();

                        }

                    }

                    if (suspension.getMap().size() > 0) {

                        for (Map.Entry<UUID, Integer> entry : suspension.getMap().entrySet()) {

                            UUID uuid = entry.getKey();
                            Integer timeout = entry.getValue() - 1;

                            if (timeout <= 0) suspension.getMap().remove(uuid);
                            else suspension.getMap().put(uuid, timeout);

                        }

                    }

                }, countdown.getOriginal() * 20, countdown.getOriginal() * 20);

    }

}
