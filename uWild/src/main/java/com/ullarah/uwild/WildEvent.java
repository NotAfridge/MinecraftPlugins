package com.ullarah.uwild;

import org.bukkit.*;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class WildEvent implements Listener {

    @EventHandler
    public void mobSpawn(final CreatureSpawnEvent event){

        List<SpawnReason> spawnReasons = new ArrayList<>();
        spawnReasons.add(SpawnReason.NATURAL);
        spawnReasons.add(SpawnReason.SPAWNER_EGG);
        spawnReasons.add(SpawnReason.CHUNK_GEN);

        if (spawnReasons.contains(event.getSpawnReason())) {

            if( new Random().nextInt(99) + 1 > 50 ) {

                Entity entity = event.getEntity();

                Boolean isWild = false;
                String entityWildType = "";
                List<String> entityArray = new ArrayList<>();

                switch (event.getEntityType()) {

                    case SPIDER:
                        isWild = true;
                        entityWildType = ChatColor.YELLOW + "Spyker";
                        break;

                    case CREEPER:
                        isWild = true;
                        entityArray.add(ChatColor.GREEN + "Treeper");
                        entityArray.add(ChatColor.BLUE + "H2O Creeper");
                        entityArray.add(ChatColor.RED + "Volcanic Creeper");
                        entityWildType = entityArray.get(new Random().nextInt(entityArray.size()));
                        break;

                    case CHICKEN:
                        isWild = true;
                        entityWildType = ChatColor.RED + "Unstable Chicken";
                        break;

                    case SHEEP:
                        isWild = true;
                        entityWildType = ChatColor.LIGHT_PURPLE + "Time Sheep";
                        break;

                    case COW:
                        isWild = true;
                        entityWildType = ChatColor.AQUA + "Cow of Zeus";
                        break;

                    case PIG:
                        isWild = true;
                        entityWildType = ChatColor.LIGHT_PURPLE + "Oinkers";
                        break;

                }

                if (isWild) {

                    entity.setMetadata("uWild", new FixedMetadataValue(WildInit.getPlugin(), true));
                    entity.setCustomName(entityWildType);
                    entity.setCustomNameVisible(false);

                    switch (event.getEntityType()) {

                        case SPIDER:
                            if (entity.getCustomName().matches(ChatColor.YELLOW + "Spyker")) {

                                Entity passenger = entity.getWorld().spawnEntity(entity.getLocation(), EntityType.CAVE_SPIDER);

                                passenger.setSilent(true);
                                passenger.setCustomName(ChatColor.YELLOW + "Spyker");
                                passenger.setCustomNameVisible(false);

                                entity.setPassenger(passenger);
                                entity.setSilent(true);

                            }
                            break;

                        case PIG:
                            if (entity.getCustomName().matches(ChatColor.LIGHT_PURPLE + "Oinkers")) {

                                Entity pigOne = entity.getWorld().spawnEntity(entity.getLocation(), EntityType.PIG);
                                Entity pigTwo = entity.getWorld().spawnEntity(entity.getLocation(), EntityType.PIG);

                                pigOne.setCustomName(ChatColor.LIGHT_PURPLE + "Oinkers");
                                pigTwo.setCustomName(ChatColor.LIGHT_PURPLE + "Oinkers");

                                pigOne.setCustomNameVisible(false);
                                pigTwo.setCustomNameVisible(false);

                                pigOne.setPassenger(pigTwo);
                                entity.setPassenger(pigOne);

                            }
                            break;

                    }

                }

            }

        }

    }

    @EventHandler
    public void mobExplode(final EntityExplodeEvent event){

        if (event.getEntity() instanceof Creeper) creeperDeath(event.getEntity());

    }

    @EventHandler
    public void mobDeath(final EntityDeathEvent event){

        Entity entity = event.getEntity();
        Location location = entity.getLocation();
        World world = location.getWorld();

        if(entity.hasMetadata("uWild")) switch (event.getEntityType()) {

            case CREEPER:
                creeperDeath(entity);
                break;

            case CHICKEN:
                if (entity.getCustomName().matches(ChatColor.RED + "Unstable Chicken"))
                    world.createExplosion(location, 2.0F, true);
                break;

            case SHEEP:
                if (entity.getCustomName().matches(ChatColor.LIGHT_PURPLE + "Time Sheep"))
                    world.setTime(new Random().nextInt(23000));
                break;

            case COW:
                if (entity.getCustomName().matches(ChatColor.AQUA + "Cow of Zeus"))
                    world.strikeLightning(location);
                break;

            case PIG:
                if (entity.getCustomName().matches(ChatColor.LIGHT_PURPLE + "Oinkers"))
                    world.spawnParticle(Particle.DRAGON_BREATH, location, 500, 3.0, 3.0, 3.0);
                break;

        }

    }

    private void creeperDeath(Entity entity) {

        Location location = entity.getLocation();
        World world = location.getWorld();

        int locX = (int) location.getX();
        int locY = (int) location.getY();
        int locZ = (int) location.getZ();

        world.createExplosion(location, 0.0F);

        if (entity.getCustomName().matches(ChatColor.GREEN + "Treeper")) {
            world.generateTree(location, new Random().nextInt(10) == 5 ? TreeType.BIG_TREE : TreeType.TREE);
        }

        if (entity.getCustomName().matches(ChatColor.BLUE + "H2O Creeper")) {
            world.getBlockAt(locX, locY, locZ).setType(Material.WATER);
            world.getBlockAt(locX, locY + 1, locZ).setType(Material.WATER);
        }

        if (entity.getCustomName().matches(ChatColor.RED + "Volcanic Creeper")) {
            world.getBlockAt(locX, locY, locZ).setType(Material.LAVA);
            world.getBlockAt(locX, locY + 1, locZ).setType(Material.LAVA);
        }

    }

}
