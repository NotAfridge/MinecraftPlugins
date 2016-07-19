package com.ullarah.urocket.event;

import com.ullarah.urocket.RocketInit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.UUID;

public class EntityDeath implements Listener {

    @EventHandler
    public void checkRocketEntity(EntityDeathEvent event) {

        EntityType entityType = event.getEntityType();
        UUID entityUUID = event.getEntity().getUniqueId();

        if (entityType == EntityType.HORSE || entityType == EntityType.PIG)
            if (RocketInit.rocketEntity.containsKey(entityUUID)) RocketInit.rocketEntity.remove(entityUUID);

    }

}