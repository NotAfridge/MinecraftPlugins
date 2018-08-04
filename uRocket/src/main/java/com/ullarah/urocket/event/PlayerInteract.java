package com.ullarah.urocket.event;

import com.ullarah.urocket.RocketFunctions;
import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.function.CommonString;
import com.ullarah.urocket.function.EntityLocation;
import com.ullarah.urocket.init.RocketLanguage;
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

public class PlayerInteract implements Listener {

    @EventHandler
    public void playerInteraction(PlayerInteractEvent event) {

        RocketFunctions rocketFunctions = new RocketFunctions();
        CommonString commonString = new CommonString();

        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack inHand = player.getInventory().getItemInMainHand();

        if (action.equals(Action.RIGHT_CLICK_AIR)) if (inHand.getType().equals(Material.CARROT_ON_A_STICK)) {

            if (player.getVehicle() instanceof Pig) {

                Pig pig = (Pig) player.getVehicle();
                Vector pigVelocity = pig.getVelocity();

                if (pigVelocity.getX() > 0.045) pigVelocity.setX(0.045);
                if (pigVelocity.getX() < -0.045) pigVelocity.setX(-0.045);

                if (pigVelocity.getY() > 0.045) pigVelocity.setY(0.045);

                if (pigVelocity.getZ() > 0.045) pigVelocity.setZ(0.045);
                if (pigVelocity.getZ() < -0.045) pigVelocity.setZ(-0.045);

                if (RocketInit.rocketEntity.containsKey(pig.getUniqueId()))
                    pig.setVelocity(new Vector(pigVelocity.getX() * 3, 0.5, pigVelocity.getZ() * 3));

            }

        }

        if (inHand.hasItemMeta()) {

            ItemMeta rocketMeta = inHand.getItemMeta();

            if (inHand.getItemMeta().hasDisplayName()) {

                String rocketItem = rocketMeta.getDisplayName();

                if (rocketItem.equals(ChatColor.RED + "Rocket Boot Repair Stand")) {

                    if (action.equals(Action.RIGHT_CLICK_BLOCK)) {

                        if (player.getWorld().getName().equals("world")) {

                            Block block = player.getTargetBlock((Set<Material>) null, 50);
                            World world = player.getWorld();
                            Location blockLocation = block.getLocation();

                            int eX = blockLocation.getBlockX();
                            int eY = blockLocation.getBlockY();
                            int eZ = blockLocation.getBlockZ();

                            if (block.getType().equals(Material.BEACON) && event.getBlockFace().getModY() == 1) {

                                List<String> stationList = RocketInit.getPlugin().getConfig().getStringList("stations");
                                String station = player.getUniqueId().toString() + "|" + world.getName() + "|" + eX + "|" + eY + "|" + eZ;

                                if (!stationList.contains(station)) {

                                    event.setCancelled(true);
                                    commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_RS_PLACE_ERROR);

                                } else if (player.isSneaking()) {

                                    String stand = player.getUniqueId().toString() + "|" + world.getName() + "|" + eX + "|" + (eY + 1) + "|" + eZ;
                                    List<String> standList = RocketInit.getPlugin().getConfig().getStringList("stands");

                                    if (new EntityLocation().getNearbyEntities(new Location(world, eX, eY + 1, eZ), 1).length != 0) {

                                        commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_RS_ENTITY);

                                    } else {

                                        if (standList.contains(stand))

                                            commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_RS_EXIST);

                                        else {

                                            standList.add(stand);

                                            RocketInit.getPlugin().getConfig().set("stands", standList);
                                            RocketInit.getPlugin().saveConfig();

                                            commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_RS_PLACE_SUCCESS);

                                        }

                                    }

                                }

                            } else {

                                player.updateInventory();
                                event.setCancelled(true);
                                commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_RS_PLACE_ERROR);

                            }

                        } else {

                            player.updateInventory();
                            event.setCancelled(true);
                            commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.PlacementDeny("Repair Stands"));

                        }

                    } else event.setCancelled(true);

                }

                if (rocketItem.equals(ChatColor.RED + "Rocket Boots")) {

                    String rocketLore = rocketMeta.getLore().get(0);

                    if (rocketLore.matches(ChatColor.YELLOW + "Rocket Level I{0,3}V?X?"))
                        if (!RocketInit.rocketUsage.contains(player.getUniqueId())) {
                            if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK))
                                rocketFunctions.attachRocketBoots(player, inHand);
                            else event.setCancelled(true);
                        }

                }

                if (rocketItem.equals(ChatColor.RED + "Rocket Boot Fuel Jacket")) {

                    if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK))
                        RocketInit.rocketJacket.add(player.getUniqueId());
                    else event.setCancelled(true);

                }

            }

        }

    }

}
