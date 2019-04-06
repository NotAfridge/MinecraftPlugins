package com.ullarah.urocket.event;

import com.ullarah.urocket.RocketFunctions;
import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.data.SprintLockout;
import com.ullarah.urocket.function.CommonString;
import com.ullarah.urocket.init.RocketLanguage;
import com.ullarah.urocket.init.RocketVariant;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class ToggleSprint implements Listener {

    @EventHandler
    public void toggleRocketSprint(PlayerToggleSprintEvent event) {

        // We only care if we're starting to sprint
        if (!event.isSprinting()) {
            return;
        }

        RocketFunctions rocketFunctions = new RocketFunctions();
        CommonString commonString = new CommonString();

        final Player player = event.getPlayer();
        final UUID playerUUID = player.getUniqueId();

        if (RocketInit.rocketPower.containsKey(playerUUID)) {

            // If not already in the lock-out
            if (!RocketInit.rocketSprint.containsKey(playerUUID)) {

                boolean runnerBoots = RocketInit.rocketVariant.get(playerUUID) == RocketVariant.Variant.RUNNER;

                // Sprinting in runner boots is allowed
                if (runnerBoots) {

                    ItemStack boots = player.getInventory().getBoots();
                    int bootPower = rocketFunctions.getBootPowerLevel(boots);

                    // Only use up durability when no speed applied
                    if (!player.hasPotionEffect(PotionEffectType.SPEED))
                        rocketFunctions.changeBootDurability(player, boots);

                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, bootPower * 120, bootPower * 3, true, false), true);
                    player.getWorld().playSound(player.getEyeLocation(), Sound.BLOCK_PISTON_EXTEND, 1.25f, 0.75f);

                }
                // Sprint flying in non-runner boots
                else if (player.isFlying()) {

                    RocketInit.rocketSprint.put(playerUUID, SprintLockout.AIR);

                    commonString.messageSend(RocketInit.getPlugin(), player, true, new String[]{
                            RocketLanguage.RB_COOLDOWN_HEAT, RocketLanguage.RB_COOLDOWN_LAND
                    });

                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 0.5f, 0.75f);

                }
                // Sprinting on the ground in non-runner boots
                else {

                    RocketInit.rocketSprint.put(playerUUID, SprintLockout.LAND);
                    commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_SPRINT);

                }

            }

        }

    }

}
