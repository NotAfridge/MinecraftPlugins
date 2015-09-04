package com.ullarah.uchest.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.ullarah.uchest.ChestInit.*;

class DonationRandom {

    public static void fillDonationChest(CommandSender sender) {

        if (sender.hasPermission("chest.random") || !(sender instanceof Player)) {

            for (int i = 0; i < getChestDonationInventory().getSize(); i++) {

                java.util.Random chestRandomItem = new java.util.Random();

                if (chestRandomItem.nextInt() > 50) {

                    java.util.Random randomItem = new java.util.Random();
                    List<String> materialList = new ArrayList<>(
                            getPlugin().getConfig().getConfigurationSection("materials").getKeys(false));

                    getChestDonationInventory().setItem(i, new ItemStack(Material.getMaterial(
                            materialList.get(randomItem.nextInt(materialList.size())))));

                }

            }

            Bukkit.broadcastMessage(getMsgPrefix() + ChatColor.GREEN + "Donation Chest has been randomized with new items!");
            Bukkit.broadcastMessage(getMsgPrefix() + ChatColor.GREEN + "Quick! Use /dchest to open it up!");

        } else sender.sendMessage(getMsgPermDeny());

    }

}
