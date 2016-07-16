package com.ullarah.ubeacon.event;

import com.ullarah.ubeacon.BeaconInit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.List;

public class BeaconPlace implements Listener {

    @EventHandler
    public void event(BlockPlaceEvent event) {

        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location blockLocation = block.getLocation();

        if (block.getType() == Material.BEACON) {

            if (event.getItemInHand().hasItemMeta()) {

                if (event.getItemInHand().getItemMeta().hasDisplayName()) {

                    String customBeacon = event.getItemInHand().getItemMeta().getDisplayName();

                    if (customBeacon.matches(ChatColor.LIGHT_PURPLE + "Rainbow Beacon")) {

                        List<String> beaconList = BeaconInit.getPlugin().getConfig().getStringList("beacons");

                        beaconList.add(player.getUniqueId().toString()
                                + "|" + player.getWorld().getName()
                                + "|" + blockLocation.getBlockX()
                                + "|" + blockLocation.getBlockY()
                                + "|" + blockLocation.getBlockZ()
                        );

                        BeaconInit.getPlugin().getConfig().set("beacons", beaconList);

                        BeaconInit.getPlugin().saveConfig();

                        player.sendMessage(BeaconInit.getMsgPrefix() + "Beacon successfully created!");

                    }

                }

            }

        }

    }

}
