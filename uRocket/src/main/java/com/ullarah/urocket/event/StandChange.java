package com.ullarah.urocket.event;

import com.ullarah.urocket.RocketFunctions;
import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.data.RepairStandData;
import com.ullarah.urocket.function.CommonString;
import com.ullarah.urocket.function.IDTag;
import com.ullarah.urocket.function.LocationShift;
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
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class StandChange implements Listener {

    @EventHandler
    public void RocketArmourStandChange(PlayerArmorStandManipulateEvent event) {

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

        Location beaconSign = new LocationShift().add(standLocation, 0, -1, 0);
        ItemMeta meta = inHand.getItemMeta();
        String display = "";
        if (meta != null && meta.hasDisplayName()) {
            display = meta.getDisplayName();
        }

        if (display.equals(ChatColor.RED + "Rocket Boots")) {
            // Adding rocket boots
            signText.changeLine(beaconSign, new HashMap<Integer, String>() {{
                put(0, "[Repair Status]");
                put(1, ChatColor.STRIKETHROUGH + "--------------");
            }});

            RepairStandData data = new RepairStandData(event.getRightClicked(), standLocation);
            RocketInit.rocketRepairStand.put(event.getRightClicked().getUniqueId(), data);

            if (fuelBlock instanceof Furnace && ((Furnace) fuelBlock).getBurnTime() > 0) {
                data.startRepairing(inHand);
            }

        } else if (inHand.getType() != Material.AIR) {
            // Trying to add non-rocket boots (allow jackets for storage purposes)
            if (!display.equals(ChatColor.RED + "Rocket Boot Fuel Jacket")) {
                commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_RS_CHANGE);
                event.setCancelled(true);
            }

        } else {
            // Removing boots
            signText.changeAllCheck(beaconSign, 0, "[Repair Status]", false,
                    new String[]{"[Repair Status]", ChatColor.STRIKETHROUGH + "--------------", "", ""});

            RocketInit.rocketRepairStand.remove(event.getRightClicked().getUniqueId());

        }

    }

}
