package com.ullarah.uchest.event;

import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;

public class FurnaceBurn implements Listener {

    @EventHandler
    public void event(final FurnaceBurnEvent event) {

        Furnace furnace = (Furnace) event.getBlock().getState();

        short cookTime = 400;

        switch (furnace.getInventory().getSmelting().getType()) {

            case IRON_HELMET:
                furnace.setCookTime(cookTime);
                break;

            case IRON_CHESTPLATE:
                furnace.setCookTime(cookTime);
                break;

            case IRON_LEGGINGS:
                furnace.setCookTime(cookTime);
                break;

            case IRON_BOOTS:
                furnace.setCookTime(cookTime);
                break;

        }

    }

}
