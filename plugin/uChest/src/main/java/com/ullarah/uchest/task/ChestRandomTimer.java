package com.ullarah.uchest.task;

import com.ullarah.uchest.command.RandomItem;
import org.bukkit.Bukkit;

import static com.ullarah.uchest.ChestInit.getChestRandomInventory;
import static com.ullarah.uchest.ChestInit.getPlugin;

public class ChestRandomTimer {

    private static final long chestIntervalFinal = getPlugin().getConfig().getLong("raninterval") * 20;
    private static final long chestStayFinal = getPlugin().getConfig().getLong("ranstay") * 20;
    private static final long chestAmountFinal = getPlugin().getConfig().getLong("ranamount");

    public static void task() {

        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(), () -> {

            getChestRandomInventory().clear();
            RandomItem.setRandomItem((int) chestAmountFinal);

            Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(getPlugin(),
                    () -> getChestRandomInventory().clear(), chestStayFinal);

        }, chestIntervalFinal, chestIntervalFinal);

    }

}
