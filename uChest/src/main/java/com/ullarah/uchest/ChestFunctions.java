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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static com.ullarah.uchest.ChestFunctions.validConvert.MONEY;
import static com.ullarah.uchest.ChestFunctions.validConvert.XP;
import static com.ullarah.uchest.ChestFunctions.validStorage.VAULT;
import static com.ullarah.uchest.ChestInit.*;

public class ChestFunctions {

    private final CommonString commonString = new CommonString();

    public void convertItem(Player player, validConvert type, ItemStack[] items) {

        DecimalFormat decimalFormat = new DecimalFormat(".##");

        double amount = 0.0;
        boolean hasItems = false;

        for (ItemStack item : items) {

            if (item != null) {

                String itemMaterial = item.getType().name();

                int itemAmount = item.getAmount();

                double itemValue = ChestInit.getMaterialConfig().getDouble(itemMaterial);
                double maxDurability = item.getType().getMaxDurability();
                double durability = item.getDurability();

                if (type == MONEY) itemValue /= 5;
                if (item.getType().getMaxDurability() > 0)
                    itemValue = itemValue - (itemValue * (durability / maxDurability));

                amount += itemValue * itemAmount;

                hasItems = true;

            }

        }

        if (hasItems) {

            if (type == MONEY) {
                getVaultEconomy().depositPlayer(player, amount);
                commonString.messageSend(getPlugin(), player, amount > 0
                        ? "You gained " + ChatColor.GREEN + "$" + decimalFormat.format(amount) : "You gained no money.");
            }

            if (type == XP) {
                new Experience().addExperience(player, amount);
                commonString.messageSend(getPlugin(), player, amount > 0
                        ? "You gained " + ChatColor.GREEN + decimalFormat.format(amount) + " XP" : "You gained no XP.");
            }

        }

    }

    public void openConvertChest(CommandSender sender, validConvert type) {

        final Player player = (Player) sender;
        int playerLevel = player.getLevel();

        int lockTimer = getPlugin().getConfig().getInt((type == MONEY ? "m" : "x") + "chest.lockout");

        int accessMoney = getPlugin().getConfig().getInt("mchest.access");
        int accessExperience = getPlugin().getConfig().getInt("xchest.access");

        boolean removeMoney = getPlugin().getConfig().getBoolean("mchest.removelevel");
        boolean removeExperience = getPlugin().getConfig().getBoolean("xchest.removelevel");

        int accessLevel = (type == MONEY ? accessMoney : accessExperience);
        boolean removeLevel = (type == MONEY ? removeMoney : removeExperience);

        if (player.getLevel() < accessLevel) {
            String s = accessLevel > 1 ? "s" : "";
            commonString.messageSend(getPlugin(), player, "You need more than " + accessLevel + " level" + s + " to open this chest.");
            return;
        }

        if (removeLevel) player.setLevel(playerLevel - accessLevel);

        if (!chestConvertLockoutTimer.containsKey(player.getUniqueId())) {
            Inventory chestExpInventory = Bukkit.createInventory(player, 27, ChatColor.DARK_GREEN + (type == MONEY ? "Money" : "Experience") + " Chest");
            player.openInventory(chestExpInventory);
        }

        if (lockTimer > 0) chestLockout(player, lockTimer, chestConvertLockoutTimer);

    }

    public void openRandomChest(CommandSender sender) {

        final Player player = (Player) sender;
        int playerLevel = player.getLevel();
        int accessLevel = getPlugin().getConfig().getInt("rchest.access");
        int lockTimer = getPlugin().getConfig().getInt("rchest.lockout");
        boolean removeLevel = getPlugin().getConfig().getBoolean("rchest.removelevel");

        if (playerLevel < accessLevel) {
            String s = accessLevel > 1 ? "s" : "";
            commonString.messageSend(getPlugin(), player, "You need more than " + accessLevel + " level" + s + " to open this chest.");
            return;
        }

        if (removeLevel) player.setLevel(playerLevel - accessLevel);

        if (!chestRandomLockoutTimer.containsKey(player.getUniqueId()))
            player.openInventory(getChestRandomHolder().getInventory());

        if (lockTimer > 0) chestLockout(player, lockTimer, chestRandomLockoutTimer);

        BukkitTask task = new BukkitRunnable() {

            int c = getPlugin().getConfig().getInt("rchest.timer");

            @Override
            public void run() {

                if (c <= 0) {

                    chestRandomTask.remove(player.getUniqueId());
                    player.closeInventory();
                    this.cancel();
                    return;

                }

                Random randomSlot = new Random();

                List<String> materialList = new ArrayList<>(ChestInit.getMaterialConfig().getKeys(false));

                for (int i = 0; i < 54; i++) getChestRandomInventory().setItem(i, null);

                getChestRandomInventory().setItem(randomSlot.nextInt(54),
                        new ItemStack(Material.getMaterial(materialList.get(randomSlot.nextInt(materialList.size())))));

                c--;

            }

        }.runTaskTimer(getPlugin(), 5, 25);

        chestRandomTask.put(player.getUniqueId(), task);

    }

    private void chestLockout(Player player, Integer lockTimer, ConcurrentHashMap<UUID, Integer> lockMap) {

        if (!lockMap.containsKey(player.getUniqueId())) {

            lockMap.put(player.getUniqueId(), lockTimer);

            new BukkitRunnable() {

                int c = lockTimer;

                @Override
                public void run() {

                    if (c <= 0) {

                        lockMap.remove(player.getUniqueId());
                        this.cancel();
                        return;

                    }

                    c--;
                    lockMap.replace(player.getUniqueId(), c);

                }

            }.runTaskTimer(getPlugin(), 20, 20);

        }

        int getCurrentLockTime = lockMap.get(player.getUniqueId());

        String minuteString = " Minute";
        String secondString = " Second";

        long minute = TimeUnit.SECONDS.toMinutes(getCurrentLockTime) - (TimeUnit.SECONDS.toHours(getCurrentLockTime) * 60);
        long second = TimeUnit.SECONDS.toSeconds(getCurrentLockTime) - (TimeUnit.SECONDS.toMinutes(getCurrentLockTime) * 60);

        if (minute > 1) minuteString += "s";
        if (second > 1) secondString += "s";

        String timeLeft = ChatColor.YELLOW + "";

        if (minute > 0) timeLeft += minute + minuteString + " " + second + secondString;
        else timeLeft += second + secondString;

        if (getCurrentLockTime <= (lockTimer - 2)) commonString.messageSend(getPlugin(), player, true, new String[]{
                "You are currently locked out from this chest.", "Try again in " + timeLeft
        });

    }

    public void chestView(Player player, UUID uuid, Inventory inventory, validStorage type) {

        UUID viewerUUID = player.getUniqueId();

        if (viewerUUID.equals(uuid)) {

            int playerLevel = player.getLevel();

            int accessHold = getPlugin().getConfig().getInt("hchest.access");
            int accessVault = getPlugin().getConfig().getInt("vchest.access");

            boolean removeHold = getPlugin().getConfig().getBoolean("hchest.removelevel");
            boolean removeVault = getPlugin().getConfig().getBoolean("vchest.removelevel");

            int accessLevel = (type == VAULT ? accessVault : accessHold);
            boolean removeLevel = (type == VAULT ? removeVault : removeHold);

            if (player.getLevel() < accessLevel) {
                String s = accessLevel > 1 ? "s" : "";
                commonString.messageSend(getPlugin(), player, "You need more than " + accessLevel + " level" + s + " to open this chest.");
                return;
            }

            if (removeLevel) player.setLevel(playerLevel - accessLevel);
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

    public enum validConvert {
        XP("xp"), MONEY("money");

        private final String type;

        validConvert(String getType) {
            type = getType;
        }

        public String toString() {
            return type;
        }
    }

}
