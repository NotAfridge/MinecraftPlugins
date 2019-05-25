package com.ullarah.urocket.event;

import com.ullarah.urocket.RocketFunctions;
import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.function.CommonString;
import com.ullarah.urocket.function.IDTag;
import com.ullarah.urocket.function.SignText;
import com.ullarah.urocket.init.RocketLanguage;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class StandChange implements Listener {

    @EventHandler
    public void RocketArmourStandChange(PlayerArmorStandManipulateEvent event) {

        RocketFunctions rocketFunctions = new RocketFunctions();
        CommonString commonString = new CommonString();
        SignText signText = new SignText();

        Player player = event.getPlayer();
        ItemStack inHand = player.getInventory().getItemInMainHand();
        World world = player.getWorld();
        Location standLocation = event.getRightClicked().getLocation();
        BlockState fuelBlock = world.getBlockAt(standLocation.getBlockX(), standLocation.getBlockY() - 2, standLocation.getBlockZ()).getState();

        List<String> standList = RocketInit.getPlugin().getConfig().getStringList("stands");
        List<String> newStandList = standList.stream().map(stand -> stand.replaceFirst(".{37}", "")).collect(Collectors.toList());

        String standNew = new IDTag().create(standLocation);

        // Ignore non-repair stands
        if (!newStandList.contains(standNew)) {
            return;
        }

        if (fuelBlock instanceof Furnace && ((Furnace) fuelBlock).getBurnTime() > 0) {
            Location beaconSign = new Location(standLocation.getWorld(), standLocation.getX(), (standLocation.getY() - 1), standLocation.getZ());

            if (inHand.hasItemMeta()) {

                if (inHand.getItemMeta().hasDisplayName()) {

                    if (inHand.getItemMeta().getDisplayName().equals(ChatColor.RED + "Rocket Boots")) {

                        ItemStack standItem = event.getArmorStandItem();

                        if (standItem != null) {

                            int bootMaterialDurability = rocketFunctions.getBootDurability(inHand);
                            int bootDurability = (bootMaterialDurability - inHand.getDurability());

                            String bootType = ChatColor.stripColor(inHand.getItemMeta().getLore().get(0));

                            signText.changeAllCheck(beaconSign, 0, "[Repair Status]", false,
                                    new String[]{
                                            "[Repair Status]",
                                            ChatColor.STRIKETHROUGH + "--------------",
                                            ChatColor.RED + bootType,
                                            String.valueOf(bootDurability) + " / " + bootMaterialDurability});

                            signText.changeLine(beaconSign, new HashMap<Integer, String>() {{
                                put(0, "[Repair Status]");
                            }});

                            RocketInit.rocketRepairStand.put(event.getRightClicked().getUniqueId(), standLocation);

                        }

                    }

                }

            } else {

                if (inHand.getType() != Material.AIR) {

                    commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_RS_CHANGE);
                    event.setCancelled(true);

                } else {

                    signText.changeAllCheck(beaconSign, 0, "[Repair Status]", false,
                            new String[]{"[Repair Status]", ChatColor.STRIKETHROUGH + "--------------", "", ""});

                    signText.changeLine(beaconSign, new HashMap<Integer, String>() {{
                        put(0, "[Repair Status]");
                    }});

                    RocketInit.rocketRepairStand.remove(event.getRightClicked().getUniqueId());

                }

            }

        }

    }

}
