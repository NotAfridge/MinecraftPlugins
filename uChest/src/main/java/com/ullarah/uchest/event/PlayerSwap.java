package com.ullarah.uchest.event;

import com.ullarah.uchest.ChestFunctions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerSwap implements Listener {

    @EventHandler
    public void event(PlayerSwapHandItemsEvent event) {

        ChestFunctions f = new ChestFunctions();
        Player p = event.getPlayer();

        ItemStack inMainHand = p.getInventory().getItemInMainHand();

        if (f.checkContentChest(inMainHand)) event.setCancelled(true);
        if (f.checkPickupTool(inMainHand)) event.setCancelled(true);

    }

}
