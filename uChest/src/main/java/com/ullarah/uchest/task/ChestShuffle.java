package com.ullarah.uchest.task;

import com.ullarah.uchest.ChestInit;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChestShuffle {

    private final boolean chestShuffleEnabled = ChestInit.getPlugin().getConfig().getBoolean("schest.enabled");
    private final double chestShuffleTimer = ChestInit.getPlugin().getConfig().getDouble("schest.timer");

    public void task() {

        if (chestShuffleEnabled) {

            int shuffleTimer = (int) (chestShuffleTimer * 20);

            List<ItemStack> materialKeys = new ArrayList<>(ChestInit.materialMap.keySet());

            ChestInit.getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(ChestInit.getPlugin(), () -> {

                for (int i = 0; i < ChestInit.getChestShuffleInventory().getSize(); i++) {

                    ItemStack itemStack = materialKeys.get(new Random().nextInt(materialKeys.size()));

                    if ((boolean) ChestInit.materialMap.get(itemStack)[0])
                        ChestInit.getChestShuffleInventory().setItem(i, itemStack);

                }

            }, shuffleTimer, shuffleTimer);

        }

    }

}
