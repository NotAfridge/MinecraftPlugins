package com.ullarah.uchest;

import com.ullarah.uchest.function.CommonString;
import com.ullarah.uchest.function.Experience;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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

import static com.ullarah.uchest.ChestFunctions.validConvert.MONEY;
import static com.ullarah.uchest.ChestFunctions.validConvert.XP;
import static com.ullarah.uchest.ChestFunctions.validStorage.VAULT;
import static com.ullarah.uchest.ChestInit.*;
import static java.util.concurrent.TimeUnit.SECONDS;

public class ChestFunctions {

    private final CommonString commonString = new CommonString();

    public void convertItem(Player player, validConvert type, ItemStack[] items) {

        DecimalFormat decimalFormat = new DecimalFormat(".##");

        double amount = 0.0;
        boolean hasItems = false;

        for (ItemStack item : items) {

            if (item != null) {

                double itemValue = 0;

                for (Map.Entry<ItemStack, Object[]> m : materialMap.entrySet()) {

                    List<ItemStack> itemMap = new ArrayList<>();

                    if (item.equals(m.getKey())) itemMap.add(m.getKey());

                    for (ItemStack i : itemMap)
                        if (item.getDurability() == i.getDurability())
                            itemValue = type == MONEY ? (double) m.getValue()[2] : (double) m.getValue()[3];

                }

                double maxDurability = item.getType().getMaxDurability();
                double durability = item.getDurability();

                if (item.getType().getMaxDurability() > 0)
                    itemValue -= (itemValue * (durability / maxDurability));

                amount += itemValue * item.getAmount();

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

    public void enchantItem(Player player, ItemStack item) {

        boolean useUnsafe = getPlugin().getConfig().getBoolean("echest.unsafe");

        if (item == null) return;

        if (item.getAmount() > 1) {
            commonString.messageSend(getPlugin(), player, "You are only allowed to enchant a single item.");
            player.getWorld().dropItemNaturally(player.getLocation(), item);
            return;
        }

        ItemMeta itemMeta = item.getItemMeta();

        if (itemMeta.hasEnchants()) {
            commonString.messageSend(getPlugin(), player, "This item already has an enchantment.");
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

    public void openConvertChest(CommandSender sender, validConvert type) {

        final Player player = (Player) sender;
        int playerLevel = player.getLevel();
        String chestType = type == MONEY ? "mchest" : "xchest";

        int lockTimer = getPlugin().getConfig().getInt(chestType + ".lockout");
        int accessLevel = getPlugin().getConfig().getInt(chestType + ".access");
        boolean removeLevel = getPlugin().getConfig().getBoolean(chestType + ".removelevel");

        if (player.getLevel() < accessLevel) {
            String s = accessLevel > 1 ? "s" : "";
            commonString.messageSend(getPlugin(), player, "You need more than " + accessLevel + " level" + s + " to open this chest.");
            return;
        }

        Inventory chestExpInventory = Bukkit.createInventory(player, 27, ChatColor.DARK_GREEN + (type == MONEY ? "Money" : "Experience") + " Chest");

        if (player.hasPermission("chest.bypass")) {
            player.openInventory(chestExpInventory);
            return;
        }

        if (!chestLockoutMap.get(chestType).containsKey(player.getUniqueId())) {
            if (removeLevel) player.setLevel(playerLevel - accessLevel);
            player.openInventory(chestExpInventory);
        }

        if (lockTimer > 0) chestLockout(player, lockTimer, chestType);

    }

    public void openEnchantmentChest(CommandSender sender) {

        final Player player = (Player) sender;
        int playerLevel = player.getLevel();
        String chestType = "echest";

        int lockTimer = getPlugin().getConfig().getInt(chestType + ".lockout");
        int accessLevel = getPlugin().getConfig().getInt(chestType + ".access");
        boolean removeLevel = getPlugin().getConfig().getBoolean(chestType + ".removelevel");

        if (player.getLevel() < accessLevel) {
            String s = accessLevel > 1 ? "s" : "";
            commonString.messageSend(getPlugin(), player, "You need more than " + accessLevel + " level" + s + " to open this chest.");
            return;
        }

        Inventory chestEnchantInventory = Bukkit.createInventory(player, 9, ChatColor.DARK_GREEN + "Enchantment Chest");

        ItemStack blockedItem = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 8);
        ItemMeta blockedItemMeta = blockedItem.getItemMeta();

        blockedItemMeta.setDisplayName(ChatColor.DARK_GRAY + "Use middle slot!");
        blockedItem.setItemMeta(blockedItemMeta);

        int[] blockedSlots = {0, 1, 2, 3, 5, 6, 7, 8};
        for (int b : blockedSlots) chestEnchantInventory.setItem(b, blockedItem);

        if (player.hasPermission("chest.bypass")) {
            player.openInventory(chestEnchantInventory);
            return;
        }

        if (!chestLockoutMap.get(chestType).containsKey(player.getUniqueId())) {
            if (removeLevel) player.setLevel(playerLevel - accessLevel);
            player.openInventory(chestEnchantInventory);
        }

        if (lockTimer > 0) chestLockout(player, lockTimer, chestType);

    }

    public void openRandomChest(CommandSender sender) {

        final Player player = (Player) sender;
        int playerLevel = player.getLevel();
        String chestType = "rchest";
        int accessLevel = getPlugin().getConfig().getInt(chestType + ".access");
        int lockTimer = getPlugin().getConfig().getInt(chestType + ".lockout");
        boolean removeLevel = getPlugin().getConfig().getBoolean(chestType + ".removelevel");

        if (playerLevel < accessLevel) {
            String s = accessLevel > 1 ? "s" : "";
            commonString.messageSend(getPlugin(), player, "You need more than " + accessLevel + " level" + s + " to open this chest.");
            return;
        }

        if (player.hasPermission("chest.bypass")) player.openInventory(getChestRandomHolder().getInventory());
        else {

            if (!chestLockoutMap.get(chestType).containsKey(player.getUniqueId())) {
                if (removeLevel) player.setLevel(playerLevel - accessLevel);
                player.openInventory(getChestRandomHolder().getInventory());
            }

            if (lockTimer > 0) chestLockout(player, lockTimer, chestType);

        }

        BukkitTask task = new BukkitRunnable() {

            int c = getPlugin().getConfig().getInt(chestType + ".timer");

            @Override
            public void run() {

                if (c <= 0) {

                    chestRandomTask.remove(player.getUniqueId());
                    player.closeInventory();
                    this.cancel();
                    return;

                }

                getChestRandomInventory().clear();

                List<ItemStack> materialKeys = new ArrayList<>(materialMap.keySet());
                ItemStack itemStack = materialKeys.get(new Random().nextInt(materialKeys.size()));

                if ((boolean) materialMap.get(itemStack)[1]) getChestRandomInventory().setItem(new Random().nextInt(54),
                        materialKeys.get(new Random().nextInt(materialKeys.size())));

                c--;

            }

        }.runTaskTimer(getPlugin(), 5, 25);

        chestRandomTask.put(player.getUniqueId(), task);

    }

    public void chestLockout(Player player, Integer lockTimer, String lockChest) {

        ConcurrentHashMap<UUID, Integer> timerMap = chestLockoutMap.get(lockChest);

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
                    chestLockoutMap.replace(lockChest, timerMap);

                }

            }.runTaskTimer(getPlugin(), 20, 20);

        }

        int getCurrentLockTime = timerMap.get(player.getUniqueId());

        String minuteString = " Minute";
        String secondString = " Second";

        long minute = SECONDS.toMinutes(getCurrentLockTime) - (SECONDS.toHours(getCurrentLockTime) * 60);
        long second = SECONDS.toSeconds(getCurrentLockTime) - (SECONDS.toMinutes(getCurrentLockTime) * 60);

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

            String chestType = type == VAULT ? "vchest" : "hchest";

            int lockTimer = getPlugin().getConfig().getInt(chestType + ".lockout");
            int accessLevel = getPlugin().getConfig().getInt(chestType + ".access");
            boolean removeLevel = getPlugin().getConfig().getBoolean(chestType + ".removelevel");

            if (player.getLevel() < accessLevel) {
                String s = accessLevel > 1 ? "s" : "";
                commonString.messageSend(getPlugin(), player, "You need more than " + accessLevel + " level" + s + " to open this chest.");
                return;
            }

            if (player.hasPermission("chest.bypass")) {
                player.openInventory(inventory);
                return;
            }

            if (!chestLockoutMap.get(chestType).containsKey(player.getUniqueId())) {
                if (removeLevel) player.setLevel(playerLevel - accessLevel);
                player.openInventory(inventory);
            }

            if (lockTimer > 0) chestLockout(player, lockTimer, chestType);

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
