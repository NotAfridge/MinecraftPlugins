package com.ullarah.urocket.event;

import com.ullarah.urocket.RocketFunctions;
import com.ullarah.urocket.RocketInit;
import org.bukkit.Material;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PlayerInteractEntity implements Listener {

    @EventHandler
    public void playerInteraction(PlayerInteractEntityEvent event) {

        RocketFunctions rocketFunctions = new RocketFunctions();

        if (event.getRightClicked() instanceof Pig) {

            Pig pig = (Pig) event.getRightClicked();
            Player player = event.getPlayer();
            ItemStack inHand = player.getInventory().getItemInMainHand();
            UUID pigUUID = pig.getUniqueId();

            if (pig.hasSaddle()) {

                if (inHand.getType() == Material.AIR) {

                    if (player.isSneaking()) {

                        pig.setSaddle(false);
                        pig.getWorld().dropItemNaturally(pig.getEyeLocation(), new ItemStack(Material.SADDLE));

                        RocketInit.rocketEntity.remove(pigUUID);

                    }

                }

            } else if (rocketFunctions.isValidRocketSaddle(inHand)) RocketInit.rocketEntity.put(pigUUID, pig.getType());

        }

    }

}
