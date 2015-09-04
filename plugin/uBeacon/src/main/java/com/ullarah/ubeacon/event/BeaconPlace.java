package com.ullarah.ubeacon.event;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.List;

import static com.ullarah.ubeacon.BeaconInit.getMsgPrefix;
import static com.ullarah.ubeacon.BeaconInit.getPlugin;

public class BeaconPlace implements Listener {

    @EventHandler
    public void event(BlockPlaceEvent event) {

        Player player = event.getPlayer();
        Block block = event.getBlock();

        World world = player.getWorld();
        Location blockLocation = block.getLocation();

        if (block.getType() == Material.BEACON) {

            if (event.getItemInHand().hasItemMeta()) {

                String customBeacon = event.getItemInHand().getItemMeta().getDisplayName();

                if (customBeacon.matches(ChatColor.LIGHT_PURPLE + "(Custom|Rainbow) Beacon")) {

                    String beaconType = ChatColor.stripColor(customBeacon).replace(" Beacon","").toLowerCase();

                    String playerUUID = player.getUniqueId().toString();
                    String beaconLocation = "X" + blockLocation.getBlockX()
                            + "Y" + blockLocation.getBlockY()
                            + "Z" + blockLocation.getBlockZ();

                    //getPlugin().getConfig().createSection("beacons.player");
                    //getPlugin().getConfig().createSection("beacons.player." + playerUUID);

                    //getPlugin().getConfig().createSection("beacons.player." + playerUUID + "." + world.getName());
                    //getPlugin().getConfig().createSection("beacons.player." + playerUUID + "." + world.getName()
                    //        + "." + beaconLocation);

                    getPlugin().getConfig().set("beacons.player." + playerUUID + "." + world.getName()
                            + "." + beaconLocation + ".type", beaconType);

                    if (beaconType.equals("custom")) {
                        getPlugin().getConfig().set("beacons.player." + playerUUID + "." + world.getName()
                                        + "." + beaconLocation + ".data",
                                new int[]{-1, -1, -1, -1, -1});
                    }

                    getPlugin().saveConfig();

                    player.sendMessage(getMsgPrefix() + "Beacon successfully created!");

                }

            }

        }

    }

}
