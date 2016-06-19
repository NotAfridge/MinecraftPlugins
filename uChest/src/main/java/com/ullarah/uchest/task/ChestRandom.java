package com.ullarah.uchest.task;

import com.ullarah.uchest.command.DonationRandom;
import org.bukkit.Bukkit;

import java.util.Random;

import static com.ullarah.uchest.ChestInit.getPlugin;

public class ChestRandom {

    private final boolean chestRandomEnabled = getPlugin().getConfig().getBoolean("dchest.enabled");
    private final long chestRandomChance = getPlugin().getConfig().getInt("dchest.random.chance");
    private final long chestRandomTimer = getPlugin().getConfig().getInt("dchest.random.timer") * 20;

    public void task() {

        if (chestRandomEnabled)
            getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(), () -> {

                if (chestRandomChance > 2)
                    if (Math.ceil(new Random().nextInt((int) chestRandomChance)) == Math.ceil((double) (chestRandomChance / 2))) {
                        Bukkit.getLogger().info("[" + getPlugin().getName() + "] " + "Randomising Donation Chest Items...");
                        new DonationRandom().chestRandomFill();
                    }

            }, chestRandomTimer, chestRandomTimer);

    }

}
