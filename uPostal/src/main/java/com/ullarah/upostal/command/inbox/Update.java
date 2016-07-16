package com.ullarah.upostal.command.inbox;

import com.ullarah.upostal.PostalInit;
import com.ullarah.upostal.function.CommonString;
import com.ullarah.upostal.function.TitleSubtitle;
import com.ullarah.upostal.task.PostalReminder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Update {

    public void run(UUID sender, UUID receiver, Inventory inventory) {

        CommonString commonString = new CommonString();
        TitleSubtitle titleSubtitle = new TitleSubtitle();
        Player player = Bukkit.getPlayer(sender);

        File inboxFile = new File(PostalInit.getInboxDataPath(), receiver.toString() + ".yml");
        FileConfiguration inboxConfig = YamlConfiguration.loadConfiguration(inboxFile);

        try {

            if (!sender.equals(receiver) && !PostalInit.inboxModification.contains(receiver)) {

                boolean newItems = false;

                String fromFormat = "" + ChatColor.GRAY + ChatColor.ITALIC + "From: ";
                String fromPlayer = fromFormat + player.getPlayerListName();

                ArrayList<ItemStack> itemList = new ArrayList<>();

                if (!inboxConfig.getList("item").isEmpty())
                    itemList.addAll(inboxConfig.getList("item").stream().map(item
                            -> (ItemStack) item).collect(Collectors.toList()));

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

                            List<String> itemLore = item.getItemMeta().hasLore() ?
                                    item.getItemMeta().getLore() : new ArrayList<>();

                            itemLore.add(fromPlayer);

                            ItemMeta itemMeta = item.getItemMeta();
                            itemMeta.setLore(itemLore);
                            item.setItemMeta(itemMeta);

                        } else {

                            ItemMeta itemMeta = item.getItemMeta();
                            itemMeta.setLore(Collections.singletonList(fromPlayer));
                            item.setItemMeta(itemMeta);

                        }

                        itemList.add(item);

                    }

                }

                inboxConfig.set("item", itemList);
                inboxConfig.save(inboxFile);

                if (PostalInit.inboxOwnerBusy.isEmpty()) if (newItems)
                    commonString.messageSend(PostalInit.getPlugin(), player, ChatColor.GREEN + "Items sent successfully!");

                if (newItems) {

                    for (Player receiverPlayer : Bukkit.getServer().getOnlinePlayers()) {

                        if (receiverPlayer.getUniqueId().equals(receiver)) {

                            PostalInit.inboxChanged.put(receiver, new PostalReminder().task(receiver));
                            String message = ChatColor.YELLOW + "You have new items in your inbox!";
                            commonString.messageSend(PostalInit.getPlugin(), receiverPlayer, message);
                            titleSubtitle.subtitle(receiverPlayer, message);
                            break;

                        }

                    }

                }

            } else {

                List<ItemStack> itemList = new ArrayList<>();
                for (ItemStack item : inventory.getContents()) if (item != null) itemList.add(item);

                inboxConfig.set("item", itemList);
                inboxConfig.save(inboxFile);
            }

        } catch (IOException e) {

            commonString.messageSend(PostalInit.getPlugin(), player, ChatColor.RED + "Inbox Update Error!");

        }

    }

}
