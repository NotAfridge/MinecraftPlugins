package com.ullarah.urocket.event;

import com.ullarah.ulib.function.FakeExplosion;
import com.ullarah.urocket.recipe.RocketFlyZone;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.hanging.HangingBreakEvent;

import java.util.List;
import java.util.stream.Collectors;

import static com.ullarah.urocket.RocketFunctions.reloadFlyZones;
import static com.ullarah.urocket.RocketInit.getMsgPrefix;
import static com.ullarah.urocket.RocketInit.getPlugin;
import static org.bukkit.event.hanging.HangingBreakEvent.RemoveCause.EXPLOSION;

public class ZoneDamage implements Listener {

    @EventHandler
    public void zoneCrystalDamage(EntityDamageByEntityEvent event) {

        if (event.getEntity() instanceof EnderCrystal) {

            final Entity zoneEntity = event.getEntity();

            Location entityLocation = zoneEntity.getLocation();

            int eX = entityLocation.getBlockX();
            int eY = entityLocation.getBlockY();
            int eZ = entityLocation.getBlockZ();

            List<String> zoneList = getPlugin().getConfig().getStringList("zones");
            List<String> newZoneList = zoneList.stream()
                    .map(zone -> zone.replaceFirst(".{37}", "")).collect(Collectors.toList());

            String zoneOriginal = event.getDamager().getUniqueId().toString() + "|"
                    + zoneEntity.getWorld().getName() + "|" + eX + "|" + eY + "|" + eZ;

            String zoneNew = zoneEntity.getWorld().getName() + "|" + eX + "|" + eY + "|" + eZ;

            if (zoneList.contains(zoneOriginal) || event.getDamager().hasPermission("rocket.remove")) {

                event.getDamager().sendMessage(getMsgPrefix() + ChatColor.GREEN
                        + "Rocket Fly Zone Controller removed successfully!");

                zoneList.remove(newZoneList.indexOf(zoneNew));
                zoneEntity.remove();

                FakeExplosion.create(entityLocation, 4, FakeExplosion.ExplosionType.LARGE);
                entityLocation.getWorld().strikeLightningEffect(entityLocation);

                zoneEntity.getWorld().dropItemNaturally(entityLocation, RocketFlyZone.zone());

                getPlugin().getConfig().set("zones", zoneList);
                getPlugin().saveConfig();

                reloadFlyZones(false);

            } else event.setCancelled(true);

        }

        if (event.getEntity() instanceof Entity) {
            if (event.getDamager() instanceof EnderCrystal)
                if (event.getEntity().getWorld().getName().equals("world")) event.setCancelled(true);

        }

    }

    @EventHandler
    public void zoneCrystalExplosionEntity(EntityExplodeEvent event) {
        if (event.getEntity() instanceof EnderCrystal) event.setCancelled(true);
    }

    @EventHandler
    public void zoneCrystalExplosionFrame(HangingBreakEvent event) {
        if (event.getCause() == EXPLOSION) event.setCancelled(true);
    }

}
