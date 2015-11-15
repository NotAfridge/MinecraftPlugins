package com.ullarah.urocket.command;

import com.ullarah.urocket.RocketVariant;
import com.ullarah.urocket.recipe.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DisplayComponentChest {

    public static void open(CommandSender sender) {

        Player player = (Player) sender;

        Inventory rocketComponentInventory = Bukkit.createInventory(null, 45, ChatColor.DARK_RED + "Rocket Components");

        rocketComponentInventory.setContents(rocketComponents());
        player.openInventory(rocketComponentInventory);

    }

    private static ItemStack[] rocketComponents() {

        List<ItemStack> rocketStack = new ArrayList<ItemStack>() {{
            Arrays.asList(
                    add(RocketBooster.booster("I")),
                    add(RocketBooster.booster("II")),
                    add(RocketBooster.booster("III")),
                    add(RocketBooster.booster("IV")),
                    add(RocketBooster.booster("V")),
                    add(RocketBooster.booster("X")),
                    add(new ItemStack(Material.AIR)),
                    add(new ItemStack(Material.AIR)),
                    add(new ItemStack(Material.AIR)),

                    add(RocketControls.control()),
                    add(RepairTank.tank()),
                    add(RepairStation.station()),
                    add(RepairStand.stand()),
                    add(RocketFlyZone.zone()),
                    add(RocketSaddle.saddle()),
                    add(new ItemStack(Material.AIR)),
                    add(new ItemStack(Material.AIR)),
                    add(new ItemStack(Material.AIR)),

                    add(RocketEnhance.enhancement(ChatColor.RED + "Fuel Efficient", null)),
                    add(RocketEnhance.enhancement(ChatColor.RED + "Solar Power", null)),
                    add(RocketEnhance.enhancement(ChatColor.RED + "Self Repair", null)),
                    add(new ItemStack(Material.AIR)),
                    add(new ItemStack(Material.AIR)),
                    add(new ItemStack(Material.AIR)),
                    add(new ItemStack(Material.AIR)),
                    add(new ItemStack(Material.AIR)),
                    add(new ItemStack(Material.AIR))
            );
        }};

        for (RocketVariant.Variant variant : RocketVariant.Variant.values())
            rocketStack.add(RocketVariants.variant(variant.getName()));

        return rocketStack.toArray(new ItemStack[rocketStack.size()]);

    }

}
