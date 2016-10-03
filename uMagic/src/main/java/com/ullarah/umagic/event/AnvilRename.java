package com.ullarah.umagic.event;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.recipe.MagicHoeNormal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;

public class AnvilRename extends MagicFunctions implements Listener {

    public AnvilRename() {
        super(false);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void event(InventoryClickEvent event) {

        if (event.getClickedInventory() instanceof AnvilInventory) {

            ItemStack cursor = event.getCursor();

            if (cursor != null && cursor.hasItemMeta()) {

                if (cursor.getItemMeta().hasDisplayName()) {

                    if (cursor.getItemMeta().getDisplayName().matches(new MagicHoeNormal().getHoeDisplayName())) {

                        Player player = (Player) event.getWhoClicked();

                        player.closeInventory();
                        getCommonString().messageSend(player, "Magical Hoes cannot be repaired!");

                    }

                }

            }

        }

    }

}
