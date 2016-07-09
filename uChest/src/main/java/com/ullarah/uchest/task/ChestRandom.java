package com.ullarah.uchest.task;

import com.ullarah.uchest.ChestInit;
import com.ullarah.uchest.command.DonationRandom;

import java.util.Random;

public class ChestRandom {

    private final boolean chestRandomEnabled = ChestInit.getPlugin().getConfig().getBoolean("dchest.enabled");
    private final long chestRandomChance = ChestInit.getPlugin().getConfig().getInt("dchest.random.chance");
    private final long chestRandomTimer = ChestInit.getPlugin().getConfig().getInt("dchest.random.timer") * 20;

    public void task() {

        if (chestRandomEnabled)
            ChestInit.getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(ChestInit.getPlugin(), () -> {

                if (chestRandomChance > 2)
                    if (Math.ceil(new Random().nextInt((int) chestRandomChance))
                            == Math.ceil((double) (chestRandomChance / 2))) {
                        ChestInit.getPlugin().getLogger().info(
                                "[" + ChestInit.getPlugin().getName() + "] "
                                        + "Randomising Donation Chest Items...");
                        new DonationRandom().chestRandomFill();
                    }

            }, chestRandomTimer, chestRandomTimer);

    }

}
