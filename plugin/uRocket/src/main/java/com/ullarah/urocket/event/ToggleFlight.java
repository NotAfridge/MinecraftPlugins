package com.ullarah.urocket.event;

import com.ullarah.ulib.function.GamemodeCheck;
import com.ullarah.ulib.function.GroundFire;
import com.ullarah.ulib.function.TitleSubtitle;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Random;

import static com.ullarah.ulib.function.CommonString.messageSend;
import static com.ullarah.urocket.RocketFunctions.*;
import static com.ullarah.urocket.RocketInit.*;
import static com.ullarah.urocket.RocketLanguage.*;
import static com.ullarah.urocket.RocketVariant.Variant;
import static com.ullarah.urocket.RocketVariant.Variant.ORIGINAL;
import static com.ullarah.urocket.RocketVariant.Variant.RUNNER;

public class ToggleFlight implements Listener {

    @EventHandler
    public void toggleRocketFlight(PlayerToggleFlightEvent event) {

        Player player = event.getPlayer();

        if (GamemodeCheck.check(player, GameMode.SURVIVAL, GameMode.ADVENTURE)) {

            ItemStack rocketBoots = player.getInventory().getBoots();

            if (rocketBoots == null) {

                messageSend(getPlugin(), player, true, RB_ATTACH);
                disableRocketBoots(player, false, false, false, false, false, true);

            } else {

                if (isValidRocketBoots(rocketBoots)) {

                    if (player.getLocation().getY() >= 250) {

                        disableRocketBoots(player, true, true, true, true, true, true);
                        messageSend(getPlugin(), player, true, RB_HIGH);

                    } else if (player.getLocation().getY() <= 0) {

                        disableRocketBoots(player, true, true, true, true, true, true);
                        messageSend(getPlugin(), player, true, RB_LOW);

                    } else {

                        boolean alternateFuel = false;

                        if (rocketVariant.containsKey(player.getUniqueId()))
                            alternateFuel = rocketVariant.get(player.getUniqueId()).isAlternateFuel();

                        if (player.getLevel() < 3 && !alternateFuel) {

                            messageSend(getPlugin(), player, true, RB_FUEL_REQUIRE);
                            TitleSubtitle.subtitle(player, 3, RB_FUEL_REQUIRE);

                            player.setFlying(false);
                            event.setCancelled(true);

                        } else if (rocketZones.contains(player.getUniqueId())) {

                            if (rocketVariant.get(player.getUniqueId()) != RUNNER) {

                                if (!player.isFlying()) messageSend(getPlugin(), player, true, RB_FZ_CURRENT);
                                TitleSubtitle.subtitle(player, 3, RB_FZ_CURRENT);

                            }

                            player.setFlying(false);
                            event.setCancelled(true);

                        } else {

                            if (rocketPower.containsKey(player.getUniqueId())) {

                                Short rocketDurability = rocketBoots.getDurability();
                                Variant bootVariant = rocketVariant.get(player.getUniqueId());

                                int bootMaterialDurability = getBootDurability(rocketBoots);

                                if (rocketDurability >= bootMaterialDurability) {

                                    player.getWorld().createExplosion(player.getLocation(), 0.0f, false);
                                    player.getInventory().setBoots(new ItemStack(Material.AIR));
                                    disableRocketBoots(player, false, false, false, false, false, true);

                                    messageSend(getPlugin(), player, true, RB_EXPLODE);
                                    TitleSubtitle.subtitle(player, 3, RB_EXPLODE);

                                } else {

                                    if (!player.isFlying() && !rocketWater.contains(player.getUniqueId())) {

                                        if (!rocketSprint.containsKey(player.getUniqueId())) {

                                            if (player.getWorld().getName().equals("world_nether")) {

                                                if (bootVariant == ORIGINAL) {

                                                    GroundFire.setFire(player, "BOOST", Material.NETHERRACK);

                                                    player.getWorld().playSound(player.getEyeLocation(), Sound.EXPLODE, 0.8f, 0.8f);
                                                    player.setFlying(true);
                                                    player.setFlySpeed(rocketPower.get(player.getUniqueId()) * 0.03f);

                                                } else {

                                                    disableRocketBoots(player, false, true, false, true, true, true);
                                                    messageSend(getPlugin(), player, true, RB_NETHER);

                                                }

                                            } else {

                                                Boolean changeDurability = true;

                                                if (getBootPowerLevel(rocketBoots) == 10) {

                                                    int ran = new Random().nextInt(11);
                                                    if (ran == 5 || ran == 0) {

                                                        rocketSprint.put(player.getUniqueId(), "AIR");
                                                        player.getWorld().playSound(player.getLocation(), Sound.FIREWORK_BLAST, 0.5f, 0.7f);

                                                    } else {

                                                        player.setFlySpeed(0.3f);
                                                        player.setVelocity(new Vector(0, 2, 0));
                                                        player.setFlying(true);

                                                        player.getWorld().playSound(player.getLocation(), Sound.FIREWORK_LARGE_BLAST, 0.5f, 0.4f);
                                                        player.getWorld().playSound(player.getLocation(), Sound.FIREWORK_LARGE_BLAST2, 0.5f, 0.6f);

                                                    }

                                                } else {

                                                    player.setFlying(true);

                                                    player.getWorld().playSound(player.getEyeLocation(), bootVariant.getSound(), bootVariant.getVolume(), bootVariant.getPitch());
                                                    player.setVelocity(bootVariant.getVector());

                                                    switch (bootVariant) {

                                                        case HEALTH:
                                                            if (player.getHealth() <= 1.0 || player.getFoodLevel() <= 2) {
                                                                messageSend(getPlugin(), player, true, RB_HUNGRY);
                                                                changeDurability = false;
                                                                disableRocketBoots(player, false, true, false, true, true, true);
                                                            }
                                                            break;

                                                        case MONEY:
                                                            if (getVaultEconomy().getBalance(player) <= 10.0) {
                                                                messageSend(getPlugin(), player, true, RB_MONEY);
                                                                changeDurability = false;
                                                                disableRocketBoots(player, false, true, false, true, true, true);
                                                            }
                                                            break;

                                                        case KABOOM:
                                                            player.setVelocity(player.getLocation().getDirection().multiply(5));
                                                            player.setVelocity(player.getVelocity().setY(10));
                                                            break;

                                                        case COAL:
                                                            if (!player.getInventory().contains(Material.COAL_BLOCK))
                                                                if (!player.getInventory().contains(Material.COAL)) {
                                                                    messageSend(getPlugin(), player, true, FuelRequired("coal"));
                                                                    changeDurability = false;
                                                                    disableRocketBoots(player, false, false, false, false, false, true);
                                                                }
                                                            break;

                                                        case FURY:
                                                            if (!player.getInventory().contains(Material.REDSTONE_BLOCK))
                                                                if (!player.getInventory().contains(Material.REDSTONE)) {
                                                                    messageSend(getPlugin(), player, true, FuelRequired("redstone"));
                                                                    changeDurability = false;
                                                                    disableRocketBoots(player, false, false, false, false, false, true);
                                                                }
                                                            break;

                                                    }
                                                }

                                                if (changeDurability) {

                                                    player.setFlySpeed(rocketPower.get(player.getUniqueId()) * 0.0475f);
                                                    changeBootDurability(player, rocketBoots);

                                                }

                                            }

                                        }

                                    } else player.setFlying(false);

                                    rocketUsage.add(player.getUniqueId());

                                }

                            } else {

                                messageSend(getPlugin(), player, true, RB_ATTACH);
                                disableRocketBoots(player, false, false, false, false, false, true);

                            }

                        }

                    }

                }

            }

        }

    }

}
