package com.ullarah.urocket.data;

import com.ullarah.urocket.RocketFunctions;
import com.ullarah.urocket.function.LocationShift;
import com.ullarah.urocket.function.SignText;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;

public class RepairStandData {

    private static final String HEADER = "[Repair Status]";
    private static final String RULER = ChatColor.STRIKETHROUGH + "--------------";

    public enum StopReason {
        FULLY_REPAIRED,
        ALREADY_MAX,
        EMPTY_TANK,
        STAND_DESTROYED,
        BOOTS_REMOVED
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

        clearSignText();
    }

    public void stopRepairing(StopReason reason) {
        switch (reason) {
            case FULLY_REPAIRED:
            case ALREADY_MAX:
                ItemStack boots = stand.getBoots();
                String bootType = ChatColor.stripColor(boots.getItemMeta().getLore().get(0));
                setSignText(ChatColor.RED + bootType, "Fully Restored");
                break;

            case EMPTY_TANK:
                setSignText("Repair Tank", "Empty");
                break;

            case STAND_DESTROYED:
                setSignText("Repair Stand", "Missing");
                break;

            case BOOTS_REMOVED:
                clearSignText();
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
        setSignText(ChatColor.RED + bootType, status);

        // Only start repairing if we need to
        repairing = !full;
    }

    private void clearSignText() {
        setSignText("", "");
    }

    private void setSignText(String line3, String line4) {
        String[] text = new String[] { HEADER, RULER, line3, line4 };
        new SignText().changeAllCheck(beaconLoc, 0, HEADER, false, text);
        new SignText().changeAllCheck(beaconLoc, 0, "", false, text);
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
