package com.ullarah.uchest.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

import static com.ullarah.uchest.ChestInit.*;

public class DonationRandom {

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

            int lockedSeconds = getPlugin().getConfig().getInt("unlock");

            for (Player player : Bukkit.getOnlinePlayers()) {

                player.sendMessage(new String[]{
                        getMsgPrefix() + ChatColor.GREEN + "Donation Chest has been randomized with new items!",
                        getMsgPrefix() + ChatColor.GREEN + "Quick! Use /dchest to open it up!",
                });

                if (lockedSeconds > 0) {
                    String end = lockedSeconds + " seconds.";
                    if (lockedSeconds == 1) end = "second.";
                    player.sendMessage(getMsgPrefix() + ChatColor.GRAY + ChatColor.ITALIC
                            + "You are restricted to one item every " + end);
                }

            }

            chestDonateLock = true;
            chestDonateCountdown();

        } else sender.sendMessage(getMsgPermDeny());

    }

    private static void chestDonateCountdown() {
        new BukkitRunnable() {
            int c = getPlugin().getConfig().getInt("donatelock");

            @Override
            public void run() {
                if (c <= 0) {
                    chestDonateLock = false;
                    this.cancel();
                    return;
                }
                c--;
            }
        }.runTaskTimer(getPlugin(), 0, 20);
    }

}
