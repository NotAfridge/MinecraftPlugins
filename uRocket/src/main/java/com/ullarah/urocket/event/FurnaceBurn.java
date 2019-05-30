package com.ullarah.urocket.event;

import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.data.RepairStandData;
import com.ullarah.urocket.function.LocationShift;
import org.bukkit.Location;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;

public class FurnaceBurn implements Listener {

    @EventHandler
    public void furnaceBurn(FurnaceBurnEvent event) {
        if (!(event.getBlock().getState() instanceof Furnace)) {
            return;
        }

        Furnace furnace = (Furnace) event.getBlock().getState();
        Location furnaceLoc = furnace.getLocation();
        Location standLoc = new LocationShift().add(furnaceLoc, 0, 2, 0);

        // Find the correct data, start repairing, and set a timeout check for stopping the repair
        for (RepairStandData data : RocketInit.rocketRepairStand.values()) {
            if (standLoc.equals(data.getLocation())) {
                data.startRepairing(data.getStand().getBoots());
                return;
            }
        }
    }
}
