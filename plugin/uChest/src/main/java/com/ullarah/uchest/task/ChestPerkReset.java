package com.ullarah.uchest.task;

import org.bukkit.Bukkit;

import java.time.LocalDateTime;

import static com.ullarah.uchest.ChestInit.*;

public class ChestPerkReset {

    public static void task() {

        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(), () -> {

            if (LocalDateTime.now().getHour() == 0)
                Bukkit.getServer().getScheduler().runTaskAsynchronously(getPlugin(), () -> {
                    chestPerkLockout.clear();
                    chestPerkLockoutCount.clear();
                });

        }, 0, 1200);

    }

}
