package com.ullarah.urocket.event;

import com.ullarah.urocket.RocketFunctions;
import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.function.CommonString;
import com.ullarah.urocket.function.FakeExplosion;
import com.ullarah.urocket.function.IDTag;
import com.ullarah.urocket.init.RocketLanguage;
import com.ullarah.urocket.recipe.RocketFlyZone;
import org.bukkit.Location;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.hanging.HangingBreakEvent;

import java.util.List;
import java.util.stream.Collectors;

public class ZoneDamage implements Listener {

    @EventHandler
    public void zoneCrystalDamage(EntityDamageByEntityEvent event) {

        if (event.getEntity().getWorld().getName().equals("world")) {

            if (event.getEntity() instanceof Entity)
                if (event.getDamager() instanceof EnderCrystal)
                    event.setCancelled(true);

            if (event.getEntity() instanceof EnderCrystal) {

                final Entity zoneEntity = event.getEntity();

                Location entityLocation = zoneEntity.getLocation();

                List<String> zoneList = RocketInit.getPlugin().getConfig().getStringList("zones");
                List<String> newZoneList = zoneList.stream().map(zone -> zone.replaceFirst(".{37}", "")).collect(Collectors.toList());

                String zoneOriginal = new IDTag().create(event.getDamager(), entityLocation);
                String zoneNew = new IDTag().create(entityLocation);

                // Only allow players to break zones
                if (newZoneList.contains(zoneNew) && !(event.getDamager() instanceof Player)) {
                    event.setCancelled(true);
                    return;
                }

                if (zoneList.contains(zoneOriginal) || (event.getDamager().hasPermission("rocket.remove") && newZoneList.contains(zoneNew))) {

                    RocketFunctions rocketFunctions = new RocketFunctions();
                    CommonString commonString = new CommonString();
                    FakeExplosion fakeExplosion = new FakeExplosion();

                    commonString.messageSend(RocketInit.getPlugin(), event.getDamager(), true, RocketLanguage.RB_FZ_REMOVE);

                    zoneList.remove(newZoneList.indexOf(zoneNew));
                    zoneEntity.remove();

                    fakeExplosion.create(entityLocation, 4, FakeExplosion.ExplosionType.LARGE);
                    entityLocation.getWorld().strikeLightningEffect(entityLocation);

                    zoneEntity.getWorld().dropItemNaturally(entityLocation, new RocketFlyZone().zone());

                    RocketInit.getPlugin().getConfig().set("zones", zoneList);
                    RocketInit.getPlugin().saveConfig();

                    rocketFunctions.reloadFlyZones(false);

                } else event.setCancelled(true);

            }

        }

    }

    @EventHandler
    public void zoneCrystalExplosionEntity(EntityExplodeEvent event) {
        if (event.getLocation().getWorld().getName().equals("world"))
            if (event.getEntity() instanceof EnderCrystal) event.setCancelled(true);
    }

    @EventHandler
    public void zoneCrystalExplosionFrame(HangingBreakEvent event) {
        if (event.getEntity().getWorld().getName().equals("world"))
            if (event.getCause() == HangingBreakEvent.RemoveCause.EXPLOSION) event.setCancelled(true);
    }

}
