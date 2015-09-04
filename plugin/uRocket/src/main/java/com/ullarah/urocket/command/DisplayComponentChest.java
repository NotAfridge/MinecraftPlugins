package com.ullarah.urocket.command;

import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.recipe.*;
import com.ullarah.urocket.recipe.variant.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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

        ItemStack variantMoney = null;
        if (RocketInit.getVaultEconomy() != null) variantMoney = VariantMoney.variant();

        return new ItemStack[]{
                BoosterOne.booster(), BoosterTwo.booster(), BoosterThree.booster(),
                BoosterFour.booster(), BoosterFive.booster(), BoosterTen.booster(),
                null, null, null,

                RocketControls.control(), RepairTank.tank(), RepairStation.station(),
                RepairStand.stand(), RocketFlyZone.zone(), RocketSaddle.saddle(),
                null, null, new ItemStack(Material.IRON_INGOT),

                VariantEnder.variant(), VariantHealth.variant(), VariantKaboom.variant(),
                VariantRainbow.variant(), VariantWater.variant(), VariantGlazed.variant(),
                VariantRedstone.variant(), VariantGlow.variant(), null,

                VariantZero.variant(), VariantNote.variant(), VariantStealth.variant(),
                VariantAgenda.variant(), VariantBoost.variant(), VariantCoal.variant(),
                VariantRunner.variant(), variantMoney, null
        };

    }

}
