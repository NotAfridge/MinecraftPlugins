package com.ullarah.umagic.event;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;

public class FurnaceSmelt extends MagicFunctions implements Listener {

    @EventHandler
    public void furnaceSmelt(FurnaceSmeltEvent event) {

        if (event.getBlock().hasMetadata(metaFurn)) {

            Furnace furnace = (Furnace) event.getBlock().getState();

            furnace.getInventory().setFuel(getFurnaceFuel());
            furnace.getInventory().setSmelting(getFurnaceSmelt());
            furnace.getInventory().setResult(null);

            furnace.update();

            furnace.setBurnTime((short) Integer.MAX_VALUE);
            furnace.setCookTime((short) Integer.MAX_VALUE);

            event.setCancelled(true);

        }

    }

}
