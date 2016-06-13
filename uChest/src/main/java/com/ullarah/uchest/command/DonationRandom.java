package com.ullarah.uchest.command;

import com.ullarah.uchest.function.CommonString;
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

    CommonString commonString = new CommonString();

    public void checkPermission(CommandSender sender) {

        if (!sender.hasPermission("chest.random")) {
            commonString.messagePermDeny(getPlugin(), sender);
            return;
        }

        chestRandomFill();

    }

    public void chestRandomFill() {

        for (int i = 0; i < getChestDonationInventory().getSize(); i++) {

            java.util.Random chestRandomItem = new java.util.Random();

            if (chestRandomItem.nextInt() > 50) {

                List<String> materialList = new ArrayList<>(getMaterialConfig().getKeys(false));

                getChestDonationInventory().setItem(i, new ItemStack(Material.getMaterial(
                        materialList.get(new java.util.Random().nextInt(materialList.size())))));

            }

        }

        int restrictSeconds = getPlugin().getConfig().getInt("dchest.random.itemsecond");

        for (Player player : getPlugin().getServer().getOnlinePlayers()) {

            commonString.messageSend(getPlugin(), player, true, new String[]{
                    ChatColor.GREEN + "Donation Chest has been randomized with new items!",
                    ChatColor.GREEN + "Quick! Use " + ChatColor.YELLOW + "/dchest" + ChatColor.GREEN + " to open it up!"
            });

            if (restrictSeconds > 0) {
                String second = restrictSeconds + " seconds.";
                if (restrictSeconds == 1) second = "second.";
                commonString.messageSend(getPlugin(), player, "" + ChatColor.GRAY +
                        ChatColor.ITALIC + "You are restricted to one item every " + second);
            }

        }

        chestDonateLock = true;
        chestDonateCountdown();

    }

    private void chestDonateCountdown() {

        new BukkitRunnable() {
            int c = getPlugin().getConfig().getInt("dchest.random.countdown");

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
