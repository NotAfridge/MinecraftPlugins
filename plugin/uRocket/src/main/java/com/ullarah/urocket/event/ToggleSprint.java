package com.ullarah.urocket.event;

import com.ullarah.ulib.function.CommonString;
import com.ullarah.urocket.RocketFunctions;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

import static com.ullarah.urocket.RocketInit.*;
import static com.ullarah.urocket.RocketLanguage.*;
import static com.ullarah.urocket.RocketVariant.Variant.RUNNER;

public class ToggleSprint implements Listener {

    @EventHandler
    public void toggleRocketSprint(PlayerToggleSprintEvent event) {

        RocketFunctions rocketFunctions = new RocketFunctions();
        CommonString commonString = new CommonString();

        if (event.isSprinting()) {

            final Player player = event.getPlayer();
            UUID playerUUID = player.getUniqueId();

            if (rocketPower.containsKey(playerUUID)) {

                if (!rocketSprint.containsKey(playerUUID)) {

                    if (player.isFlying() && rocketVariant.get(playerUUID) != RUNNER) {

                        rocketSprint.put(playerUUID, "AIR");

                        commonString.messageSend(getPlugin(), player, true, new String[]{
                                RB_COOLDOWN_HEAT, RB_COOLDOWN_LAND
                        });

                        player.getWorld().playSound(player.getLocation(), Sound.FIREWORK_BLAST, 0.5f, 0.75f);

                    } else if (rocketVariant.get(playerUUID) == RUNNER) {

                        ItemStack boots = player.getInventory().getBoots();
                        int bootPower = rocketFunctions.getBootPowerLevel(boots);

                        if (!player.hasPotionEffect(PotionEffectType.SPEED))
                            rocketFunctions.changeBootDurability(player, boots);

                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, bootPower * 120, bootPower * 3, true, false), true);
                        player.getWorld().playSound(player.getEyeLocation(), Sound.PISTON_EXTEND, 1.25f, 0.75f);

                    } else {

                        rocketSprint.put(playerUUID, "LAND");
                        commonString.messageSend(getPlugin(), player, true, RB_SPRINT);

                    }

                }

            }

        }

    }

}
