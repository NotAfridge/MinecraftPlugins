package com.ullarah.urocket.event;

import com.ullarah.urocket.RocketFunctions;
import com.ullarah.urocket.RocketInit;
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

        RocketFunctions rocketFunctions = new RocketFunctions();
        CommonString commonString = new CommonString();

        if (event.isSprinting()) {

            final Player player = event.getPlayer();
            UUID playerUUID = player.getUniqueId();

            if (RocketInit.rocketPower.containsKey(playerUUID)) {

                if (!RocketInit.rocketSprint.containsKey(playerUUID)) {

                    if (player.isFlying() && RocketInit.rocketVariant.get(playerUUID) != RocketVariant.Variant.RUNNER) {

                        RocketInit.rocketSprint.put(playerUUID, "AIR");

                        commonString.messageSend(RocketInit.getPlugin(), player, true, new String[]{
                                RocketLanguage.RB_COOLDOWN_HEAT, RocketLanguage.RB_COOLDOWN_LAND
                        });

                        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 0.5f, 0.75f);

                    } else if (RocketInit.rocketVariant.get(playerUUID) == RocketVariant.Variant.RUNNER) {

                        ItemStack boots = player.getInventory().getBoots();
                        int bootPower = rocketFunctions.getBootPowerLevel(boots);

                        if (!player.hasPotionEffect(PotionEffectType.SPEED))
                            rocketFunctions.changeBootDurability(player, boots);

                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, bootPower * 120, bootPower * 3, true, false), true);
                        player.getWorld().playSound(player.getEyeLocation(), Sound.BLOCK_PISTON_EXTEND, 1.25f, 0.75f);

                    } else {

                        RocketInit.rocketSprint.put(playerUUID, "LAND");
                        commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_SPRINT);

                    }

                }

            }

        }

    }

}
