package com.ullarah.urocket.event;

import com.ullarah.urocket.recipe.RepairStand;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.ullarah.urocket.RocketInit.getPlugin;

public class StandBreak implements Listener {

    @EventHandler
    public void RocketArmourStandBreak(EntityDamageByEntityEvent event) {

        if (event.getEntityType().equals(EntityType.ARMOR_STAND)) {

            final LivingEntity standEntity = (LivingEntity) event.getEntity();

            Location entityLocation = standEntity.getLocation();

            int eX = entityLocation.getBlockX();
            int eY = entityLocation.getBlockY();
            int eZ = entityLocation.getBlockZ();

            List<String> standList = getPlugin().getConfig().getStringList("stands");
            List<String> newStandList = new ArrayList<>();

            for (String stand : standList) newStandList.add(stand.replaceFirst(".{37}", ""));

            String standOriginal = event.getDamager().getUniqueId().toString() + "|"
                    + standEntity.getWorld().getName() + "|" + eX + "|" + eY + "|" + eZ;

            String standNew = standEntity.getWorld().getName() + "|" + eX + "|" + eY + "|" + eZ;

            if (standList.contains(standOriginal) || event.getDamager().hasPermission("rocket.remove")) {

                if (standEntity.getEquipment().getBoots().getAmount() == 1) {
                    ItemStack standBoots = standEntity.getEquipment().getBoots();
                    if (standBoots.getType() != Material.AIR)
                        standEntity.getWorld().dropItemNaturally(entityLocation, standBoots);
                }

                standEntity.getWorld().dropItemNaturally(entityLocation, RepairStand.stand());

                standList.remove(newStandList.indexOf(standNew));
                standEntity.remove();

                getPlugin().getConfig().set("stands", standList);
                getPlugin().saveConfig();

            }

        }

    }

}
