package com.ullarah.upostal.task;

import com.ullarah.ulib.function.TitleSubtitle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

import static com.ullarah.upostal.PostalInit.getMsgPrefix;
import static com.ullarah.upostal.PostalInit.getPlugin;

public class PostalReminder {

    public static BukkitTask task(final UUID player) {

        return Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(), new Runnable() {

            public void run() {

                Bukkit.getScheduler().runTask(getPlugin(), new Runnable() {

                    @Override
                    public void run() {

                        for (Player receiverPlayer : Bukkit.getServer().getOnlinePlayers()) {

                            if (receiverPlayer.getUniqueId().equals(player)) {

                                String message = ChatColor.YELLOW + "You have new items in your inbox!";
                                receiverPlayer.sendMessage(getMsgPrefix() + message);
                                TitleSubtitle.subtitle(receiverPlayer, 5, message);
                                break;

                            }

                        }

                    }

                });

            }

        }, 6000, 6000);

    }

}
