package com.ullarah.urocket.task;

import com.ullarah.urocket.RocketInit;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;

import static com.ullarah.urocket.RocketInit.getPlugin;
import static com.ullarah.urocket.RocketInit.rocketHealer;

public class RocketHeal {

    public void task() {

        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(),
                () -> Bukkit.getScheduler().runTask(getPlugin(), () -> {

                    if (!rocketHealer.isEmpty())
                        for (Map.Entry<UUID, Integer> rocketHealer : RocketInit.rocketHealer.entrySet()) {

                            Player player = Bukkit.getPlayer(rocketHealer.getKey());
                            Integer repair = rocketHealer.getValue();

                            if (repair != 0) {

                                ItemStack rocketBoots = player.getInventory().getBoots();

                                short currentDurability = rocketBoots.getDurability();
                                rocketBoots.setDurability((short) (currentDurability - repair));

                                player.playSound(player.getLocation(), Sound.ANVIL_USE, 0.5f, 1.5f);

                            }

                        }

                }), 300, 300);

    }

}
