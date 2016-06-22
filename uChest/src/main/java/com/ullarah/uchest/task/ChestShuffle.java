package com.ullarah.uchest.task;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.ullarah.uchest.ChestInit.*;

public class ChestShuffle {

    private final boolean chestShuffleEnabled = getPlugin().getConfig().getBoolean("schest.enabled");
    private final double chestShuffleTimer = getPlugin().getConfig().getDouble("schest.timer");

    public void task() {

        if (chestShuffleEnabled) {

            int shuffleTimer = (int) (chestShuffleTimer * 20);

            List<ItemStack> materialKeys = new ArrayList<>(materialMap.keySet());

            getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(), () -> {

                for (int i = 0; i < getChestShuffleInventory().getSize(); i++) {

                    ItemStack itemStack = materialKeys.get(new Random().nextInt(materialKeys.size()));

                    if ((boolean) materialMap.get(itemStack)[0]) getChestShuffleInventory().setItem(i, itemStack);

                }

            }, shuffleTimer, shuffleTimer);

        }

    }

}
