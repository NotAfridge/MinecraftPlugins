package com.ullarah.uchest;

import com.ullarah.uchest.function.CommonString;
import com.ullarah.uchest.function.Experience;
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
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static com.ullarah.uchest.ChestFunctions.validStorage.VAULT;
import static com.ullarah.uchest.ChestInit.*;

public class ChestFunctions {

    private final CommonString commonString = new CommonString();

    public void convertItem(Player player, String type, ItemStack[] items) {

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
                commonString.messageSend(getPlugin(), player, amount > 0
                        ? new CommonString().pluginPrefix(getPlugin()) + "You gained " + ChatColor.GREEN + "$" +
                        decimalFormat.format(amount) : commonString.pluginPrefix(getPlugin()) + "You gained no money.");

            }

            if (type.equals("XP")) {

                new Experience().addExperience(player, amount);
                commonString.messageSend(getPlugin(), player, amount > 0
                        ? new CommonString().pluginPrefix(getPlugin()) + "You gained " + ChatColor.GREEN +
                        decimalFormat.format(amount) + " XP" : commonString.pluginPrefix(getPlugin()) + "You gained no XP.");

            }

        }

    }

    public void openConvertChest(CommandSender sender, String type) {

        final Player player = (Player) sender;

        if (player.getLevel() < chestAccessLevel) {
            commonString.messageSend(getPlugin(), player, "You need more than " + chestAccessLevel + " levels to open this chest.");
            return;
        }

        if (chestConvertLockout.contains(player.getUniqueId())) {
            chestLockout(player, "convertlock", chestConvertLockoutTimer, chestConvertLockout);
            return;
        }

        Inventory chestExpInventory = Bukkit.createInventory(player, 27, ChatColor.DARK_GREEN + (type.equals("MONEY") ? "Money" : "Experience") + " Chest");
        player.openInventory(chestExpInventory);
        chestLockoutEntry(player, chestConvertLockoutTimer, chestConvertLockout);

    }

    public void openRandomChest(CommandSender sender) {

        final Player player = (Player) sender;
        int playerLevel = player.getLevel();

        if (playerLevel < randomAccessLevel) {
            commonString.messageSend(getPlugin(), player, "You need more than " + randomAccessLevel + " levels to open this chest.");
            return;
        }

        if (chestRandomLockout.contains(player.getUniqueId())) {
            chestLockout(player, "ranlock", chestRandomLockoutTimer, chestRandomLockout);
            return;
        }

        player.setLevel(playerLevel - randomAccessLevel);
        Inventory randomInventory = getChestRandomHolder().getInventory();

        int randomTimer = getPlugin().getConfig().getInt("rantimer");

        player.openInventory(randomInventory);
        chestLockoutEntry(player, chestRandomLockoutTimer, chestRandomLockout);

        for (int i = 45; i < 54; i++)
            randomInventory.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5));

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

    public void chestLockout(Player player, String configTimer, ConcurrentHashMap<UUID, Integer> lockMap, Set<UUID> lockSet) {

        if (!lockMap.containsKey(player.getUniqueId())) {

            lockMap.put(player.getUniqueId(), getPlugin().getConfig().getInt(configTimer));

            new BukkitRunnable() {

                int c = lockMap.get(player.getUniqueId());

                @Override
                public void run() {

                    if (c <= 0) {

                        lockMap.remove(player.getUniqueId());
                        lockSet.remove(player.getUniqueId());
                        this.cancel();
                        return;

                    }

                    lockMap.replace(player.getUniqueId(), c--);

                }

            }.runTaskTimer(getPlugin(), 20, 20);

        }

        int getCurrentLockTime = lockMap.get(player.getUniqueId());

        String minuteString = " Minute";
        String secondString = " Second";

        long minute = TimeUnit.SECONDS.toMinutes(getCurrentLockTime) - (TimeUnit.SECONDS.toHours(getCurrentLockTime) * 60);
        long second = TimeUnit.SECONDS.toSeconds(getCurrentLockTime) - (TimeUnit.SECONDS.toMinutes(getCurrentLockTime) * 60);

        if (minute > 1) minuteString = minuteString + "s";
        if (second > 1) secondString = secondString + "s";

        String timeLeft = ChatColor.YELLOW + "";

        if (minute > 0) timeLeft += minute + minuteString + " ";
        if (second > 0) timeLeft += second + secondString;

        commonString.messageSend(getPlugin(), player, true, new String[]{
                "You are currently locked out from this chest.", "Try again in " + timeLeft
        });

    }

    public void changeChest(Inventory randomInventory, int percent) {

        Random randomSlot = new Random();
        ItemStack redGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);

        List<String> materialList = new ArrayList<>(getPlugin().getConfig().getConfigurationSection("materials").getKeys(false));

        for (int i = 0; i < 45; i++) getChestRandomInventory().setItem(i, null);

        getChestRandomInventory().setItem(randomSlot.nextInt(44),
                new ItemStack(Material.getMaterial(materialList.get(randomSlot.nextInt(materialList.size())))));

        int itemSlot = 53;
        for (int p = percent; p > 0; p -= 10) {
            randomInventory.setItem(itemSlot, redGlass);
            itemSlot--;
        }

    }

    public void chestView(Player player, Player viewer, Inventory inventory, validStorage type) {

        if (viewer == null) {

            if (player.getLevel() < holdingAccessLevel) {
                commonString.messageSend(getPlugin(), player, "You need more than " + holdingAccessLevel + " levels to open this chest.");
                return;
            }

            if (type == VAULT) player.setLevel(player.getLevel() - holdingAccessLevel);
            player.openInventory(inventory);

        } else player.openInventory(inventory);

    }

    public ItemStack createItemStack(Material material, String name, List<String> lore) {

        ItemStack chestIcon = new ItemStack(material);
        ItemMeta chestIconMeta = chestIcon.getItemMeta();

        chestIconMeta.setDisplayName(name);
        chestIconMeta.setLore(lore);

        chestIcon.setItemMeta(chestIconMeta);

        return chestIcon;

    }

    public void openChestDelay(final Player player, final String type) {

        Bukkit.getServer().getScheduler().runTaskLater(getPlugin(), () -> Bukkit.dispatchCommand(player, type), 1);

    }

    private void chestLockoutEntry(Player player, ConcurrentHashMap<UUID, Integer> lockMap, Set<UUID> lockSet) {

        if (lockMap.containsKey(player.getUniqueId())) {

            Integer count = lockMap.get(player.getUniqueId());

            if (count < 2) lockMap.put(player.getUniqueId(), count + 1);
            else lockSet.add(player.getUniqueId());

        } else lockMap.put(player.getUniqueId(), 1);

    }

    public enum validCommands {
        HELP, TOGGLE, RANDOM, RESET, VIEW, UPGRADE
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
