package com.ullarah.urocket.event;

import com.ullarah.urocket.RocketFunctions;
import org.bukkit.Material;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

import static com.ullarah.urocket.RocketInit.rocketEntity;

public class PlayerInteractEntity implements Listener {

    @EventHandler
    public void playerInteraction(PlayerInteractEntityEvent event) {

        RocketFunctions rocketFunctions = new RocketFunctions();

        if (event.getRightClicked() instanceof Pig) {

            Pig pig = (Pig) event.getRightClicked();
            ItemStack inHand = event.getPlayer().getItemInHand();
            Player player = event.getPlayer();
            UUID pigUUID = pig.getUniqueId();

            if (pig.hasSaddle()) {

                if (inHand.getType() == Material.AIR) {

                    if (player.isSneaking()) {

                        pig.setSaddle(false);
                        pig.getWorld().dropItemNaturally(pig.getEyeLocation(), new ItemStack(Material.SADDLE));

                        if (rocketEntity.containsKey(pigUUID)) rocketEntity.remove(pigUUID);

                    }

                }

            } else if (rocketFunctions.isValidRocketSaddle(inHand)) rocketEntity.put(pigUUID, pig.getType());

        }

    }

}
