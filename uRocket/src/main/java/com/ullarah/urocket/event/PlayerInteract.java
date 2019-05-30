package com.ullarah.urocket.event;

import com.ullarah.urocket.RocketFunctions;
import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.data.RocketPlayer;
import com.ullarah.urocket.function.CommonString;
import com.ullarah.urocket.function.EntityLocation;
import com.ullarah.urocket.function.IDTag;
import com.ullarah.urocket.function.LocationShift;
import com.ullarah.urocket.init.RocketLanguage;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
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

        Player player = event.getPlayer();
        RocketPlayer rp = RocketInit.getPlayer(player);
        Action action = event.getAction();
        ItemStack inHand = player.getInventory().getItemInMainHand();

        if (action.equals(Action.RIGHT_CLICK_AIR) && inHand.getType().equals(Material.CARROT_ON_A_STICK)) {

            if (player.getVehicle() instanceof Pig) {

                Pig pig = (Pig) player.getVehicle();
                Vector pigVelocity = pig.getVelocity();

                double min = -0.045;
                double max = 0.045;

                if (pigVelocity.getX() > max) pigVelocity.setX(max);
                if (pigVelocity.getX() < min) pigVelocity.setX(min);

                if (pigVelocity.getY() > max) pigVelocity.setY(max);

                if (pigVelocity.getZ() > max) pigVelocity.setZ(max);
                if (pigVelocity.getZ() < min) pigVelocity.setZ(min);

                if (RocketInit.rocketEntity.containsKey(pig.getUniqueId()))
                    pig.setVelocity(new Vector(pigVelocity.getX() * 3, 0.5, pigVelocity.getZ() * 3));

            }
            return;
        }

        if (inHand.hasItemMeta()) {
            ItemMeta rocketMeta = inHand.getItemMeta();

            if (rocketMeta.hasDisplayName()) {

                String rocketItem = rocketMeta.getDisplayName();
                if (rocketItem.equals(ChatColor.RED + "Rocket Boot Repair Stand")) {
                    placeRepairStand(event, rp, action);
                }
                else if (rocketItem.equals(ChatColor.RED + "Rocket Boots")) {
                    equipBoots(event, rp, action, inHand);
                }
                else if (rocketItem.equals(ChatColor.RED + "Rocket Boot Fuel Jacket")) {
                    equipJacket(event, rp, action);
                }

            }
        }

    }

    private void placeRepairStand(PlayerInteractEvent event, RocketPlayer rp, Action action) {
        if (!action.equals(Action.RIGHT_CLICK_AIR) && !action.equals(Action.RIGHT_CLICK_BLOCK)) {
            event.setCancelled(true);
            return;
        }

        CommonString commonString = new CommonString();
        Player player = rp.getPlayer();

        // Only place in the overworld
        if (!player.getWorld().getName().equals("world")) {
            event.setCancelled(true);
            commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.PlacementDeny("Repair Stands"));
            return;
        }

        Block station = player.getTargetBlock(null, 50);
        Location stationLocation = station.getLocation();
        Location standLocation = new LocationShift().add(stationLocation, 0.5, 1, 0.5);

        boolean onTop = (event.getBlockFace() == BlockFace.UP);

        // Must be placed on top of a beacon
        if (!station.getType().equals(Material.BEACON) || !onTop) {
            event.setCancelled(true);
            commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_RS_PLACE_ERROR);
            return;
        }

        List<String> stationList = RocketInit.getPlugin().getConfig().getStringList("stations");
        String stationTag = new IDTag().create(player, stationLocation);

        // Not placed on top of a repair station the player owns
        if (!stationList.contains(stationTag)) {
            event.setCancelled(true);
            commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_RS_PLACE_ERROR);
            return;
        }

        // Have to be sneaking to place, else it tries to open the GUI
        if (!player.isSneaking()) {
            event.setCancelled(true);
            commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_RS_SNEAK_ERROR);
            return;
        }

        // Must not be any entities in its location already
        if (new EntityLocation().getNearbyEntities(standLocation, 1).size() != 0) {
            event.setCancelled(true);
            commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_RS_ENTITY);
            return;
        }

        String stand = new IDTag().create(player, standLocation);
        List<String> standList = RocketInit.getPlugin().getConfig().getStringList("stands");

        // Stand can't have been registered in this location
        if (standList.contains(stand)) {
            event.setCancelled(true);
            commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_RS_EXIST);
            return;
        }

        // Place it, save config
        standList.add(stand);
        RocketInit.getPlugin().getConfig().set("stands", standList);
        RocketInit.getPlugin().saveConfig();

        commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_RS_PLACE_SUCCESS);
    }

    private void equipBoots(PlayerInteractEvent event, RocketPlayer rp, Action action, ItemStack inHand) {
        if (!action.equals(Action.RIGHT_CLICK_AIR) && !action.equals(Action.RIGHT_CLICK_BLOCK)) {
            event.setCancelled(true);
            return;
        }

        RocketFunctions rocketFunctions = new RocketFunctions();
        Player player = rp.getPlayer();
        String rocketLore = inHand.getItemMeta().getLore().get(0);

        if (rocketLore.matches(ChatColor.YELLOW + "Rocket Level I{0,3}V?X?")) {
            if (!rp.isUsingBoots()) {
                rocketFunctions.attachRocketBoots(player, inHand);
            }
        }
    }

    private void equipJacket(PlayerInteractEvent event, RocketPlayer rp, Action action) {
        if (!action.equals(Action.RIGHT_CLICK_AIR) && !action.equals(Action.RIGHT_CLICK_BLOCK)) {
            event.setCancelled(true);
            return;
        }

        rp.setWearingJacket(true);
    }

}
