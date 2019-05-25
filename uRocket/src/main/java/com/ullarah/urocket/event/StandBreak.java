package com.ullarah.urocket.event;

import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.function.IDTag;
import com.ullarah.urocket.recipe.RepairStand;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

public class StandBreak implements Listener {

    @EventHandler
    public void RocketArmourStandBreak(EntityDamageByEntityEvent event) {

        if (event.getEntityType().equals(EntityType.ARMOR_STAND)) {

            final LivingEntity standEntity = (LivingEntity) event.getEntity();

            Location entityLocation = standEntity.getLocation();

            List<String> standList = RocketInit.getPlugin().getConfig().getStringList("stands");
            List<String> newStandList = standList.stream().map(stand -> stand.replaceFirst(".{37}", "")).collect(Collectors.toList());

            String standOriginal = new IDTag().create(event.getDamager(), entityLocation);
            String standNew = new IDTag().create(entityLocation);

            // Only allow players to break stands
            if (newStandList.contains(standNew) && !(event.getDamager() instanceof Player)) {
                event.setCancelled(true);
                return;
            }

            if (standList.contains(standOriginal) || (event.getDamager().hasPermission("rocket.remove") && newStandList.contains(standNew))) {

                event.setCancelled(true);

                // Drop all items on the armor stand
                for (ItemStack stack : standEntity.getEquipment().getArmorContents()) {
                    if (stack != null && stack.getType() != Material.AIR) {
                        standEntity.getWorld().dropItemNaturally(entityLocation, stack);
                    }
                }

                // Drop the enchanted stand
                standEntity.getWorld().dropItemNaturally(entityLocation, new RepairStand().stand());

                // Remove from the list and explicitly remove the entity, not kill it.
                standList.remove(newStandList.indexOf(standNew));
                standEntity.remove();

                RocketInit.getPlugin().getConfig().set("stands", standList);
                RocketInit.getPlugin().saveConfig();

            }

        }

    }

}
