package com.ullarah.urocket.event;

import com.ullarah.urocket.RocketFunctions;
import com.ullarah.urocket.function.*;
import org.bukkit.ChatColor;
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

import static com.ullarah.urocket.RocketInit.*;
import static com.ullarah.urocket.init.RocketEnhancement.Enhancement.UNLIMITED;
import static com.ullarah.urocket.init.RocketLanguage.*;
import static com.ullarah.urocket.init.RocketVariant.Variant;
import static com.ullarah.urocket.init.RocketVariant.Variant.ORIGINAL;
import static com.ullarah.urocket.init.RocketVariant.Variant.RUNNER;

public class ToggleFlight implements Listener {

    @EventHandler
    public void toggleRocketFlight(PlayerToggleFlightEvent event) {

        RocketFunctions rocketFunctions = new RocketFunctions();
        CommonString commonString = new CommonString();
        TitleSubtitle titleSubtitle = new TitleSubtitle();
        GamemodeCheck gamemodeCheck = new GamemodeCheck();
        GroundFire groundFire = new GroundFire();

        Player player = event.getPlayer();

        if (gamemodeCheck.check(player, GameMode.SURVIVAL, GameMode.ADVENTURE)) {

            if (rocketTimeout.contains(player.getUniqueId())) {
                player.setFlying(false);
                event.setCancelled(true);
                return;
            }

            ItemStack rocketBoots = player.getInventory().getBoots();
            ItemStack rocketFuelJacket = player.getInventory().getChestplate();

            if (rocketBoots == null) {
                commonString.messageSend(getPlugin(), player, true, RB_ATTACH);
                rocketFunctions.disableRocketBoots(player, false, false, false, false, false);
                event.setCancelled(true);
                return;
            }

            if (rocketFunctions.isValidRocketBoots(rocketBoots)) {

                Variant bootVariant = rocketVariant.get(player.getUniqueId());

                Material fuelSingle = bootVariant.getFuelSingle();
                Material fuelBlock = bootVariant.getFuelBlock();

                boolean isUnlimited = false;

                if (rocketEnhancement.containsKey(player.getUniqueId()))
                    if (rocketEnhancement.get(player.getUniqueId()).equals(UNLIMITED)) isUnlimited = true;

                if (!isUnlimited) if (fuelSingle != null && fuelBlock != null) {
                    if (!rocketJacket.contains(player.getUniqueId()) || rocketFuelJacket == null) {
                        commonString.messageSend(getPlugin(), player, true, ChatColor.RED + "Fuel Jacket not found!");
                        event.setCancelled(true);
                        return;
                    }
                }

                if (player.getLocation().getY() >= 250) {

                    rocketFunctions.disableRocketBoots(player, true, true, true, true, true);
                    commonString.messageSend(getPlugin(), player, true, RB_HIGH);

                } else if (player.getLocation().getY() <= 0) {

                    rocketFunctions.disableRocketBoots(player, true, true, true, true, true);
                    commonString.messageSend(getPlugin(), player, true, RB_LOW);

                } else {

                    if (rocketZones.contains(player.getUniqueId())) {

                        if (!bootVariant.equals(RUNNER)) {

                            if (!player.isFlying()) commonString.messageSend(getPlugin(), player, true, RB_FZ_CURRENT);
                            titleSubtitle.subtitle(player, 3, RB_FZ_CURRENT);

                        }

                        player.setFlying(false);
                        event.setCancelled(true);

                    } else {

                        if (rocketPower.containsKey(player.getUniqueId())) {

                            Short rocketDurability = rocketBoots.getDurability();
                            int bootMaterialDurability = rocketFunctions.getBootDurability(rocketBoots);

                            if (rocketDurability >= bootMaterialDurability) {

                                player.getWorld().createExplosion(player.getLocation(), 0.0f, false);
                                player.getInventory().setBoots(new ItemStack(Material.AIR));
                                rocketFunctions.disableRocketBoots(player, false, false, false, false, false);

                                commonString.messageSend(getPlugin(), player, true, RB_EXPLODE);
                                titleSubtitle.subtitle(player, 3, RB_EXPLODE);

                            } else {

                                if (!player.isFlying() && !rocketWater.contains(player.getUniqueId())) {

                                    if (!rocketSprint.containsKey(player.getUniqueId())) {

                                        if (!isUnlimited) if (fuelSingle != null && fuelBlock != null)
                                            if (!rocketFunctions.fuelCheck(player, fuelSingle, fuelBlock)) {
                                                player.setFlying(false);
                                                event.setCancelled(true);
                                                return;
                                            }

                                        if (player.getWorld().getName().equals("world_nether")) {

                                            if (bootVariant == ORIGINAL) {

                                                rocketFire.add(groundFire.setFire(player, "BOOST", Material.NETHERRACK));

                                                player.getWorld().playSound(player.getEyeLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.8f, 0.8f);
                                                player.setFlying(true);
                                                player.setFlySpeed(rocketPower.get(player.getUniqueId()) * 0.03f);

                                            }

                                        } else if (rocketFunctions.getBootPowerLevel(rocketBoots) == 10) {

                                            int ran = new Random().nextInt(11);
                                            if (ran == 5 || ran == 0) {

                                                rocketSprint.put(player.getUniqueId(), "AIR");
                                                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 0.5f, 0.7f);

                                            } else {

                                                player.setFlySpeed(0.3f);
                                                player.setVelocity(new Vector(0, 2, 0));
                                                player.setFlying(true);

                                                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_LARGE_BLAST, 0.5f, 0.4f);
                                                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_LARGE_BLAST_FAR, 0.5f, 0.6f);

                                            }

                                        } else {

                                            switch (bootVariant) {

                                                case HEALTH:
                                                    if (player.getHealth() <= 1.0 || player.getFoodLevel() <= 2) {
                                                        commonString.messageSend(getPlugin(), player, true, RB_HUNGRY);
                                                        player.setFlying(false);
                                                        event.setCancelled(true);
                                                        return;
                                                    }
                                                    player.setVelocity(bootVariant.getVector());
                                                    break;

                                                case MONEY:
                                                    if (getVaultEconomy().getBalance(player) <= 10.0) {
                                                        commonString.messageSend(getPlugin(), player, true, RB_MONEY);
                                                        player.setFlying(false);
                                                        event.setCancelled(true);
                                                        return;
                                                    }
                                                    player.setVelocity(bootVariant.getVector());
                                                    break;

                                                case KABOOM:
                                                    player.setVelocity(player.getLocation().getDirection().multiply(5));
                                                    player.setVelocity(player.getVelocity().setY(10));
                                                    break;

                                                case ENDER:
                                                    new RandomTeleport().teleport(player, new Random().nextInt(50));
                                                    break;

                                                case BOOST:
                                                    player.setVelocity(player.getVelocity().setY(10));

                                                default:
                                                    player.setVelocity(bootVariant.getVector());
                                                    break;

                                            }

                                            player.setFlying(true);

                                            player.getWorld().playSound(
                                                    player.getEyeLocation(),
                                                    bootVariant.getSound(),
                                                    bootVariant.getVolume(),
                                                    bootVariant.getPitch());

                                            player.setFlySpeed(rocketPower.get(player.getUniqueId()) * 0.045f);
                                            rocketFunctions.changeBootDurability(player, rocketBoots);

                                        }

                                    }

                                } else player.setFlying(false);

                                rocketUsage.add(player.getUniqueId());

                            }

                        } else {

                            commonString.messageSend(getPlugin(), player, true, RB_ATTACH);
                            rocketFunctions.disableRocketBoots(player, false, false, false, false, false);

                        }

                    }

                }

            }

        }

    }

}
