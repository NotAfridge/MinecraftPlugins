package com.ullarah.uchest.command;

import com.ullarah.uchest.ChestInit;
import com.ullarah.uchest.function.CommonString;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DonationRandom {

    final CommonString commonString = new CommonString();

    public void checkPermission(CommandSender sender) {

        if (!sender.hasPermission("chest.random")) {
            commonString.messagePermDeny(ChestInit.getPlugin(), sender);
            return;
        }

        chestRandomFill();

    }

    public void chestRandomFill() {

        for (int i = 0; i < ChestInit.getChestDonationInventory().getSize(); i++) {

            if (new Random().nextInt(100) > 50) {

                List<ItemStack> materialKeys = new ArrayList<>(ChestInit.materialMap.keySet());
                ItemStack itemStack = materialKeys.get(new Random().nextInt(materialKeys.size()));

                if ((boolean) ChestInit.materialMap.get(itemStack)[1])
                    ChestInit.getChestDonationInventory().setItem(i, itemStack);

            }

        }

        int restrictSeconds = ChestInit.getPlugin().getConfig().getInt("dchest.random.itemsecond");

        for (Player player : ChestInit.getPlugin().getServer().getOnlinePlayers()) {

            commonString.messageSend(ChestInit.getPlugin(), player, true, new String[]{
                    ChatColor.GREEN + "Donation Chest has been randomized with new items!",
                    ChatColor.GREEN + "Quick! Use " + ChatColor.YELLOW + "/chest" + ChatColor.GREEN + " to open it up!"
            });

            if (restrictSeconds > 0) {
                String second = restrictSeconds + " seconds.";
                if (restrictSeconds == 1) second = "second.";
                commonString.messageSend(ChestInit.getPlugin(), player, "" + ChatColor.GRAY +
                        ChatColor.ITALIC + "You are restricted to one item every " + second);
            }

        }

        ChestInit.chestDonateLock = true;
        chestDonateCountdown();

    }

    private void chestDonateCountdown() {

        new BukkitRunnable() {
            int c = ChestInit.getPlugin().getConfig().getInt("dchest.random.countdown");

            @Override
            public void run() {
                if (c <= 0) {
                    ChestInit.chestDonateLock = false;
                    this.cancel();
                    return;
                }
                c--;
            }
        }.runTaskTimer(ChestInit.getPlugin(), 0, 20);

    }

}
