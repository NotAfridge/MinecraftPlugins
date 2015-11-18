package com.ullarah.urocket.task;

import com.ullarah.urocket.function.GamemodeCheck;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;

import java.util.Map;
import java.util.UUID;

import static com.ullarah.urocket.RocketInit.*;
import static com.ullarah.urocket.init.RocketVariant.Variant;
import static com.ullarah.urocket.init.RocketVariant.Variant.STEALTH;

public class ActiveEffects {

    public void task() {

        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
        GamemodeCheck gamemodeCheck = new GamemodeCheck();

        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin,
                () -> plugin.getServer().getScheduler().runTask(plugin, () -> {

                    if (!rocketPower.isEmpty()) {
                        for (Map.Entry<UUID, Integer> entry : rocketPower.entrySet()) {

                            Player player = Bukkit.getPlayer(entry.getKey());

                            if (gamemodeCheck.check(player, GameMode.SURVIVAL, GameMode.ADVENTURE))
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
