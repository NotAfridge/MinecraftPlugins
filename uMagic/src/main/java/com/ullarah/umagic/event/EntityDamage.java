package com.ullarah.umagic.event;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamage extends MagicFunctions implements Listener {

    public EntityDamage() {
        super(false);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void event(EntityDamageByEntityEvent event) {

        if (event.getDamager() instanceof Player) {

            Player player = (Player) event.getDamager();

            if (usingMagicHoe(player)) event.setCancelled(true);

        }

    }

}
