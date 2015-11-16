package com.ullarah.urocket.event;

import com.ullarah.ulib.function.EntityLocation;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Set;

import static com.ullarah.ulib.function.CommonString.messageSend;
import static com.ullarah.urocket.RocketFunctions.attachRocketBoots;
import static com.ullarah.urocket.RocketInit.*;
import static com.ullarah.urocket.RocketLanguage.*;
import static org.bukkit.Material.BEACON;

public class PlayerInteract implements Listener {

    @EventHandler
    public void playerInteraction(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action.equals(Action.RIGHT_CLICK_AIR)) if (player.getItemInHand().getType().equals(Material.CARROT_STICK)) {

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

            ItemStack rocketBoots = player.getItemInHand();
            ItemMeta rocketMeta = rocketBoots.getItemMeta();

            if (player.getItemInHand().getItemMeta().hasDisplayName()) {

                String rocketItem = rocketMeta.getDisplayName();

                if (rocketItem.equals(ChatColor.RED + "Rocket Repair Stand")) {

                    if (action.equals(Action.RIGHT_CLICK_BLOCK)) {

                        if (player.getWorld().getName().equals("world")) {

                            Block block = player.getTargetBlock((Set<Material>) null, 50);
                            World world = player.getWorld();
                            Location blockLocation = block.getLocation();

                            int eX = blockLocation.getBlockX();
                            int eY = blockLocation.getBlockY();
                            int eZ = blockLocation.getBlockZ();

                            if (block.getType().equals(BEACON) && event.getBlockFace().getModY() == 1) {

                                List<String> stationList = getPlugin().getConfig().getStringList("stations");
                                String station = player.getUniqueId().toString() + "|" + world.getName() + "|" + eX + "|" + eY + "|" + eZ;

                                if (!stationList.contains(station)) {

                                    event.setCancelled(true);
                                    messageSend(getPlugin(), player, true, RB_RS_PLACE_ERROR);

                                } else if (player.isSneaking()) {

                                    String stand = player.getUniqueId().toString() + "|" + world.getName() + "|" + eX + "|" + (eY + 1) + "|" + eZ;
                                    List<String> standList = getPlugin().getConfig().getStringList("stands");

                                    if (EntityLocation.getNearbyEntities(new Location(world, eX, eY + 1, eZ), 1).length != 0) {

                                        messageSend(getPlugin(), player, true, RB_RS_ENTITY);

                                    } else {

                                        if (standList.contains(stand))

                                            messageSend(getPlugin(), player, true, RB_RS_EXIST);

                                        else {

                                            standList.add(stand);

                                            getPlugin().getConfig().set("stands", standList);
                                            getPlugin().saveConfig();

                                            messageSend(getPlugin(), player, true, RB_RS_PLACE_SUCCESS);

                                        }

                                    }

                                }

                            } else {

                                player.updateInventory();
                                event.setCancelled(true);
                                messageSend(getPlugin(), player, true, RB_RS_PLACE_ERROR);

                            }

                        } else {

                            player.updateInventory();
                            event.setCancelled(true);
                            messageSend(getPlugin(), player, true, PlacementDeny("Repair Stands"));

                        }

                    } else event.setCancelled(true);

                }

                if (rocketItem.equals(ChatColor.RED + "Rocket Boots")) {

                    String rocketLore = rocketMeta.getLore().get(0);

                    if (rocketLore.matches(ChatColor.YELLOW + "Rocket Level I{0,3}V?X?"))
                        if (!rocketUsage.contains(player.getUniqueId())) {
                            if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK))
                                attachRocketBoots(player, rocketBoots);
                            else event.setCancelled(true);
                        }

                }

            }

        }

    }

}
