package com.ullarah.ubeacon.event;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static com.ullarah.ubeacon.BeaconInit.getPlugin;

public class BeaconOpen implements Listener {

    @EventHandler
    public void event(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (block.getType() == Material.BEACON) {

            World world = player.getWorld();
            Location blockLocation = block.getLocation();

            List<String> beaconList = getPlugin().getConfig().getStringList("beacons");

            String beacon = player.getUniqueId().toString() + "|"
                    + world.getName() + "|"
                    + blockLocation.getBlockX() + "|"
                    + blockLocation.getBlockY() + "|"
                    + blockLocation.getBlockZ() + "|"
                    + "custom";

            if (beaconList.contains(beacon)) {

                String[] colourArray = beacon.replace(beacon, "").replace("(","").replace(")","").split(",");

                int[] intArray = new int[colourArray.length];
                for (int i = 0; i < colourArray.length; i++) intArray[i] = Integer.parseInt(colourArray[i]);

                Inventory beaconHopper = Bukkit.createInventory(null, InventoryType.HOPPER);

                int s = 0;

                for (int i : intArray) {
                    if (i >= 0) beaconHopper.setItem(s, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) i));
                    s++;
                }

                player.openInventory(beaconHopper);

            }

        }

    }

}
