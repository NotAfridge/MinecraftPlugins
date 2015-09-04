package com.ullarah.urocket.event;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

import static com.ullarah.urocket.RocketFunctions.Variant.RUNNER;
import static com.ullarah.urocket.RocketFunctions.changeBootDurability;
import static com.ullarah.urocket.RocketFunctions.getBootPowerLevel;
import static com.ullarah.urocket.RocketInit.*;

public class ToggleSprint implements Listener {

    @EventHandler
    public void toggleRocketSprint(PlayerToggleSprintEvent event) {

        final Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (event.isSprinting()) {

            if (rocketPower.containsKey(playerUUID)) {

                if (!rocketSprint.containsKey(playerUUID)) {

                    if (player.isFlying() && rocketVariant.get(playerUUID) != RUNNER) {

                        rocketSprint.put(playerUUID, "AIR");

                        player.sendMessage(new String[]{
                                getMsgPrefix() + ChatColor.RED +
                                        "Uh Oh! Your Rocket Boots have overheated!",
                                getMsgPrefix() + ChatColor.RESET +
                                        "You need to land for them to cooldown!"
                        });

                        player.getWorld().playSound(player.getLocation(), Sound.FIREWORK_BLAST, 0.5f, 0.75f);

                    } else if (rocketVariant.get(playerUUID) == RUNNER) {

                        ItemStack boots = player.getInventory().getBoots();
                        int bootPower = getBootPowerLevel(boots);

                        if (!player.hasPotionEffect(PotionEffectType.SPEED)) changeBootDurability(player, boots);

                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
                                bootPower * 120, bootPower * 3, true, false), true);

                        player.getWorld().playSound(player.getEyeLocation(),
                                Sound.PISTON_EXTEND, 1.25f, 0.75f);

                    } else {

                        rocketSprint.put(playerUUID, "LAND");
                        player.sendMessage(getMsgPrefix() + "You cannot run in your Rocket Boots!");

                    }

                }

            }

        }

    }

}
