package com.ullarah.ujoinquit.event;

import com.ullarah.ulib.function.PermissionCheck;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import static com.ullarah.ujoinquit.JoinQuitFunctions.messageType.JOIN;
import static com.ullarah.ujoinquit.JoinQuitFunctions.messageType.QUIT;
import static com.ullarah.ujoinquit.JoinQuitFunctions.setMessage;

public class MessageClick implements Listener {

    @EventHandler
    public void playerClickInventory(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        String chestName = event.getInventory().getTitle();

        if (chestName.matches(".*(Join|Quit) Message") && PermissionCheck.check(player, "jq.access", "jq.join", "jq.quit")) {

            if (event.getRawSlot() < 54) {

                if (event.getCurrentItem().getType() != Material.AIR && event.getCurrentItem().getType() == Material.PAPER) {

                    if (chestName.matches(".*Join Message")) setMessage(player, JOIN, event.getRawSlot());
                    if (chestName.matches(".*Quit Message")) setMessage(player, QUIT, event.getRawSlot());

                    player.closeInventory();

                }

            }

            event.setCancelled(true);

        }

    }

}
