package com.ullarah.urocket.task;

import com.ullarah.urocket.RocketFunctions;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import static com.ullarah.urocket.RocketInit.pluginName;
import static com.ullarah.urocket.RocketInit.rocketEnhancement;
import static com.ullarah.urocket.init.RocketEnhancement.Enhancement.REPAIR;

public class RocketRepair {

    public void task() {

        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
        RocketFunctions rocketFunctions = new RocketFunctions();

        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin,
                () -> plugin.getServer().getScheduler().runTask(plugin, () -> {

                    if (!rocketEnhancement.isEmpty())
                        rocketEnhancement.entrySet().stream().filter(entry -> rocketEnhancement.get(
                                entry.getKey()).equals(REPAIR)).forEach(entry -> {

                            Player player = Bukkit.getPlayer(entry.getKey());
                            ItemStack rocketBoots = player.getInventory().getBoots();

                            if (rocketBoots == null) return;

                            int repairRate = rocketFunctions.getBootRepairRate(rocketBoots.getType());

                            if (repairRate > 0 && player.isFlying()) {

                                short currentDurability = rocketBoots.getDurability();

                                if (currentDurability > 0) {
                                    rocketBoots.setDurability((short) (currentDurability - repairRate));
                                    player.playSound(player.getLocation(), Sound.ANVIL_USE, 0.35f, 1.75f);
                                }

                            }

                        });

                }), 400, 400);

    }

}
