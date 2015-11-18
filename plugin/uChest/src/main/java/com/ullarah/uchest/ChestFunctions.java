package com.ullarah.uchest;

import com.ullarah.ulib.function.CommonString;
import com.ullarah.ulib.function.Experience;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.ullarah.uchest.ChestFunctions.validStorage.VAULT;
import static com.ullarah.uchest.ChestInit.*;

public class ChestFunctions {

    public static void convertItem(Player player, String type, ItemStack[] items) {

        DecimalFormat decimalFormat = new DecimalFormat(".##");

        double amount = 0.0;
        boolean hasItems = false;

        for (ItemStack item : items) {

            if (item != null) {

                String itemMaterial = item.getType().name();

                int itemAmount = item.getAmount();

                double itemValue = getPlugin().getConfig().getConfigurationSection("materials").getDouble(itemMaterial);
                double maxDurability = item.getType().getMaxDurability();
                double durability = item.getDurability();

                if (type.equals("MONEY")) itemValue /= 5;

                if (item.getType().getMaxDurability() > 0)
                    itemValue = itemValue - (itemValue * (durability / maxDurability));

                amount += itemValue * itemAmount;

                hasItems = true;

            }

        }

        if (hasItems) {

            if (type.equals("MONEY")) {

                getVaultEconomy().depositPlayer(player, amount);

                new CommonString().messageSend(getPlugin(), player, true, new String[]{
                        amount > 0 ? new CommonString().pluginPrefix(getPlugin()) + "You gained " + ChatColor.GREEN +
                                "$" + decimalFormat.format(amount) : new CommonString().pluginPrefix(getPlugin()) + "You gained no money."
                });

            }

            if (type.equals("XP")) {

                new Experience().addExperience(player, amount);

                new CommonString().messageSend(getPlugin(), player, true, new String[]{
                        amount > 0 ? new CommonString().pluginPrefix(getPlugin()) + "You gained " + ChatColor.GREEN +
                                decimalFormat.format(amount) + " XP" : new CommonString().pluginPrefix(getPlugin()) + "You gained no XP."
                });

            }

        }

    }

    public static void openConvertChest(CommandSender sender, String type) {

        final Player player = (Player) sender;
        int playerLevel = player.getLevel();

        if (playerLevel < chestAccessLevel) new CommonString().messageSend(getPlugin(), player, true, new String[]{
                "You need more than " + chestAccessLevel + " levels to open this chest."
        });
        else if (chestConvertLockout.contains(player.getUniqueId())) {

            if (!chestConvertLockoutTimer.containsKey(player.getUniqueId())) {

                chestConvertLockoutTimer.put(player.getUniqueId(), getPlugin().getConfig().getInt("convertlock"));

                new BukkitRunnable() {
                    int c = chestConvertLockoutTimer.get(player.getUniqueId());

                    @Override
                    public void run() {
                        if (c <= 0) {
                            chestConvertLockoutTimer.remove(player.getUniqueId());
                            chestConvertLockout.remove(player.getUniqueId());
                            this.cancel();
                            return;
                        }
                        chestConvertLockoutTimer.replace(player.getUniqueId(), c--);
                    }
                }.runTaskTimer(getPlugin(), 20, 20);

            }

            int getCurrentLockTime = chestConvertLockoutTimer.get(player.getUniqueId());

            String minuteString = " Minute";
            String secondString = " Second";

            long minute = TimeUnit.SECONDS.toMinutes(getCurrentLockTime) - (TimeUnit.SECONDS.toHours(getCurrentLockTime) * 60);
            long second = TimeUnit.SECONDS.toSeconds(getCurrentLockTime) - (TimeUnit.SECONDS.toMinutes(getCurrentLockTime) * 60);

            if (minute > 1) minuteString = minuteString + "s";
            if (second > 1) secondString = secondString + "s";

            String timeLeft = ChatColor.YELLOW + "";

            if (minute > 0) timeLeft += minute + minuteString + " ";
            if (second > 0) timeLeft += second + secondString;

            new CommonString().messageSend(getPlugin(), player, true, new String[]{
                    "You are currently locked out from conversion chests.",
                    "Try again in " + timeLeft
            });

        } else {

            Inventory chestExpInventory = Bukkit.createInventory(
                    player, 27, ChatColor.DARK_GREEN + (type.equals("MONEY") ? "Money" : "Experience") + " Chest");
            player.openInventory(chestExpInventory);

            chestConvertLockoutEntry(player);

        }

    }

    public static void openRandomChest(CommandSender sender) {

        final Player player = (Player) sender;
        int playerLevel = player.getLevel();

        if (playerLevel < randomAccessLevel) new CommonString().messageSend(getPlugin(), player, true, new String[]{
                "You need more than " + randomAccessLevel + " levels to open this chest."
        });
        else if (chestRandomLockout.contains(player.getUniqueId())) {

            if (!chestRandomLockoutTimer.containsKey(player.getUniqueId())) {

                chestRandomLockoutTimer.put(player.getUniqueId(), getPlugin().getConfig().getInt("ranlock"));

                new BukkitRunnable() {
                    int c = chestRandomLockoutTimer.get(player.getUniqueId());

                    @Override
                    public void run() {
                        if (c <= 0) {
                            chestRandomLockoutTimer.remove(player.getUniqueId());
                            chestRandomLockout.remove(player.getUniqueId());
                            this.cancel();
                            return;
                        }
                        chestRandomLockoutTimer.replace(player.getUniqueId(), c--);
                    }
                }.runTaskTimer(getPlugin(), 20, 20);

            }

            int getCurrentLockTime = chestRandomLockoutTimer.get(player.getUniqueId());

            String minuteString = " Minute";
            String secondString = " Second";

            long minute = TimeUnit.SECONDS.toMinutes(getCurrentLockTime) - (TimeUnit.SECONDS.toHours(getCurrentLockTime) * 60);
            long second = TimeUnit.SECONDS.toSeconds(getCurrentLockTime) - (TimeUnit.SECONDS.toMinutes(getCurrentLockTime) * 60);

            if (minute > 1) minuteString = minuteString + "s";
            if (second > 1) secondString = secondString + "s";

            String timeLeft = ChatColor.YELLOW + "";

            if (minute > 0) timeLeft += minute + minuteString + " ";
            if (second > 0) timeLeft += second + secondString;

            new CommonString().messageSend(getPlugin(), player, true, new String[]{
                    "You are currently locked out the random chest.",
                    "Try again in " + timeLeft
            });

        } else {

            player.setLevel(playerLevel - randomAccessLevel);

            Inventory randomInventory = getChestRandomHolder().getInventory();

            ItemStack greenGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);

            int randomTimer = getPlugin().getConfig().getInt("rantimer");

            player.openInventory(randomInventory);
            chestRandomLockoutEntry(player);

            for (int i = 45; i < 54; i++)
                randomInventory.setItem(i, greenGlass);

            BukkitTask task = new BukkitRunnable() {
                int c = randomTimer;

                @Override
                public void run() {
                    if (c <= 0) {
                        chestRandomTask.remove(player.getUniqueId());
                        player.closeInventory();
                        this.cancel();
                        return;
                    }
                    changeChest(randomInventory, (c * 100) / randomTimer);
                    c--;
                }
            }.runTaskTimer(getPlugin(), 5, 20);

            chestRandomTask.put(player.getUniqueId(), task);

        }

    }

    public static void changeChest(Inventory randomInventory, int percent) {

        Random randomSlot = new Random();
        ItemStack redGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);

        List<String> materialList = new ArrayList<>(
                getPlugin().getConfig().getConfigurationSection("materials").getKeys(false));

        for (int i = 0; i < 45; i++) getChestRandomInventory().setItem(i, null);

        getChestRandomInventory().setItem(randomSlot.nextInt(44),
                new ItemStack(Material.getMaterial(
                        materialList.get(randomSlot.nextInt(materialList.size())))));

        if (percent <= 90) randomInventory.setItem(53, redGlass);
        if (percent <= 80) randomInventory.setItem(52, redGlass);
        if (percent <= 70) randomInventory.setItem(51, redGlass);
        if (percent <= 60) randomInventory.setItem(50, redGlass);
        if (percent <= 50) randomInventory.setItem(49, redGlass);
        if (percent <= 40) randomInventory.setItem(48, redGlass);
        if (percent <= 30) randomInventory.setItem(47, redGlass);
        if (percent <= 20) randomInventory.setItem(46, redGlass);
        if (percent <= 10) randomInventory.setItem(45, redGlass);

    }

    public static void chestView(Player player, Player viewer, Inventory inventory, validStorage type) {

        if (viewer == null) {

            if (player.getLevel() <= holdingAccessLevel) {

                new CommonString().messageSend(getPlugin(), player, true, new String[]{
                        "You need more than " + holdingAccessLevel + " levels to open this chest."
                });

            } else {

                if (type == VAULT) player.setLevel(player.getLevel() - holdingAccessLevel);
                player.openInventory(inventory);

            }

        } else player.openInventory(inventory);

    }

    public static ItemStack createItemStack(Material material, String name, List<String> lore) {

        ItemStack chestIcon = new ItemStack(material);
        ItemMeta chestIconMeta = chestIcon.getItemMeta();

        chestIconMeta.setDisplayName(name);
        chestIconMeta.setLore(lore);

        chestIcon.setItemMeta(chestIconMeta);

        return chestIcon;

    }

    public static void openChestDelay(final Player player, final String type) {

        Bukkit.getServer().getScheduler().runTaskLater(getPlugin(), () -> Bukkit.dispatchCommand(player, type), 1);

    }

    private static void chestConvertLockoutEntry(Player player) {

        if (chestConvertLockoutCount.containsKey(player.getUniqueId())) {

            Integer count = chestConvertLockoutCount.get(player.getUniqueId());

            if (count < 2) chestConvertLockoutCount.put(player.getUniqueId(), count + 1);
            else chestConvertLockout.add(player.getUniqueId());

        } else chestConvertLockoutCount.put(player.getUniqueId(), 1);

    }

    private static void chestRandomLockoutEntry(Player player) {

        if (chestRandomLockoutCount.containsKey(player.getUniqueId())) {

            Integer count = chestRandomLockoutCount.get(player.getUniqueId());

            if (count < 2) chestRandomLockoutCount.put(player.getUniqueId(), count + 1);
            else chestRandomLockout.add(player.getUniqueId());

        } else chestRandomLockoutCount.put(player.getUniqueId(), 1);

    }

    public enum validCommands {
        HELP, MAINTENANCE, TOGGLE, RANDOM, RESET, VIEW, UPGRADE
    }

    public enum validStorage {
        HOLD("hold"), VAULT("vault");

        private final String type;

        validStorage(String getType) {
            type = getType;
        }

        public String toString() {
            return type;
        }
    }

}
