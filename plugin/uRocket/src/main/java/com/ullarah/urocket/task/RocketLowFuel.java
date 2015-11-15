package com.ullarah.urocket.task;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

import static com.ullarah.urocket.RocketInit.*;

public class RocketLowFuel {

    public void task() {

        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);

        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin,
                () -> plugin.getServer().getScheduler().runTask(plugin, () -> {

                    if (!rocketLowFuel.isEmpty()) for (UUID uuid : rocketLowFuel) {

                        Player player = Bukkit.getPlayer(uuid);

                        if (rocketLowFuel.contains(player.getUniqueId()) && !rocketVariant.get(uuid).isAlternateFuel())
                            if (player.isFlying()) if (player.getLevel() <= 6)
                                player.getWorld().playSound(player.getLocation(), Sound.ORB_PICKUP, 0.5f, 1.3f);
                            else rocketLowFuel.remove(uuid);

                    }

                }), 0, 15);

    }

}
