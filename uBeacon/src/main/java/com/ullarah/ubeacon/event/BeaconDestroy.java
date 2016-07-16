package com.ullarah.ubeacon.event;

import com.ullarah.ubeacon.BeaconInit;
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

public class BeaconDestroy implements Listener {

    @EventHandler
    public void event(BlockBreakEvent event) {

        Player player = event.getPlayer();
        Block block = event.getBlock();

        World world = player.getWorld();
        Location blockLocation = block.getLocation();

        List<String> beaconList = BeaconInit.getPlugin().getConfig().getStringList("beacons");

        if (block.getType() == Material.STAINED_GLASS) {

            String beacon = player.getUniqueId().toString() + "|"
                    + world.getName() + "|"
                    + blockLocation.getBlockX() + "|"
                    + (blockLocation.getBlockY() - 1) + "|"
                    + blockLocation.getBlockZ();

            if (beaconList.contains(beacon)) {

                player.sendMessage(BeaconInit.getMsgPrefix() + "Destroy the beacon first.");
                event.setCancelled(true);

            }

        }

        if (block.getType() == Material.BEACON) {

            String beacon = player.getUniqueId().toString() + "|"
                    + world.getName() + "|"
                    + blockLocation.getBlockX() + "|"
                    + blockLocation.getBlockY() + "|"
                    + blockLocation.getBlockZ();

            if (beaconList.contains(beacon)) {

                beaconList.remove(beacon);
                world.getBlockAt(blockLocation).setType(Material.AIR);
                BeaconInit.getPlugin().getConfig().set("beacons", beaconList);
                BeaconInit.getPlugin().saveConfig();

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
