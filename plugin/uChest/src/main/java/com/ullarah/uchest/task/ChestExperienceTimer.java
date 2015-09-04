package com.ullarah.uchest.task;

import com.ullarah.ulib.function.Experience;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;

import java.util.Map;
import java.util.UUID;

import static com.ullarah.uchest.ChestInit.chestRandomRemoveXP;
import static com.ullarah.uchest.ChestInit.getPlugin;

public class ChestExperienceTimer {

    private static final long xpRemIntervalFinal = getPlugin().getConfig().getLong("xprinterval") * 20;
    private static final long xpRemAmountFinal = getPlugin().getConfig().getLong("xpramount");

    public static void task() {

        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(), () -> {

            for (Map.Entry<UUID, InventoryView> map : chestRandomRemoveXP.entrySet()) {

                Player player = Bukkit.getPlayer(map.getKey());
                InventoryView inventory = map.getValue();

                if (player.getLevel() < 1) {
                    chestRandomRemoveXP.remove(map.getKey());
                    inventory.close();
                } else Experience.setExperience(player, xpRemAmountFinal);

            }

        }, xpRemIntervalFinal, xpRemIntervalFinal);

    }

}
