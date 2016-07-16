package com.ullarah.upostal.task;

import com.ullarah.upostal.PostalInit;
import com.ullarah.upostal.function.CommonString;
import com.ullarah.upostal.function.TitleSubtitle;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

public class PostalReminder {

    public BukkitTask task(final UUID player) {

        CommonString commonString = new CommonString();
        TitleSubtitle titleSubtitle = new TitleSubtitle();

        return PostalInit.getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(PostalInit.getPlugin(),
                () -> PostalInit.getPlugin().getServer().getScheduler().runTask(PostalInit.getPlugin(), () -> {

                    for (Player receiverPlayer : PostalInit.getPlugin().getServer().getOnlinePlayers())
                        if (receiverPlayer.getUniqueId().equals(player)) {

                            String message = ChatColor.YELLOW + "You have new items in your inbox!";
                            commonString.messageSend(PostalInit.getPlugin(), receiverPlayer, message);
                            titleSubtitle.subtitle(receiverPlayer, message);
                            break;

                        }

                }), 6000, 6000);

    }

}
