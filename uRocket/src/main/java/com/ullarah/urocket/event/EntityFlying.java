package com.ullarah.urocket.event;

import com.ullarah.urocket.RocketFunctions;
import com.ullarah.urocket.RocketInit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class EntityFlying implements Listener {

    @EventHandler
    public void enableFlyingHorse(HorseJumpEvent event) {

        RocketFunctions rocketFunctions = new RocketFunctions();

        if (RocketInit.rocketEntity.containsKey(event.getEntity().getUniqueId())) {

            Horse horse = event.getEntity();

            ItemStack saddle = event.getEntity().getInventory().getSaddle();

            if (rocketFunctions.isValidRocketSaddle(saddle)) {

                Vector horseVelocity = horse.getVelocity();
                double horseJump = event.getPower() * event.getEntity().getJumpStrength();

                if (horseVelocity.getX() > 0.45) horseVelocity.setX(0.45);
                if (horseVelocity.getX() < -0.45) horseVelocity.setX(-0.45);

                if (horseVelocity.getY() > 1.0) horseVelocity.setY(1.0);

                if (horseVelocity.getZ() > 0.45) horseVelocity.setZ(0.45);
                if (horseVelocity.getZ() < -0.45) horseVelocity.setZ(-0.45);

                horse.setVelocity(new Vector(horseVelocity.getX() * 1.5, horseJump, horseVelocity.getZ() * 1.5));

            }

        }

    }

    @EventHandler
    public void entityLandingDamage(EntityDamageEvent event) {

        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {

            if (event.getEntity() instanceof Horse)
                if (RocketInit.rocketEntity.containsKey(event.getEntity().getUniqueId()))
                    event.setCancelled(true);

            if (event.getEntity() instanceof Pig)
                if (RocketInit.rocketEntity.containsKey(event.getEntity().getUniqueId()))
                    event.setCancelled(true);

            if (event.getEntity() instanceof Player) {

                Player player = (Player) event.getEntity();

                if (player.isInsideVehicle()) {

                    EntityType entityType = player.getVehicle().getType();

                    if (entityType == EntityType.HORSE || entityType == EntityType.PIG)
                        if (RocketInit.rocketEntity.containsKey(event.getEntity().getUniqueId()))
                            event.setCancelled(true);

                }

            }

        }

    }

}
