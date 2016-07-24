package com.ullarah.uchest.event;

import com.ullarah.uchest.ChestFunctions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;

public class EntityShear implements Listener {

    @EventHandler
    public void event(PlayerShearEntityEvent event) {

        ChestFunctions f = new ChestFunctions();
        Player p = event.getPlayer();

        ItemStack inMainHand = p.getInventory().getItemInMainHand();
        ItemStack inOffHand = p.getInventory().getItemInOffHand();

        if (f.checkPickupTool(inMainHand)) event.setCancelled(true);
        if (f.checkPickupTool(inOffHand)) event.setCancelled(true);

    }

}
