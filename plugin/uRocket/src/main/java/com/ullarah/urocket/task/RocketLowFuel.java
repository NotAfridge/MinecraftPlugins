package com.ullarah.urocket.task;

import com.ullarah.ulib.function.TitleSubtitle;
import com.ullarah.urocket.RocketVariant;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

import static com.ullarah.ulib.function.CommonString.messageSend;
import static com.ullarah.urocket.RocketInit.*;
import static com.ullarah.urocket.RocketLanguage.*;

public class RocketLowFuel {

    public void task() {

        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);

        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin,
                () -> plugin.getServer().getScheduler().runTask(plugin, () -> {

                    if (!rocketUsage.isEmpty()) for (UUID uuid : rocketUsage) {

                        Player player = Bukkit.getPlayer(uuid);

                        if (player.isFlying()) {

                            boolean fuelLow = false;
                            String fuelType = "";
                            RocketVariant.Variant bootVariant = rocketVariant.get(player.getUniqueId());

                            switch (bootVariant) {

                                case HEALTH:
                                    fuelType = "health";
                                    if (player.getHealth() <= 3.0 || player.getFoodLevel() <= 4) fuelLow = true;
                                    break;

                                case MONEY:
                                    fuelType = "money";
                                    if (getVaultEconomy().getBalance(player) <= 20.0) fuelLow = true;
                                    break;

                                case TREE:
                                    fuelType = "wood";
                                    if (!player.getInventory().containsAtLeast(new ItemStack(Material.WOOD), 16))
                                        if (!player.getInventory().containsAtLeast(new ItemStack(Material.LOG), 1))
                                            fuelLow = true;
                                    break;

                                case FURY:
                                    fuelType = "redstone";
                                    if (!player.getInventory().containsAtLeast(new ItemStack(Material.REDSTONE), 16))
                                        if (!player.getInventory().containsAtLeast(new ItemStack(Material.REDSTONE_BLOCK), 1))
                                            fuelLow = true;
                                    break;

                                default:
                                    fuelType = "coal";
                                    if (!player.getInventory().containsAtLeast(new ItemStack(Material.COAL), 16))
                                        if (!player.getInventory().containsAtLeast(new ItemStack(Material.COAL_BLOCK), 1))
                                            fuelLow = true;
                                    break;

                            }

                            if (fuelLow) {
                                TitleSubtitle.subtitle(player, 1, RB_FUEL_LOW);
                                messageSend(getPlugin(), player, true, new String[]{
                                        FuelLow(fuelType), RB_FUEL_WARNING
                                });
                                player.getWorld().playSound(player.getLocation(), Sound.ORB_PICKUP, 0.5f, 1.3f);
                            }

                        }

                    }


                }), 0, 100);

    }

}
