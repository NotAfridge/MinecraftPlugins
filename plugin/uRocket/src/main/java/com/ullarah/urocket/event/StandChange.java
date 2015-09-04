package com.ullarah.urocket.event;

import com.ullarah.ulib.function.SignText;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static com.ullarah.urocket.RocketInit.*;

public class StandChange implements Listener {

    @EventHandler
    public void RocketArmourStandChange(PlayerArmorStandManipulateEvent event) {

        Player player = event.getPlayer();
        World world = player.getWorld();
        ItemStack standItem = event.getArmorStandItem();
        Location standLocation = event.getRightClicked().getLocation();

        Block fuelBlock = world.getBlockAt(
                standLocation.getBlockX(),
                standLocation.getBlockY() - 2,
                standLocation.getBlockZ());

        if (fuelBlock.getType() == Material.BURNING_FURNACE) {

            List<String> standList = getPlugin().getConfig().getStringList("stands");

            String stand = player.getUniqueId().toString() + "|"
                    + world.getName() + "|"
                    + standLocation.getBlockX() + "|"
                    + standLocation.getBlockY() + "|"
                    + standLocation.getBlockZ();

            Location beaconSign = new Location(
                    standLocation.getWorld(),
                    standLocation.getX(),
                    (standLocation.getY() - 1),
                    standLocation.getZ());

            if (standList.contains(stand)) {

                if (player.getItemInHand().hasItemMeta()) {

                    if (player.getItemInHand().getItemMeta().hasDisplayName()) {

                        if (player.getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.RED + "Rocket Boots")) {

                            if (standItem != null) {

                                int bootDurability = (195 - player.getItemInHand().getDurability());
                                String bootType = ChatColor.stripColor(player.getItemInHand().getItemMeta().getLore().get(0));

                                SignText.changeAllCheck(beaconSign, 0, "[Repair Status]", false,
                                        new String[]{"[Repair Status]", "----------",
                                                ChatColor.RED + bootType, String.valueOf(bootDurability)});

                                rocketRepairStand.put(event.getRightClicked().getUniqueId(), standLocation);

                            }

                        }

                    }

                } else {

                    if (player.getItemInHand().getType() != Material.AIR) {

                        player.sendMessage(getMsgPrefix() + "You can only place Rocket Boots on this stand!");
                        event.setCancelled(true);

                    } else {

                        SignText.changeAllCheck(beaconSign, 0, "[Repair Status]", false,
                                new String[]{"[Repair Status]", "----------", "", ""});

                        rocketRepairStand.remove(event.getRightClicked().getUniqueId());

                    }

                }

            }

        }

    }

}
