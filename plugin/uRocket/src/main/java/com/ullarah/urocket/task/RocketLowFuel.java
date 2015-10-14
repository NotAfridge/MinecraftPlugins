package com.ullarah.urocket.task;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.UUID;

import static com.ullarah.urocket.RocketInit.*;

public class RocketLowFuel {

    public void task() {

        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(),
                () -> Bukkit.getServer().getScheduler().runTask(getPlugin(), () -> {

                    if (!rocketLowFuel.isEmpty()) for (UUID uuid : rocketLowFuel) {

                        Player player = Bukkit.getPlayer(uuid);

                        boolean alternateFuel = false;

                        switch (rocketVariant.get(uuid)) {

                            case COAL:
                                alternateFuel = true;
                                break;

                            case HEALTH:
                                alternateFuel = true;
                                break;

                            case MONEY:
                                alternateFuel = true;
                                break;

                            case REDSTONE:
                                alternateFuel = true;
                                break;

                        }

                        if (rocketLowFuel.contains(player.getUniqueId()) && !alternateFuel) {

                            if (player.isFlying()) if (player.getLevel() <= 6)
                                player.getWorld().playSound(player.getLocation(), Sound.ORB_PICKUP, 0.5f, 1.3f);
                            else rocketLowFuel.remove(uuid);

                        }

            }

                }), 0, 15);

    }

}
