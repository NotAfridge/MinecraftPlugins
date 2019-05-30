package com.ullarah.urocket.data;

import com.ullarah.urocket.RocketFunctions;
import com.ullarah.urocket.function.LocationShift;
import com.ullarah.urocket.function.SignText;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class RepairStandData {

    public enum StopReason {
        FULLY_REPAIRED,
        ALREADY_MAX,
        EMPTY_TANK
    }

    private boolean repairing;
    private ArmorStand stand;
    private Location location;
    private Location beaconLoc;

    public RepairStandData(ArmorStand stand, Location location) {
        this.repairing = false;
        this.stand = stand;
        this.location = new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
        this.beaconLoc = new LocationShift().add(this.location, 0, -1, 0);
    }

    public void stopRepairing(StopReason reason) {
        switch (reason) {
            case FULLY_REPAIRED:
            case ALREADY_MAX:
                ItemStack boots = stand.getBoots();
                String bootType = ChatColor.stripColor(boots.getItemMeta().getLore().get(0));

                new SignText().changeAllCheck(beaconLoc, 0, "[Repair Status]", false,
                        new String[]{
                                "[Repair Status]",
                                ChatColor.STRIKETHROUGH + "--------------",
                                ChatColor.RED + bootType,
                                "Fully Restored"});

                break;

            case EMPTY_TANK:
                new SignText().changeLine(beaconLoc,
                        new HashMap<Integer, String>() {{
                            put(2, "Repair Tank");
                            put(3, "Empty");
                        }});
                break;
        }

        repairing = false;
    }

    public void startRepairing(ItemStack boots) {
        int bootMaterialDurability = new RocketFunctions().getBootDurability(boots);
        int bootDurability = (bootMaterialDurability - boots.getDurability());
        String bootType = ChatColor.stripColor(boots.getItemMeta().getLore().get(0));

        boolean full = (bootDurability == bootMaterialDurability);
        String status = full ? "Fully Restored" : bootDurability + " / " + bootMaterialDurability;

        new SignText().changeAllCheck(beaconLoc, 0, "[Repair Status]", false,
                new String[]{
                        "[Repair Status]",
                        ChatColor.STRIKETHROUGH + "--------------",
                        ChatColor.RED + bootType,
                        status});

        // Only start repairing if we need to
        repairing = !full;
    }

    public ArmorStand getStand() {
        return stand;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isRepairing() {
        return repairing;
    }
}
