package com.ullarah.urocket.command;

import com.ullarah.urocket.recipe.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DisplayComponentChest {

    public static void open(CommandSender sender) {

        Player player = (Player) sender;

        Inventory rocketComponentInventory = Bukkit.createInventory(null, 36, ChatColor.DARK_RED + "Rocket Components");

        rocketComponentInventory.setContents(rocketComponents());
        player.openInventory(rocketComponentInventory);

    }

    private static ItemStack[] rocketComponents() {

        return new ItemStack[]{
                RocketBooster.booster("I"),
                RocketBooster.booster("II"),
                RocketBooster.booster("III"),
                RocketBooster.booster("IV"),
                RocketBooster.booster("V"),
                RocketBooster.booster("X"),
                RocketEnhance.enhancement(ChatColor.RED + "Fuel Efficient", null),
                RocketEnhance.enhancement(ChatColor.RED + "Solar Power", null),
                null,

                RocketControls.control(),
                RepairTank.tank(),
                RepairStation.station(),
                RepairStand.stand(),
                RocketFlyZone.zone(),
                RocketSaddle.saddle(),
                RocketEnhance.enhancement(ChatColor.RED + "Self Repair", null),
                null,
                null,

                RocketVariants.variant(ChatColor.LIGHT_PURPLE + "Gay Agenda"),
                RocketVariants.variant(ChatColor.AQUA + "Pole Vaulter"),
                RocketVariants.variant(ChatColor.GRAY + "Coal Miner"),
                RocketVariants.variant(ChatColor.DARK_AQUA + "Essence of Ender"),
                RocketVariants.variant(ChatColor.GRAY + "Glazed Over"),
                RocketVariants.variant(ChatColor.YELLOW + "Shooting Star"),
                RocketVariants.variant(ChatColor.GREEN + "Health Zapper"),
                RocketVariants.variant(ChatColor.RED + "TNT Overload"),
                RocketVariants.variant(ChatColor.WHITE + "Loud Silence"),

                RocketVariants.variant(ChatColor.GOLD + "Musical Madness"),
                RocketVariants.variant(ChatColor.YELLOW + "Radical Rainbows"),
                RocketVariants.variant(ChatColor.DARK_RED + "Red Fury"),
                RocketVariants.variant(ChatColor.GOLD + "Rocket Runner"),
                RocketVariants.variant(ChatColor.WHITE + "Super Stealth"),
                RocketVariants.variant(ChatColor.BLUE + "Water Slider"),
                RocketVariants.variant(ChatColor.YELLOW + "Patient Zero"),
                RocketVariants.variant(ChatColor.DARK_GREEN + "Robin Hood"),
                null
        };

    }

}
