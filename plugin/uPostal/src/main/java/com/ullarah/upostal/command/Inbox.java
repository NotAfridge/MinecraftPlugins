package com.ullarah.upostal.command;

import com.ullarah.ulib.function.ProfileUtils;
import com.ullarah.ulib.function.TitleSubtitle;
import com.ullarah.upostal.task.PostalReminder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.ullarah.upostal.PostalInit.*;

public class Inbox {

    public static void prepare(Player player, UUID inbox) {

        if (!getMaintenanceCheck()) {

            File inboxFile = new File(getInboxDataPath(), inbox.toString() + ".yml");

            if (inboxFile.exists()) {

                FileConfiguration inboxConfig = YamlConfiguration.loadConfiguration(inboxFile);

                String inboxPlayerName = inboxConfig.getString("name");
                UUID inboxPlayerUUID = UUID.fromString(inboxConfig.getString("uuid"));
                Integer inboxPlayerSlot = inboxConfig.getInt("slot");
                ArrayList inboxPlayerStock = (ArrayList) inboxConfig.getList("item");
                Boolean inboxBlacklist = inboxConfig.getBoolean("blacklist");

                if (player.getUniqueId().equals(inboxPlayerUUID)) {

                    if (inboxChanged.containsKey(inboxPlayerUUID)) {
                        inboxChanged.get(inboxPlayerUUID).cancel();
                        inboxChanged.remove(inboxPlayerUUID);
                    }

                    if (inboxBlacklist)
                        player.sendMessage(getMsgPrefix() + ChatColor.RED + "Your inbox has been blacklisted.");
                    else if (inboxPlayerStock.isEmpty())
                        player.sendMessage(getMsgPrefix() + "You have no items in your inbox!");
                    else view(inboxConfig, inboxPlayerName, player, inboxPlayerUUID, inboxPlayerSlot);

                } else {

                    if (inboxBlacklist)
                        player.sendMessage(getMsgPrefix() + ChatColor.RED + "Their inbox has been blacklisted.");
                    else view(inboxConfig, inboxPlayerName, player, inboxPlayerUUID, inboxPlayerSlot);

                }

            } else {

                player.sendMessage(getMsgPrefix() + "That player does not have an inbox!");

            }

        } else player.sendMessage(getMaintenanceMessage());

    }

    private static void view(FileConfiguration inboxPlayer, String inboxPlayerName, Player inboxViewer,
                             UUID inboxPlayerUUID, Integer inboxPlayerSlot) {

        Inventory inboxInventory = Bukkit.createInventory(null, inboxPlayerSlot,
                ChatColor.DARK_RED + "Inbox: " + ChatColor.DARK_AQUA + inboxPlayerName);

        if (inboxOwnerBusy.contains(inboxPlayerUUID) || inboxViewerBusy.contains(inboxPlayerUUID)) {

            inboxViewer.sendMessage(getMsgPrefix() + "This inbox is currently busy. Try again later.");

        } else {

            if (inboxPlayerName.equals(inboxViewer.getPlayerListName())) {

                inboxOwnerBusy.add(inboxPlayerUUID);

            } else {

                inboxViewerBusy.add(inboxPlayerUUID);

            }

            if (inboxPlayer.get("item") != null) {

                ArrayList<ItemStack> inboxItemStack = new ArrayList<>();

                for (Object inboxCurrentItem : inboxPlayer.getList("item")) {

                    ItemStack inboxTaken = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
                    ItemMeta inboxTakenMeta = inboxTaken.getItemMeta();

                    inboxTakenMeta.setDisplayName(ChatColor.WHITE + "Slot Taken");
                    inboxTaken.setItemMeta(inboxTakenMeta);

                    if (inboxPlayerName.equals(inboxViewer.getPlayerListName())) {

                        inboxItemStack.add((ItemStack) inboxCurrentItem);

                    } else {

                        inboxItemStack.add(inboxTaken);

                    }

                }

                inboxInventory.setContents(inboxItemStack.toArray(new ItemStack[inboxItemStack.size()]));

            }

            inboxViewer.openInventory(inboxInventory);

        }

    }

    public static void update(UUID sender, UUID receiver, Inventory inventory) {

        Player player = Bukkit.getPlayer(sender);

        if (!getMaintenanceCheck()) {

            boolean newItems = false;
            String fromString = "" + ChatColor.GRAY + ChatColor.ITALIC + "From: " + player.getPlayerListName();

            File inboxFile = new File(getInboxDataPath(), receiver.toString() + ".yml");
            FileConfiguration inboxConfig = YamlConfiguration.loadConfiguration(inboxFile);

            if (!sender.equals(receiver)) {

                try {

                    ArrayList<ItemStack> itemList = new ArrayList<>();

                    if (!inboxConfig.getList("item").isEmpty())
                        for (Object item : inboxConfig.getList("item"))
                            itemList.add((ItemStack) item);

                    for (ItemStack item : inventory.getContents()) {

                        if (item != null) {

                            newItems = true;

                            if (item.hasItemMeta()) {

                                if (item.getItemMeta().hasDisplayName()) {

                                    if (item.getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Slot Taken")
                                            && item.getType() == Material.STAINED_GLASS_PANE) {

                                        newItems = false;
                                        continue;

                                    }

                                }

                                List<String> itemLore = new ArrayList<>();

                                if (item.getItemMeta().hasLore()) {
                                    itemLore = item.getItemMeta().getLore();
                                    itemLore.add(fromString);
                                } else itemLore.add(fromString);

                                ItemMeta itemMeta = item.getItemMeta();
                                itemMeta.setLore(itemLore);
                                item.setItemMeta(itemMeta);

                            } else {

                                ItemMeta itemMeta = item.getItemMeta();
                                itemMeta.setLore(Arrays.asList(fromString));
                                item.setItemMeta(itemMeta);

                            }

                            itemList.add(item);

                        }

                    }

                    inboxConfig.set("item", itemList);
                    inboxConfig.save(inboxFile);

                } catch (IOException e) {

                    player.sendMessage(getMsgPrefix() + ChatColor.RED + "Inbox Update Error!");

                }

                if (inboxOwnerBusy.isEmpty()) if (newItems) player.sendMessage(
                        getMsgPrefix() + ChatColor.GREEN + "Items sent successfully!");

                if (newItems) {

                    for (Player receiverPlayer : Bukkit.getServer().getOnlinePlayers()) {

                        if (receiverPlayer.getUniqueId().equals(receiver)) {

                            inboxChanged.put(receiver, PostalReminder.task(receiver));
                            String message = ChatColor.YELLOW + "You have new items in your inbox!";
                            receiverPlayer.sendMessage(getMsgPrefix() + message);
                            TitleSubtitle.subtitle(receiverPlayer, 5, message);
                            break;

                        }

                    }

                }

            } else try {

                List<ItemStack> itemList = new ArrayList<>();
                for (ItemStack item : inventory.getContents()) if (item != null) itemList.add(item);

                inboxConfig.set("item", itemList);
                inboxConfig.save(inboxFile);

            } catch (IOException e) {

                player.sendMessage(getMsgPrefix() + ChatColor.RED + "Inbox Update Error!");

            }

        } else player.sendMessage(getMaintenanceMessage());

    }

    public static void clear(CommandSender sender, String[] args) {

        if (!getMaintenanceCheck()) {

            if (sender.hasPermission("postal.clear")) {

                if (!getMaintenanceCheck()) if (args.length >= 2) {

                    File inboxFile = new File(getInboxDataPath(), ProfileUtils.lookup(args[1]).getId().toString() + ".yml");

                    if (inboxFile.exists()) {

                        try {

                            FileConfiguration inboxConfig = YamlConfiguration.loadConfiguration(inboxFile);
                            inboxConfig.set("item", new ArrayList<>());
                            inboxConfig.save(inboxFile);

                            sender.sendMessage(getMsgPrefix() + ChatColor.GREEN + "Inbox cleared successfully!");

                        } catch (IOException e) {

                            sender.sendMessage(getMsgPrefix() + ChatColor.RED + "Inbox Clear Error!");
                            e.printStackTrace();

                        }

                    } else sender.sendMessage(getMsgPrefix() + "That player does not have an inbox!");

                } else sender.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "/postal clear <player>");

                else sender.sendMessage(getMaintenanceMessage());

            } else sender.sendMessage(getMsgPermDeny());

        } else sender.sendMessage(getMaintenanceMessage());

    }

    public static void upgrade(CommandSender sender) {

        if (!getMaintenanceCheck()) {

            Player player = (Player) sender;

            File inboxFile = new File(getInboxDataPath(), player.getUniqueId().toString() + ".yml");

            if (inboxFile.exists()) {

                FileConfiguration inboxConfig = YamlConfiguration.loadConfiguration(inboxFile);

                int inboxPlayerSlot = inboxConfig.getInt("slot");

                if (inboxPlayerSlot >= 54) {

                    player.sendMessage(getMsgPrefix() + ChatColor.AQUA + "You have the maximum number of slots!");

                } else {

                    if (player.getLevel() >= 50) {

                        inboxConfig.set("slot", inboxPlayerSlot + 9);
                        player.setLevel(player.getLevel() - 50);

                        try {
                            inboxConfig.save(inboxFile);
                        } catch (IOException e) {
                            player.sendMessage(getMsgPrefix() + ChatColor.RED + "Slot Update Error!");
                        }

                        player.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "You upgraded your inbox! You now have " +
                                ChatColor.GREEN + (inboxPlayerSlot + 9) + ChatColor.YELLOW + " slots!");

                    } else {

                        player.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "You need at least" +
                                ChatColor.GOLD + " 50xp levels " + ChatColor.YELLOW + "to upgrade!");

                    }

                }

            }

        } else sender.sendMessage(getMaintenanceMessage());

    }

}
