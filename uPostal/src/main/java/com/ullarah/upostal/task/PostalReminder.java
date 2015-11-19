package com.ullarah.upostal.task;

import com.ullarah.upostal.function.CommonString;
import com.ullarah.upostal.function.TitleSubtitle;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

import static com.ullarah.upostal.PostalInit.getPlugin;

public class PostalReminder {

    public BukkitTask task(final UUID player) {

        CommonString commonString = new CommonString();
        TitleSubtitle titleSubtitle = new TitleSubtitle();

        return getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(),
                () -> getPlugin().getServer().getScheduler().runTask(getPlugin(), () -> {

                    for (Player receiverPlayer : getPlugin().getServer().getOnlinePlayers())
                        if (receiverPlayer.getUniqueId().equals(player)) {

                            String message = ChatColor.YELLOW + "You have new items in your inbox!";
                            commonString.messageSend(getPlugin(), receiverPlayer, message);
                            titleSubtitle.subtitle(receiverPlayer, message);
                            break;

                        }

                }), 6000, 6000);

    }

}
