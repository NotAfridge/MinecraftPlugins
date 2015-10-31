package com.ullarah.uchest;

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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.ullarah.uchest.ChestFunctions.validStorage.VAULT;
import static com.ullarah.uchest.ChestInit.*;
import static com.ullarah.ulib.function.CommonString.messagePlayer;
import static com.ullarah.ulib.function.CommonString.pluginPrefix;

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

                messagePlayer(getPlugin(), player, new String[]{
                        amount > 0 ? pluginPrefix(getPlugin()) + "You gained " + ChatColor.GREEN +
                                "$" + decimalFormat.format(amount) : pluginPrefix(getPlugin()) + "You gained no money."
                });

            }

            if (type.equals("XP")) {

                Experience.addExperience(player, amount);

                messagePlayer(getPlugin(), player, new String[]{
                        amount > 0 ? pluginPrefix(getPlugin()) + "You gained " + ChatColor.GREEN +
                                decimalFormat.format(amount) + " XP" : pluginPrefix(getPlugin()) + "You gained no XP."
                });

            }

        }

    }

    public static void openConvertChest(CommandSender sender, String type) {

        final Player player = (Player) sender;
        int playerLevel = player.getLevel();

        if (playerLevel < chestAccessLevel) messagePlayer(getPlugin(), player, new String[]{
                "You need more than " + chestAccessLevel + " levels to open this chest."
        });
        else if (chestLockout.contains(player.getUniqueId())) {

            messagePlayer(getPlugin(), player, new String[]{
                    "You are currently locked out from this chest.",
                    "Don't panic. Just try again later!"
            });

            Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(
                    getPlugin(), () -> chestLockout.remove(player.getUniqueId()), 6000);

        } else {

            Inventory chestExpInventory = Bukkit.createInventory(
                    player, 27, ChatColor.DARK_GREEN + (type.equals("MONEY") ? "Money" : "Experience") + " Chest");
            player.openInventory(chestExpInventory);

            chestLockoutEntry(player);

        }

    }

    public static void openRandomChest(CommandSender sender) {

        final Player player = (Player) sender;
        int playerLevel = player.getLevel();

        if (playerLevel < randomAccessLevel) messagePlayer(getPlugin(), player, new String[]{
                "You need more than " + randomAccessLevel + " levels to open this chest."
        });
        else if (chestLockout.contains(player.getUniqueId())) {

            messagePlayer(getPlugin(), player, new String[]{
                    "You are currently locked out from this chest.",
                    "Don't panic. Just try again later!"
            });

            Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(
                    getPlugin(), () -> chestLockout.remove(player.getUniqueId()), 6000);

        } else {

            player.setLevel(playerLevel - randomAccessLevel);

            Inventory randomInventory = getChestRandomHolder().getInventory();

            ItemStack greenGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);

            int randomTimer = getPlugin().getConfig().getInt("rantimer");

            player.openInventory(randomInventory);
            chestLockoutEntry(player);

            for (int i = 45; i < 54; i++)
                randomInventory.setItem(i, greenGlass);

            new BukkitRunnable() {
                int c = randomTimer;

                @Override
                public void run() {
                    if (c <= 0) {
                        player.closeInventory();
                        this.cancel();
                        return;
                    }
                    changeChest(randomInventory, (c * 100) / randomTimer);
                    c--;
                }
            }.runTaskTimer(getPlugin(), 5, 20);

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

                messagePlayer(getPlugin(), player, new String[]{
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

    private static void chestLockoutEntry(Player player) {

        if (chestLockoutCount.containsKey(player.getUniqueId())) {

            Integer count = chestLockoutCount.get(player.getUniqueId());

            if (count < 2) chestLockoutCount.put(player.getUniqueId(), count + 1);
            else chestLockout.add(player.getUniqueId());

        } else chestLockoutCount.put(player.getUniqueId(), 1);

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
