package com.ullarah.urocket.task;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;

import static com.ullarah.urocket.RocketEnhancement.Enhancement;
import static com.ullarah.urocket.RocketEnhancement.Enhancement.REPAIR;
import static com.ullarah.urocket.RocketInit.getPlugin;
import static com.ullarah.urocket.RocketInit.rocketEnhancement;

public class RocketRepair {

    public void task() {

        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(),
                () -> Bukkit.getScheduler().runTask(getPlugin(), () -> {

                    if (!rocketEnhancement.isEmpty())
                        for (Map.Entry<UUID, Enhancement> entry : rocketEnhancement.entrySet()) {

                            if (rocketEnhancement.get(entry.getKey()).equals(REPAIR)) {

                                Player player = Bukkit.getPlayer(entry.getKey());
                                ItemStack rocketBoots = player.getInventory().getBoots();

                                int repair = 0;

                                switch (rocketBoots.getType()) {

                                    case LEATHER_BOOTS:
                                        repair = 5;
                                        break;

                                    case IRON_BOOTS:
                                        repair = 4;
                                        break;

                                    case GOLD_BOOTS:
                                        repair = 3;
                                        break;

                                    case DIAMOND_BOOTS:
                                        repair = 2;
                                        break;

                                }

                                if (repair > 0 && player.isFlying()) {

                                    short currentDurability = rocketBoots.getDurability();
                                    rocketBoots.setDurability((short) (currentDurability - repair));

                                    player.playSound(player.getLocation(), Sound.ANVIL_USE, 0.35f, 1.75f);

                                }

                            }

                        }

                }), 400, 400);

    }

}
