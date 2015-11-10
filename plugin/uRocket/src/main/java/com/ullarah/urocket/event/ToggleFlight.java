package com.ullarah.urocket.event;

import com.ullarah.ulib.function.GamemodeCheck;
import com.ullarah.ulib.function.GroundFire;
import com.ullarah.ulib.function.TitleSubtitle;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.Random;

import static com.ullarah.urocket.RocketFunctions.*;
import static com.ullarah.urocket.RocketInit.*;
import static com.ullarah.urocket.RocketVariant.Variant;
import static com.ullarah.urocket.RocketVariant.Variant.ORIGINAL;
import static com.ullarah.urocket.RocketVariant.Variant.RUNNER;

public class ToggleFlight implements Listener {

    @EventHandler
    public void toggleRocketFlight(PlayerToggleFlightEvent event) {

        Player player = event.getPlayer();
        ItemStack rocketBoots = player.getInventory().getBoots();
        Boolean changeDurability = true;

        if (GamemodeCheck.check(player, GameMode.SURVIVAL, GameMode.ADVENTURE)) {

            if (rocketBoots == null) {

                player.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "You need to re-attach your Rocket Boots!");
                disableRocketBoots(player, false, false, false, false, false);

            } else {

                if (rocketBoots.hasItemMeta()) {

                    ItemMeta rocketMeta = rocketBoots.getItemMeta();

                    if (rocketMeta.hasDisplayName()) {

                        if (rocketMeta.getDisplayName().matches(ChatColor.RED + "Rocket Boots")) {

                            if (rocketMeta.hasLore()) {

                                String rocketLore = rocketMeta.getLore().get(0);

                                if (rocketLore.matches(ChatColor.YELLOW + "Rocket Level I{0,3}V?X?")) {

                                    if (player.getLocation().getY() >= 250) {

                                        disableRocketBoots(player, true, true, true, true, true);
                                        player.sendMessage(getMsgPrefix() + "Rocket Boots don't work so well up high!");

                                    } else if (player.getLocation().getY() <= 0) {

                                        disableRocketBoots(player, true, true, true, true, true);
                                        player.sendMessage(getMsgPrefix() + "Rocket Boots don't work so in the void!");

                                    } else {

                                        boolean alternateFuel = false;

                                        if (rocketVariant.containsKey(player.getUniqueId()))
                                            alternateFuel = rocketVariant.get(player.getUniqueId()).isAlternateFuel();

                                        if (player.getLevel() < 3 && !alternateFuel) {

                                            player.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "You need more XP to start your Rocket Boots!");

                                            TitleSubtitle.subtitle(player, 3, ChatColor.YELLOW + "You need more XP!");

                                            player.setFlying(false);
                                            event.setCancelled(true);

                                        } else if (rocketZones.contains(player.getUniqueId())) {

                                            if (rocketVariant.get(player.getUniqueId()) != RUNNER) {

                                                if (!player.isFlying())
                                                    player.sendMessage(getMsgPrefix() + ChatColor.RED + "You are currently in a No-Fly Zone!");

                                                TitleSubtitle.subtitle(player, 3, ChatColor.RED + "You are in a No-Fly Zone!");

                                            }

                                            player.setFlying(false);
                                            event.setCancelled(true);

                                        } else {

                                            if (rocketPower.containsKey(player.getUniqueId())) {

                                                Short rocketDurability = rocketBoots.getDurability();
                                                Variant bootVariant = rocketVariant.get(player.getUniqueId());

                                                int bootMaterialDurability = 0;

                                                switch (rocketBoots.getType()) {

                                                    case LEATHER_BOOTS:
                                                        bootMaterialDurability = 65;
                                                        break;

                                                    case IRON_BOOTS:
                                                        bootMaterialDurability = 195;
                                                        break;

                                                    case GOLD_BOOTS:
                                                        bootMaterialDurability = 91;
                                                        break;

                                                    case DIAMOND_BOOTS:
                                                        bootMaterialDurability = 429;
                                                        break;

                                                }

                                                if (rocketDurability >= bootMaterialDurability) {

                                                    player.getWorld().createExplosion(player.getLocation(), 0.0f, false);
                                                    player.getInventory().setBoots(new ItemStack(Material.AIR));
                                                    disableRocketBoots(player, false, false, false, false, false);

                                                    player.sendMessage(getMsgPrefix() + "Your Rocket Boots exploded!");
                                                    TitleSubtitle.subtitle(player, 3, ChatColor.BOLD + "Your Rocket Boots exploded!");

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

                                                                    disableRocketBoots(player, false, true, false, true, true);
                                                                    player.sendMessage(getMsgPrefix() + ChatColor.RED + "These Rocket Boots don't work in the Nether!");
                                                                    
                                                                }

                                                            } else {

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

                                                                                player.sendMessage(getMsgPrefix() + "Too hungry to fly...");
                                                                                changeDurability = false;

                                                                                disableRocketBoots(player, false, true, false, true, true);

                                                                            }
                                                                            break;

                                                                        case KABOOM:
                                                                            player.setVelocity(player.getLocation().getDirection().multiply(5));
                                                                            player.setVelocity(player.getVelocity().setY(10));
                                                                            break;

                                                                        case COAL:
                                                                            if (!player.getInventory().contains(Material.COAL) || !player.getInventory().contains(Material.COAL_BLOCK)) {

                                                                                player.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "You need coal to launch these boots!");
                                                                                player.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "Get more coal, and re-attach the boots!");

                                                                                changeDurability = false;
                                                                                disableRocketBoots(player, false, false, false, false, false);

                                                                            }
                                                                            break;

                                                                        case FURY:
                                                                            if (!player.getInventory().contains(Material.REDSTONE) || !player.getInventory().contains(Material.REDSTONE_BLOCK)) {

                                                                                player.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "You need redstone to launch these boots!");
                                                                                player.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "Get more redstone, and re-attach the boots!");

                                                                                changeDurability = false;
                                                                                disableRocketBoots(player, false, false, false, false, false);

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

                                                player.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "You need to re-attach your Rocket Boots!");
                                                disableRocketBoots(player, false, false, false, false, false);

                                            }

                                        }

                                    }

                                }

                            }

                        }

                    }

                }

            }

        }

    }

}
