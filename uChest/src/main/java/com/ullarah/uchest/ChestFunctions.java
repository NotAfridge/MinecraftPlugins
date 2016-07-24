package com.ullarah.uchest;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.ullarah.uchest.command.ChestPickup;
import com.ullarah.uchest.function.CommonString;
import com.ullarah.uchest.function.Experience;
import com.ullarah.uchest.init.ChestLanguage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
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

public class ChestFunctions {

    private final CommonString commonString = new CommonString();

    public void convertItem(Player player, ValidChest type, ItemStack[] items) {

        DecimalFormat decimalFormat = new DecimalFormat(".##");

        double amount = 0.0;
        boolean hasItems = false;

        for (ItemStack item : items) {

            if (ChestInit.materialMap.containsKey(item)) {

                Object[] materialObject = ChestInit.materialMap.get(new ItemStack(item.getType(), 1, item.getDurability()));
                double itemValue = type == ValidChest.MONEY ? (double) materialObject[3] : (double) materialObject[4];

                double maxDurability = item.getType().getMaxDurability();
                double durability = item.getDurability();

                if (item.getType().getMaxDurability() > 0)
                    itemValue -= (itemValue * (durability / maxDurability));

                amount += itemValue * item.getAmount();
                hasItems = true;

            }

        }

        if (hasItems) {

            if (type == ValidChest.MONEY) {
                if (ChestInit.getVaultEconomy() == null) {
                    for (ItemStack item : items)
                        if (item != null) player.getWorld().dropItemNaturally(player.getLocation(), item);
                    commonString.messageSend(ChestInit.getPlugin(), player, "Money Chest is currently unavailable.");
                    return;
                }

                ChestInit.getVaultEconomy().depositPlayer(player, amount);
                commonString.messageSend(ChestInit.getPlugin(), player, amount > 0
                        ? "You gained " + ChatColor.GREEN + "$" + decimalFormat.format(amount) : "You gained no money.");
            }

            if (type == ValidChest.XP) {
                new Experience().addExperience(player, amount);
                commonString.messageSend(ChestInit.getPlugin(), player, amount > 0
                        ? "You gained " + ChatColor.GREEN + decimalFormat.format(amount) + " XP" : "You gained no XP.");
            }

        }

    }

    public void enchantItem(Player player, ItemStack item) {

        boolean useUnsafe = ChestInit.getPlugin().getConfig().getBoolean("echest.unsafe");

        if (item == null) return;

        if (item.getAmount() > 1) {
            commonString.messageSend(ChestInit.getPlugin(), player, "You are only allowed to enchant a single item.");
            player.getWorld().dropItemNaturally(player.getLocation(), item);
            return;
        }

        ItemMeta itemMeta = item.getItemMeta();

        if (itemMeta.hasEnchants()) {
            commonString.messageSend(ChestInit.getPlugin(), player, "This item already has an enchantment.");
            player.getWorld().dropItemNaturally(player.getLocation(), item);
            return;
        }

        List<Enchantment> allEnchants = new ArrayList<>();
        for (Enchantment enchant : Enchantment.values()) {
            if (useUnsafe) allEnchants.add(enchant);
            else if (enchant.canEnchantItem(item)) allEnchants.add(enchant);
        }

        if (allEnchants.size() >= 1) {
            Collections.shuffle(allEnchants);
            Enchantment gotEnchant = allEnchants.get(0);
            item.addEnchantment(gotEnchant, 1 + (int) (Math.random() * ((gotEnchant.getMaxLevel() - 1) + 1)));
        }

        player.getWorld().dropItemNaturally(player.getLocation(), item);

    }

    public void openConvertChest(CommandSender sender, ValidChest type) {

        final Player player = (Player) sender;
        int playerLevel = player.getLevel();
        String chestType = type == ValidChest.MONEY ? "mchest" : "xchest";

        int lockTimer = ChestInit.getPlugin().getConfig().getInt(chestType + ".lockout");
        int accessLevel = ChestInit.getPlugin().getConfig().getInt(chestType + ".access");
        boolean removeLevel = ChestInit.getPlugin().getConfig().getBoolean(chestType + ".removelevel");

        if (player.getLevel() < accessLevel) {
            String s = accessLevel > 1 ? "s" : "";
            commonString.messageSend(ChestInit.getPlugin(), player, "You need more than "
                    + accessLevel + " level" + s + " to open this chest.");
            return;
        }

        Inventory chestExpInventory = Bukkit.createInventory(player, 27, ChatColor.DARK_GREEN
                + (type == ValidChest.MONEY ? "Money" : "Experience") + " Chest");

        if (player.hasPermission("chest.bypass")) {
            player.openInventory(chestExpInventory);
            return;
        }

        if (!ChestInit.chestLockoutMap.get(chestType).containsKey(player.getUniqueId())) {
            if (removeLevel) player.setLevel(playerLevel - accessLevel);
            player.openInventory(chestExpInventory);
        }

        if (lockTimer > 0) chestLockout(player, lockTimer, chestType);

    }

    public void openEnchantmentChest(CommandSender sender) {

        final Player player = (Player) sender;
        int playerLevel = player.getLevel();
        String chestType = "echest";

        int lockTimer = ChestInit.getPlugin().getConfig().getInt(chestType + ".lockout");
        int accessLevel = ChestInit.getPlugin().getConfig().getInt(chestType + ".access");
        boolean removeLevel = ChestInit.getPlugin().getConfig().getBoolean(chestType + ".removelevel");

        if (player.getLevel() < accessLevel) {
            String s = accessLevel > 1 ? "s" : "";
            commonString.messageSend(ChestInit.getPlugin(), player, "You need more than "
                    + accessLevel + " level" + s + " to open this chest.");
            return;
        }

        Inventory chestEnchantInventory = Bukkit.createInventory(player, 9, ChestLanguage.N_ECHEST);

        ItemStack blockedItem = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemMeta blockedItemMeta = blockedItem.getItemMeta();

        blockedItemMeta.setDisplayName(ChatColor.GRAY + "Use Middle Slot");
        blockedItem.setItemMeta(blockedItemMeta);

        for (int b : new int[]{0, 1, 2, 3, 5, 6, 7, 8}) chestEnchantInventory.setItem(b, blockedItem);

        if (player.hasPermission("chest.bypass")) {
            player.openInventory(chestEnchantInventory);
            return;
        }

        if (!ChestInit.chestLockoutMap.get(chestType).containsKey(player.getUniqueId())) {
            if (removeLevel) player.setLevel(playerLevel - accessLevel);
            player.openInventory(chestEnchantInventory);
        }

        if (lockTimer > 0) chestLockout(player, lockTimer, chestType);

    }

    public void openRandomChest(CommandSender sender) {

        final Player player = (Player) sender;
        int playerLevel = player.getLevel();
        String chestType = "rchest";
        int accessLevel = ChestInit.getPlugin().getConfig().getInt(chestType + ".access");
        int lockTimer = ChestInit.getPlugin().getConfig().getInt(chestType + ".lockout");
        boolean removeLevel = ChestInit.getPlugin().getConfig().getBoolean(chestType + ".removelevel");

        if (playerLevel < accessLevel) {
            String s = accessLevel > 1 ? "s" : "";
            commonString.messageSend(ChestInit.getPlugin(), player, "You need more than "
                    + accessLevel + " level" + s + " to open this chest.");
            return;
        }

        if (player.hasPermission("chest.bypass")) player.openInventory(ChestInit.getChestRandomInventory());
        else {

            if (!ChestInit.chestLockoutMap.get(chestType).containsKey(player.getUniqueId())) {
                if (removeLevel) player.setLevel(playerLevel - accessLevel);
                player.openInventory(ChestInit.getChestRandomInventory());
            }

            if (lockTimer > 0) chestLockout(player, lockTimer, chestType);

        }

        BukkitTask task = new BukkitRunnable() {

            int c = ChestInit.getPlugin().getConfig().getInt(chestType + ".timer");

            @Override
            public void run() {

                if (c <= 0) {

                    ChestInit.chestRandomTask.remove(player.getUniqueId());
                    player.closeInventory();
                    this.cancel();
                    return;

                }

                ChestInit.getChestRandomInventory().clear();

                ItemStack itemStack = getRandomItem();
                int chestSize = new Random().nextInt(ChestInit.getChestRandomInventory().getSize());

                ChestInit.getChestRandomInventory().setItem(chestSize, itemStack);

                c--;

            }

        }.runTaskTimer(ChestInit.getPlugin(), 5, 25);

        ChestInit.chestRandomTask.put(player.getUniqueId(), task);

    }

    private ItemStack getRandomItem() {

        List<ItemStack> materialKeys = new ArrayList<>(ChestInit.materialMap.keySet());

        ItemStack item = materialKeys.get(new Random().nextInt(materialKeys.size()));

        if ((boolean) ChestInit.materialMap.get(item)[2]) return item;
        else getRandomItem();

        return new ItemStack(Material.AIR);

    }

    public void openShuffleChest(CommandSender sender) {

        final Player player = (Player) sender;
        int playerLevel = player.getLevel();
        String chestType = "schest";
        int accessLevel = ChestInit.getPlugin().getConfig().getInt(chestType + ".access");
        int lockTimer = ChestInit.getPlugin().getConfig().getInt(chestType + ".lockout");
        boolean removeLevel = ChestInit.getPlugin().getConfig().getBoolean(chestType + ".removelevel");

        if (playerLevel < accessLevel) {
            String s = accessLevel > 1 ? "s" : "";
            commonString.messageSend(ChestInit.getPlugin(), player, "You need more than "
                    + accessLevel + " level" + s + " to open this chest.");
            return;
        }

        if (player.hasPermission("chest.bypass")) player.openInventory(ChestInit.getChestShuffleInventory());
        else {

            if (!ChestInit.chestLockoutMap.get(chestType).containsKey(player.getUniqueId())) {
                if (removeLevel) player.setLevel(playerLevel - accessLevel);
                player.openInventory(ChestInit.getChestShuffleInventory());
            }

            if (lockTimer > 0) chestLockout(player, lockTimer, chestType);

        }

    }

    public void chestLockout(Player player, Integer lockTimer, String lockChest) {

        ConcurrentHashMap<UUID, Integer> timerMap = ChestInit.chestLockoutMap.get(lockChest);

        if (!timerMap.containsKey(player.getUniqueId())) {

            timerMap.put(player.getUniqueId(), lockTimer);

            new BukkitRunnable() {

                int c = lockTimer;

                @Override
                public void run() {

                    if (c <= 0) {

                        timerMap.remove(player.getUniqueId());
                        this.cancel();
                        return;

                    }

                    c--;
                    timerMap.replace(player.getUniqueId(), c);
                    ChestInit.chestLockoutMap.replace(lockChest, timerMap);

                }

            }.runTaskTimer(ChestInit.getPlugin(), 20, 20);

        }

        int getCurrentLockTime = timerMap.get(player.getUniqueId());

        String minuteString = " Minute";
        String secondString = " Second";

        long minute = TimeUnit.SECONDS.toMinutes(getCurrentLockTime) - (TimeUnit.SECONDS.toHours(getCurrentLockTime) * 60);
        long second = TimeUnit.SECONDS.toSeconds(getCurrentLockTime) - (TimeUnit.SECONDS.toMinutes(getCurrentLockTime) * 60);

        if (minute > 1) minuteString += "s";
        if (second > 1) secondString += "s";

        String timeLeft = ChatColor.YELLOW + "";

        if (minute > 0) timeLeft += minute + minuteString + " " + second + secondString;
        else timeLeft += second + secondString;

        if (getCurrentLockTime <= (lockTimer - 2))
            commonString.messageSend(ChestInit.getPlugin(), player, true, new String[]{
                "You are currently locked out from this chest.", "Try again in " + timeLeft
        });

    }

    public void chestView(Player player, UUID uuid, Inventory inventory, ValidChest type) {

        UUID viewerUUID = player.getUniqueId();

        if (viewerUUID.equals(uuid)) {

            int playerLevel = player.getLevel();

            String chestType = type == ValidChest.VAULT ? "vchest" : "hchest";

            int lockTimer = ChestInit.getPlugin().getConfig().getInt(chestType + ".lockout");
            int accessLevel = ChestInit.getPlugin().getConfig().getInt(chestType + ".access");
            boolean removeLevel = ChestInit.getPlugin().getConfig().getBoolean(chestType + ".removelevel");

            if (player.getLevel() < accessLevel) {
                String s = accessLevel > 1 ? "s" : "";
                commonString.messageSend(ChestInit.getPlugin(), player, "You need more than "
                        + accessLevel + " level" + s + " to open this chest.");
                return;
            }

            if (player.hasPermission("chest.bypass")) {
                player.openInventory(inventory);
                return;
            }

            if (!ChestInit.chestLockoutMap.get(chestType).containsKey(player.getUniqueId())) {
                if (removeLevel) player.setLevel(playerLevel - accessLevel);
                player.openInventory(inventory);
            }

            if (lockTimer > 0) chestLockout(player, lockTimer, chestType);

        } else player.openInventory(inventory);

    }

    public boolean checkPickupTool(ItemStack item) {

        if (item.getType() == Material.SHEARS) if (item.hasItemMeta()) if (item.getItemMeta().hasDisplayName())
            if (item.getItemMeta().getDisplayName().equals(ChestPickup.toolName)) return true;

        return false;

    }

    public boolean checkContentChest(ItemStack item) {

        if (item.getType() == Material.CHEST) if (item.hasItemMeta()) if (item.getItemMeta().hasDisplayName())
            if (item.getItemMeta().getDisplayName().matches(
                    "" + ChatColor.YELLOW + ChatColor.BOLD + "Content Chest(.*)")) return true;

        return false;

    }

    public boolean checkBlock(Player player, Block block) {

        if (player.hasPermission("chest.bypass")) return true;

        RegionManager regionManager = ChestInit.getWorldGuard().getRegionManager(block.getWorld());
        ApplicableRegionSet applicableRegionSet = regionManager.getApplicableRegions(block.getLocation());

        if (applicableRegionSet.getRegions().isEmpty()) return false;
        for (ProtectedRegion r : applicableRegionSet.getRegions())
            if (!r.isOwner(ChestInit.getWorldGuard().wrapPlayer(player))) return false;

        return true;

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

        ChestInit.getPlugin().getServer().getScheduler().runTaskLater(ChestInit.getPlugin(),
                () -> ChestInit.getPlugin().getServer().dispatchCommand(player, type), 1);

    }

    public enum validCommands {
        HELP, TOGGLE, RANDOM, RESET, VIEW, UPGRADE
    }

    public enum ValidChest {
        HOLD("hold"), VAULT("vault"), XP("xp"), MONEY("money");

        private final String type;

        ValidChest(String getType) {
            type = getType;
        }

        public String toString() {
            return type;
        }
    }

}
