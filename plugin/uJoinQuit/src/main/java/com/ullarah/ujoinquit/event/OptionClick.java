package com.ullarah.ujoinquit.event;

import com.ullarah.ujoinquit.JoinQuitFunctions;
import com.ullarah.ulib.function.PermissionCheck;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import static org.bukkit.Material.AIR;

public class OptionClick implements Listener {

    @EventHandler
    public void playerClickOption(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        String inventoryTitle = event.getInventory().getTitle();

        if (inventoryTitle.matches(".*Extra Options")
                && PermissionCheck.check(player, "jq.access", "jq.extra")) {

            if (event.getRawSlot() >= 0 && event.getRawSlot() < 5) {

                if (event.getCurrentItem().getType() != AIR) {

                    switch (event.getCurrentItem().getType()) {

                        case COMPASS:
                            JoinQuitFunctions.setLocation(player);
                            break;

                    }

                    player.closeInventory();

                }

            }

            event.setCancelled(true);

        }

    }

}
