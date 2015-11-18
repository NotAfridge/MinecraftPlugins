package com.ullarah.ujoinquit.event;

import com.ullarah.ujoinquit.function.PermissionCheck;
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
    public void playerClickMessage(InventoryClickEvent event) {

        if (event.getClickedInventory() == null) return;

        Player player = (Player) event.getWhoClicked();
        String inventoryTitle = event.getClickedInventory().getTitle();

        if (inventoryTitle.matches(".*(Join|Quit) Message")
                && new PermissionCheck().check(player, "jq.access", "jq.join", "jq.quit")) {

            if (event.getClickedInventory() == null) return;

            if (event.getRawSlot() >= 0 && event.getRawSlot() < 54) {

                Material clickedItem = event.getCurrentItem().getType();

                if (clickedItem != Material.AIR
                        && (clickedItem == Material.STAINED_GLASS_PANE || clickedItem == Material.THIN_GLASS)) {

                    if (inventoryTitle.matches(".*Join Message")) setMessage(player, JOIN, event.getRawSlot());
                    if (inventoryTitle.matches(".*Quit Message")) setMessage(player, QUIT, event.getRawSlot());

                    event.getCursor().setType(Material.AIR);
                    player.closeInventory();

                }

            }

            event.setCancelled(true);

        }

    }

}
