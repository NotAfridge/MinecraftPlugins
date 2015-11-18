package com.ullarah.urocket.command;

import com.ullarah.urocket.RocketEnhancement;
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

    public void open(CommandSender sender) {

        Player player = (Player) sender;

        Inventory rocketComponentInventory = Bukkit.createInventory(null, 45, ChatColor.DARK_RED + "Rocket Components");

        rocketComponentInventory.setContents(rocketComponents());
        player.openInventory(rocketComponentInventory);

    }

    private ItemStack[] rocketComponents() {

        List<ItemStack> rocketStack = new ArrayList<ItemStack>() {{
            Arrays.asList(
                    add(RocketBooster.booster("I")),
                    add(RocketBooster.booster("II")),
                    add(RocketBooster.booster("III")),
                    add(RocketBooster.booster("IV")),
                    add(RocketBooster.booster("V")),
                    add(RocketFuelJacket.jacket(Material.LEATHER_CHESTPLATE)),
                    add(RocketFuelJacket.jacket(Material.IRON_CHESTPLATE)),
                    add(RocketFuelJacket.jacket(Material.GOLD_CHESTPLATE)),
                    add(RocketFuelJacket.jacket(Material.DIAMOND_CHESTPLATE)),

                    add(new RocketControls().control()),
                    add(new RepairTank().tank()),
                    add(new RepairStation().station()),
                    add(new RepairStand().stand()),
                    add(new RocketFlyZone().zone()),
                    add(new RocketSaddle().saddle()),
                    add(new ItemStack(Material.AIR)),
                    add(new ItemStack(Material.AIR)),
                    add(new ItemStack(Material.AIR))
            );
        }};

        for (RocketVariant.Variant variant : RocketVariant.Variant.values())
            rocketStack.add(RocketVariants.variant(variant.getName()));

        for (RocketEnhancement.Enhancement enhancement : RocketEnhancement.Enhancement.values())
            rocketStack.add(RocketEnhance.enhancement(enhancement.getName()));

        return rocketStack.toArray(new ItemStack[rocketStack.size()]);

    }

}
