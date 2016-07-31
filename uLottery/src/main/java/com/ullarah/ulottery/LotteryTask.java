package com.ullarah.ulottery;

import com.ullarah.ulottery.function.Broadcast;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

class LotteryTask extends LotteryMessageInit {

    static void deathLotteryStart() {

        LotteryInit.getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(
                LotteryInit.getPlugin(), () -> {

                    if (!getPause().isPaused()) {

                        getDuration().setCount(getDuration().getCount() + 1);

                        if (getCountdown().getCount() > 0) getCountdown().setCount(getCountdown().getCount() - 1);
                        else {

                            if (getBank().getWinAmount() > 0) {

                                Collection onlinePlayers = LotteryInit.getPlugin().getServer().getOnlinePlayers();
                                ArrayList<Player> validPlayers = new ArrayList<>();

                                for (Object player : onlinePlayers) {
                                    Player p = (Player) player;
                                    if (getSuspension().getMap().containsKey(p.getUniqueId())) continue;
                                    if (getExclude().hasPlayer(p.getPlayerListName())) continue;
                                    if (getRecentWinner().getWinName().equalsIgnoreCase(p.getPlayerListName()))
                                        continue;
                                    validPlayers.add((Player) player);
                                }

                                if (validPlayers.isEmpty()) {
                                    getCountdown().setCount(getCountdown().getOriginal());
                                    return;
                                }

                                Player player = validPlayers.get(new Random().nextInt(validPlayers.size()));

                                String winnings;

                                if (LotteryInit.getEconomy() != null) {

                                    LotteryInit.getEconomy().depositPlayer(player, getBank().getWinAmount());
                                    winnings = "$" + getBank().getWinAmount();

                                } else {

                                    LotteryInit.getPlugin().getServer().getScheduler().runTask(
                                            LotteryInit.getPlugin(), () ->
                                                    player.getWorld().dropItemNaturally(player.getEyeLocation(),
                                                            new ItemStack(getBank().getItemMaterial(),
                                                                    getBank().getWinAmount())));

                                    winnings = getBank().getWinAmount() + " " + getBank().getItemMaterial().name()
                                            .replace("_", " ").toLowerCase() + (getBank().getWinAmount() > 1 ? "s" : "");
                                }

                                new Broadcast().sendMessage(LotteryInit.getPlugin(), new String[]{
                                        ChatColor.YELLOW + player.getPlayerListName()
                                                + ChatColor.RESET + " won " + ChatColor.GREEN + winnings
                                                + ChatColor.RESET + " from the Lottery!"
                                });

                                getRecentWinner().setWinName(player.getPlayerListName());
                                getRecentWinner().setWinAmount(getBank().getWinAmount());

                                getHistory().addHistory(player.getPlayerListName(), getBank().getWinAmount());

                            }

                            getCountdown().reset();
                            getDuration().reset();
                            getBank().reset();
                            getBlock().reset();
                            getRecentDeath().reset();
                            getSuspension().reset();

                        }

                    }

                    if (getSuspension().getMap().size() > 0) {

                        for (Map.Entry<UUID, Integer> entry : getSuspension().getMap().entrySet()) {

                            UUID uuid = entry.getKey();
                            Integer timeout = entry.getValue() - 1;

                            if (timeout <= 0) getSuspension().getMap().remove(uuid);
                            else getSuspension().getMap().put(uuid, timeout);

                        }

                    }

                }, 0, 1200);

    }

}
