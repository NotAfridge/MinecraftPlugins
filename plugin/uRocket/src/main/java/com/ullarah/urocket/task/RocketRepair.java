package com.ullarah.urocket.task;

import com.ullarah.urocket.RocketFunctions;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static com.ullarah.urocket.RocketEnhancement.Enhancement.REPAIR;
import static com.ullarah.urocket.RocketInit.getPlugin;
import static com.ullarah.urocket.RocketInit.rocketEnhancement;

public class RocketRepair {

    public void task() {

        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(),
                () -> Bukkit.getScheduler().runTask(getPlugin(), () -> {

                    if (!rocketEnhancement.isEmpty())
                        rocketEnhancement.entrySet().stream().filter(entry -> rocketEnhancement.get(
                                entry.getKey()).equals(REPAIR)).forEach(entry -> {

                            Player player = Bukkit.getPlayer(entry.getKey());
                            ItemStack rocketBoots = player.getInventory().getBoots();

                            int repairRate = RocketFunctions.getBootRepairRate(rocketBoots.getType());

                            if (repairRate > 0 && player.isFlying()) {

                                short currentDurability = rocketBoots.getDurability();
                                rocketBoots.setDurability((short) (currentDurability - repairRate));

                                player.playSound(player.getLocation(), Sound.ANVIL_USE, 0.35f, 1.75f);

                            }

                        });

                }), 400, 400);

    }

}
