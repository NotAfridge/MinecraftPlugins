package com.ullarah.ubeacon.event;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static com.ullarah.ubeacon.BeaconInit.getMsgPrefix;
import static com.ullarah.ubeacon.BeaconInit.getPlugin;

public class BeaconDestroy implements Listener {

    @EventHandler
    public void event(BlockBreakEvent event) {

        Player player = event.getPlayer();
        Block block = event.getBlock();

        World world = player.getWorld();
        Location blockLocation = block.getLocation();

        if (block.getType() == Material.STAINED_GLASS) {

            List<String> beaconList = getPlugin().getConfig().getStringList("beacons");

            String rainbowBeacon = player.getUniqueId().toString() + "|"
                    + world.getName() + "|"
                    + blockLocation.getBlockX() + "|"
                    + (blockLocation.getBlockY() - 1) + "|"
                    + blockLocation.getBlockZ() + "|";

            if (beaconList.contains(rainbowBeacon)) {

                player.sendMessage(getMsgPrefix() + "Destroy the beacon first.");
                event.setCancelled(true);

            }

        }

        if (block.getType() == Material.BEACON) {

            List<String> beaconList = getPlugin().getConfig().getStringList("beacons");

            String beacon = player.getUniqueId().toString() + "|"
                    + world.getName() + "|"
                    + blockLocation.getBlockX() + "|"
                    + blockLocation.getBlockY() + "|"
                    + blockLocation.getBlockZ();

            if (beaconList.contains(beacon)) {

                beaconList.remove(beacon);
                world.getBlockAt(blockLocation).setType(Material.AIR);
                getPlugin().getConfig().set("beacons", beaconList);
                getPlugin().saveConfig();

                world.getBlockAt(
                        blockLocation.getBlockX(),
                        blockLocation.getBlockY() + 1,
                        blockLocation.getBlockZ()).setType(Material.AIR);

                world.dropItemNaturally(blockLocation, new ItemStack(Material.NETHER_STAR));
                world.createExplosion(blockLocation, 0.0f, false);

            }

        }

    }

}
