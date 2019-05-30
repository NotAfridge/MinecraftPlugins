package com.ullarah.urocket.event;

import com.ullarah.urocket.RocketFunctions;
import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.data.FlyLockout;
import com.ullarah.urocket.data.RocketPlayer;
import com.ullarah.urocket.function.*;
import com.ullarah.urocket.init.RocketEnhancement;
import com.ullarah.urocket.init.RocketLanguage;
import com.ullarah.urocket.init.RocketVariant;
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

public class ToggleFlight implements Listener {

    @EventHandler
    public void toggleRocketFlight(PlayerToggleFlightEvent event) {

        if (!event.isFlying())
            return;

        RocketFunctions rocketFunctions = new RocketFunctions();
        CommonString commonString = new CommonString();
        TitleSubtitle titleSubtitle = new TitleSubtitle();
        GamemodeCheck gamemodeCheck = new GamemodeCheck();
        GroundFire groundFire = new GroundFire();

        Player player = event.getPlayer();
        RocketPlayer rp = RocketInit.getPlayer(player);
        FlyLockout locks = rp.getLockouts();

        if (!gamemodeCheck.check(player, GameMode.SURVIVAL, GameMode.ADVENTURE))
            return;

        ItemStack rocketBoots = player.getInventory().getBoots();
        ItemStack rocketFuelJacket = player.getInventory().getChestplate();

        // Check for valid rocket boots
        if (!rocketFunctions.isValidRocketBoots(rocketBoots)) {
            // If no permission for lenient flight (flight without rboots), always enforce the wearing of boots
            if (!player.hasPermission("rocket.lenientflight")) {
                commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_ATTACH);
                rocketFunctions.disableRocketBoots(player, false);
                event.setCancelled(true);
            }
            return;
        }

        // Unknown boots? Get the player to re-equip
        if (rp.getBootData() == null) {
            commonString.messageSend(RocketInit.getPlugin(), player, true, ChatColor.RED + "Rocket Boots malfunction! Try putting them back on again.");
            event.setCancelled(true);
            return;
        }

        Integer power = rp.getBootData().getPower();
        RocketVariant.Variant variant = rp.getBootData().getVariant();
        RocketEnhancement.Enhancement enhancement = rp.getBootData().getEnhancement();

        boolean isUnlimited = (enhancement == RocketEnhancement.Enhancement.UNLIMITED);
        Material fuelSingle = variant.getFuelSingle();
        Material fuelBlock = variant.getFuelBlock();

        // Check if fuel jacket is required and equipped
        if (!isUnlimited) if (fuelSingle != null && fuelBlock != null) {
            if (!rp.isWearingJacket() || !rocketFunctions.isValidFuelJacket(rocketFuelJacket)) {
                commonString.messageSend(RocketInit.getPlugin(), player, true, ChatColor.RED + "Fuel Jacket not found!");
                event.setCancelled(true);
                return;
            }
        }

        // Height checks
        if (player.getLocation().getY() >= 250) {
            rocketFunctions.disableRocketBoots(player, true);
            commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_HIGH);
            event.setCancelled(true);
            return;
        }
        if (player.getLocation().getY() <= 2) {
            rocketFunctions.disableRocketBoots(player, true);
            commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_LOW);
            event.setCancelled(true);
            return;
        }

        // Is in some sort of lockout
        if (locks.isInLockout()) {

            // No-fly zone message
            if (locks.isInNoFlyZone()) {
                if (!variant.equals(RocketVariant.Variant.RUNNER)) {
                    commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_FZ_CURRENT);
                    titleSubtitle.subtitle(player, 3, RocketLanguage.RB_FZ_CURRENT);
                }
            }

            event.setCancelled(true);
            return;
        }

        short rocketDurability = rocketBoots.getDurability();
        int bootMaterialDurability = rocketFunctions.getBootDurability(rocketBoots);

        if (rocketDurability >= bootMaterialDurability) {

            player.getWorld().createExplosion(player.getLocation(), 0.0f, false);
            player.getInventory().setBoots(new ItemStack(Material.AIR));
            rocketFunctions.disableRocketBoots(player, false);

            commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_EXPLODE);
            titleSubtitle.subtitle(player, 3, RocketLanguage.RB_EXPLODE);

            event.setCancelled(true);
            return;

        }

        // Check there's enough fuel
        if (!isUnlimited) if (fuelSingle != null && fuelBlock != null)
            if (!rocketFunctions.fuelCheck(player, fuelSingle, fuelBlock)) {
                event.setCancelled(true);
                return;
            }

        if (player.getWorld().getName().equals("world_nether")) {

            if (variant == RocketVariant.Variant.ORIGINAL) {

                RocketInit.rocketFire.add(groundFire.setFire(player, "BOOST", Material.NETHERRACK));

                player.getWorld().playSound(player.getEyeLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.8f, 0.8f);
                player.setFlying(true);
                player.setFlySpeed(power * 0.03f);

            }

        } else if (rocketFunctions.getBootPowerLevel(rocketBoots) == 10) {

            int ran = new Random().nextInt(11);
            if (ran == 5 || ran == 0) {

                locks.setSprintLock(FlyLockout.Sprint.AIR);
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 0.5f, 0.7f);

            } else {

                player.setFlySpeed(0.3f);
                player.setVelocity(new Vector(0, 2, 0));
                player.setFlying(true);

                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, 0.5f, 0.4f);
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST_FAR, 0.5f, 0.6f);

            }

        } else {

            switch (variant) {

                case HEALTH:
                    if (player.getHealth() <= 1.0 || player.getFoodLevel() <= 2) {
                        commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_HUNGRY);
                        player.setFlying(false);
                        event.setCancelled(true);
                        return;
                    }
                    player.setVelocity(variant.getVector());
                    break;

                case MONEY:
                    if (RocketInit.getVaultEconomy().getBalance(player) <= 10.0) {
                        commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_MONEY);
                        player.setFlying(false);
                        event.setCancelled(true);
                        return;
                    }
                    player.setVelocity(variant.getVector());
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
                    player.setVelocity(variant.getVector());
                    break;

            }

            player.setFlying(true);

            player.getWorld().playSound(
                    player.getEyeLocation(),
                    variant.getSound(),
                    variant.getVolume(),
                    variant.getPitch());

            player.setFlySpeed(power * 0.045f);
            rocketFunctions.changeBootDurability(player, rocketBoots);

        }

        rp.setUsingBoots(true);

    }

}
