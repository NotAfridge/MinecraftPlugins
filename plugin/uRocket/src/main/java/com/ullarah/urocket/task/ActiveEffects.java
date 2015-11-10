package com.ullarah.urocket.task;

import com.ullarah.ulib.function.GamemodeCheck;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.Map;
import java.util.UUID;

import static com.ullarah.urocket.RocketInit.*;
import static com.ullarah.urocket.RocketVariant.Variant;
import static com.ullarah.urocket.RocketVariant.Variant.STEALTH;

public class ActiveEffects {

    public void task() {

        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(),
                () -> Bukkit.getScheduler().runTask(getPlugin(), () -> {

                    if (!rocketPower.isEmpty()) {
                        for (Map.Entry<UUID, Integer> entry : rocketPower.entrySet()) {

                            Player player = Bukkit.getPlayer(entry.getKey());

                            if (GamemodeCheck.check(player, GameMode.SURVIVAL, GameMode.ADVENTURE))
                                if (player.getWorld().getName().equals("world"))
                                    if (rocketVariant.containsKey(player.getUniqueId())) if (player.isFlying()) {

                                        Variant bootVariant = rocketVariant.get(player.getUniqueId());
                                        PotionEffect[] effects = bootVariant.getPotionEffects();

                                        for (PotionEffect effect : player.getActivePotionEffects())
                                            player.removePotionEffect(effect.getType());

                                        if (effects != null) {
                                            for (PotionEffect effect : effects) {
                                                rocketEffects.add(player.getUniqueId());
                                                player.addPotionEffect(effect);
                                            }
                                        }

                                        if (bootVariant.equals(STEALTH))
                                            for (Player onlinePlayer : Bukkit.getOnlinePlayers())
                                                onlinePlayer.hidePlayer(player);

                                    } else if (rocketEffects.contains(player.getUniqueId())) {

                                        for (PotionEffect effect : player.getActivePotionEffects())
                                            player.removePotionEffect(effect.getType());

                                        for (Player onlinePlayer : Bukkit.getOnlinePlayers())
                                            onlinePlayer.showPlayer(player);

                                        rocketEffects.remove(player.getUniqueId());

                                    }

                        }
                    }

                }), 0, 0);

    }

}
