package com.ullarah.urocket.event;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Set;

import static com.ullarah.urocket.RocketFunctions.attachRocketBoots;
import static com.ullarah.urocket.RocketInit.*;
import static org.bukkit.Material.BEACON;

public class PlayerInteract implements Listener {

    @EventHandler
    public void playerInteraction(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        Block block = player.getTargetBlock((Set<Material>) null, 50);

        World world = player.getWorld();
        Location blockLocation = block.getLocation();

        int eX = blockLocation.getBlockX();
        int eY = blockLocation.getBlockY();
        int eZ = blockLocation.getBlockZ();

        Action action = event.getAction();

        if (action == Action.RIGHT_CLICK_AIR) if (player.getItemInHand().getType() == Material.CARROT_STICK) {

            if (player.getVehicle() instanceof Pig) {

                Pig pig = (Pig) player.getVehicle();
                Vector pigVelocity = pig.getVelocity();

                if (pigVelocity.getX() > 0.045) pigVelocity.setX(0.045);
                if (pigVelocity.getX() < -0.045) pigVelocity.setX(-0.045);

                if (pigVelocity.getY() > 0.045) pigVelocity.setY(0.045);

                if (pigVelocity.getZ() > 0.045) pigVelocity.setZ(0.045);
                if (pigVelocity.getZ() < -0.045) pigVelocity.setZ(-0.045);

                if (rocketEntity.containsKey(pig.getUniqueId()))
                    pig.setVelocity(new Vector(pigVelocity.getX() * 3, 0.5, pigVelocity.getZ() * 3));

            }

        }

        if (player.getItemInHand().hasItemMeta()) {

            if (player.getItemInHand().getItemMeta().hasDisplayName()) {

                String rocketItem = player.getItemInHand().getItemMeta().getDisplayName();
                ItemMeta rocketMeta = player.getItemInHand().getItemMeta();

                if (rocketItem.equals(ChatColor.RED + "Rocket Repair Stand")) {

                    if (action == Action.RIGHT_CLICK_BLOCK) {

                        if (player.getWorld().getName().equals("world")) {

                            if (block.getType() == BEACON && event.getBlockFace().getModY() == 1) {

                                List<String> stationList = getPlugin().getConfig().getStringList("stations");

                                String station = player.getUniqueId().toString() + "|"
                                        + world.getName() + "|" + eX + "|" + eY + "|" + eZ;

                                if (!stationList.contains(station)) {

                                    event.setCancelled(true);
                                    player.sendMessage(getMsgPrefix() + ChatColor.YELLOW
                                            + "You can only place this on top of a Repair Station!");

                                } else if (player.isSneaking()) {

                                    String stand = player.getUniqueId().toString() + "|"
                                            + world.getName() + "|" + eX + "|" + (eY + 1) + "|" + eZ;

                                    List<String> standList = getPlugin().getConfig().getStringList("stands");

                                    if (standList.contains(stand))
                                        player.sendMessage(getMsgPrefix() + ChatColor.YELLOW
                                                + "Repair Stand already exists at this location!");
                                    else {

                                        standList.add(stand);

                                        getPlugin().getConfig().set("stands", standList);
                                        getPlugin().saveConfig();

                                        player.sendMessage(getMsgPrefix() + ChatColor.GREEN
                                                + "Repair Stand ready to use!");

                                    }

                                }

                            } else {

                                player.updateInventory();
                                event.setCancelled(true);
                                player.sendMessage(getMsgPrefix() + ChatColor.YELLOW
                                        + "You can only place this on top of a Repair Station!");

                            }

                        } else {

                            player.updateInventory();
                            event.setCancelled(true);
                            player.sendMessage(getMsgPrefix() + ChatColor.YELLOW
                                    + "You can only place this on top of a Repair Station!");

                        }

                    } else event.setCancelled(true);

                }

                if (rocketItem.equals(ChatColor.RED + "Rocket Boots")) {

                    String rocketLore = rocketMeta.getLore().get(0);

                    if (rocketLore.matches(ChatColor.YELLOW + "Rocket Level I{0,3}V?X?"))
                        if (!rocketUsage.contains(player.getUniqueId())) {
                            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)
                                attachRocketBoots(player, rocketMeta, rocketLore);
                            else event.setCancelled(true);
                        }

                }

            }

        }

    }

}
