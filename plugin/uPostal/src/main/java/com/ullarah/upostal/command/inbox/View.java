package com.ullarah.upostal.command.inbox;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

import static com.ullarah.upostal.PostalInit.*;

public class View {

    public static void run(ArrayList inboxPlayerStock, Player inboxViewer, UUID inboxUUID,
                           String inboxPlayerName, Integer inboxSlot) {

        Inventory inboxInventory = Bukkit.createInventory(null, inboxSlot,
                ChatColor.DARK_RED + "Inbox: " + ChatColor.DARK_AQUA + inboxPlayerName);

        if (inboxOwnerBusy.contains(inboxUUID) || inboxViewerBusy.contains(inboxUUID)) {

            inboxViewer.sendMessage(getMsgPrefix() + "This inbox is currently busy. Try again later.");

        } else {

            if (inboxUUID.equals(inboxViewer.getUniqueId())) {

                inboxOwnerBusy.add(inboxUUID);

            } else {

                inboxViewerBusy.add(inboxUUID);

            }

            if (!inboxPlayerStock.isEmpty()) {

                ArrayList<ItemStack> inboxItemStack = new ArrayList<>();

                for (Object inboxCurrentItem : inboxPlayerStock) {

                    ItemStack inboxTaken = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
                    ItemMeta inboxTakenMeta = inboxTaken.getItemMeta();

                    inboxTakenMeta.setDisplayName(ChatColor.WHITE + "Slot Taken");
                    inboxTaken.setItemMeta(inboxTakenMeta);

                    if (inboxUUID.equals(inboxViewer.getUniqueId())) {

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

}
