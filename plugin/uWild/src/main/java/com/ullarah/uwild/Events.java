package com.ullarah.uwild;

import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Random;

public class Events implements Listener {

    @SuppressWarnings("unused")
    @EventHandler
    public void mobSpawn(final CreatureSpawnEvent event){

        Entity entity = event.getEntity();

        if( new Random().nextInt(99) + 1 > 50 ) {

            entity.setMetadata("uWild", new FixedMetadataValue(Init.getPlugin(), true));

            switch (event.getEntityType()) {

                case CREEPER:
                    entity.setCustomName("Treeper");
                    entity.setCustomNameVisible(true);
                    break;

                case CHICKEN:
                    entity.setCustomName("Unstable Chicken");
                    entity.setCustomNameVisible(true);
                    break;

            }
        }

    }

    @SuppressWarnings("unused")
    @EventHandler
    public void mobDeath(final EntityDeathEvent event){

        Entity entity = event.getEntity();
        Location location = entity.getLocation();
        World world = location.getWorld();

        if(entity.hasMetadata("uWild")) switch (event.getEntityType()) {

            case CREEPER:
                if (entity.getCustomName().equalsIgnoreCase("Treeper")) {
                    world.createExplosion(location, 0.0F);
                    world.generateTree(location, TreeType.TREE);
                }
                break;

            case CHICKEN:
                if (entity.getCustomName().equalsIgnoreCase("Unstable Chicken"))
                    world.createExplosion(location, 2.0F, true);
                break;

        }

    }

}
