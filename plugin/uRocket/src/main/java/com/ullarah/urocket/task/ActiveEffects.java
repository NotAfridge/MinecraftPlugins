package com.ullarah.urocket.task;

import com.ullarah.ulib.function.GamemodeCheck;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;
import java.util.UUID;

import static com.ullarah.urocket.RocketFunctions.Variant;
import static com.ullarah.urocket.RocketInit.*;

public class ActiveEffects {

    public void task() {

        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(), new Runnable() {

            public void run() {

                Bukkit.getScheduler().runTask(getPlugin(), new Runnable() {

                    @Override
                    public void run() {

                        if (!rocketPower.isEmpty()) {
                            for (Map.Entry<UUID, Integer> rocketActive : rocketPower.entrySet()) {

                                Player player = Bukkit.getPlayer(rocketActive.getKey());

                                if (GamemodeCheck.check(player, GameMode.SURVIVAL, GameMode.ADVENTURE)) {
                                    if (player.getWorld().getName().equals("world")) {
                                        if (rocketVariant.containsKey(player.getUniqueId())) if (player.isFlying()) {

                                            Variant bootVariant = rocketVariant.get(player.getUniqueId());

                                            if (bootVariant != null) {

                                                for (PotionEffect effect : player.getActivePotionEffects())
                                                    player.removePotionEffect(effect.getType());

                                                switch (bootVariant) {

                                                    case ENDER:
                                                        rocketEffects.add(player.getUniqueId());
                                                        player.addPotionEffect(new PotionEffect(
                                                                PotionEffectType.NIGHT_VISION,
                                                                Integer.MAX_VALUE, 2, false, false), true);
                                                        player.addPotionEffect(new PotionEffect(
                                                                PotionEffectType.HEALTH_BOOST,
                                                                Integer.MAX_VALUE, 4, false, false), true);
                                                        break;

                                                    case ZERO:
                                                        rocketEffects.add(player.getUniqueId());
                                                        player.addPotionEffect(new PotionEffect(
                                                                PotionEffectType.CONFUSION,
                                                                Integer.MAX_VALUE, 0, false, false), true);
                                                        player.addPotionEffect(new PotionEffect(
                                                                PotionEffectType.DAMAGE_RESISTANCE,
                                                                Integer.MAX_VALUE, 1, false, false), true);
                                                        player.addPotionEffect(new PotionEffect(
                                                                PotionEffectType.INCREASE_DAMAGE,
                                                                Integer.MAX_VALUE, 1, false, false), true);
                                                        break;

                                                    case STEALTH:
                                                        rocketEffects.add(player.getUniqueId());
                                                        for (Player onlinePlayer : Bukkit.getOnlinePlayers())
                                                            onlinePlayer.hidePlayer(player);
                                                        break;

                                                    case DRUNK:
                                                        rocketEffects.add(player.getUniqueId());
                                                        player.addPotionEffect(new PotionEffect(
                                                                PotionEffectType.CONFUSION,
                                                                Integer.MAX_VALUE, 0, false, false), true);
                                                        player.addPotionEffect(new PotionEffect(
                                                                PotionEffectType.FAST_DIGGING,
                                                                Integer.MAX_VALUE, 2, false, false), true);
                                                        break;

                                                    case BOOST:
                                                        rocketEffects.add(player.getUniqueId());
                                                        player.addPotionEffect(new PotionEffect(
                                                                PotionEffectType.HEAL,
                                                                Integer.MAX_VALUE, 1, false, false), true);
                                                        break;

                                                    default:
                                                        break;

                                                }

                                            }

                                        } else if (rocketEffects.contains(player.getUniqueId())) {

                                            for (PotionEffect effect : player.getActivePotionEffects())
                                                player.removePotionEffect(effect.getType());

                                            for (Player onlinePlayer : Bukkit.getOnlinePlayers())
                                                onlinePlayer.showPlayer(player);

                                            rocketEffects.remove(player.getUniqueId());

                                        }
                                    }
                                }

                            }
                        }

                    }

                });

            }

        }, 0, 0);

    }

}
