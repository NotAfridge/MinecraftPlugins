package com.ullarah.upostal.command.inbox;

import com.ullarah.upostal.PostalInit;
import com.ullarah.upostal.function.CommonString;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

class View {

    void run(ArrayList inboxPlayerStock, Player inboxViewer, UUID inboxUUID,
             String inboxPlayerName, Integer inboxSlot) {

        CommonString commonString = new CommonString();

        Inventory inboxInventory = Bukkit.createInventory(null, inboxSlot,
                ChatColor.DARK_RED + "Inbox: " + ChatColor.DARK_AQUA + inboxPlayerName);

        if (PostalInit.inboxOwnerBusy.contains(inboxUUID) || PostalInit.inboxViewerBusy.contains(inboxUUID)) {
            commonString.messageSend(PostalInit.getPlugin(), inboxViewer, "This inbox is currently busy. Try again later.");
            return;
        }

        if (inboxUUID.equals(inboxViewer.getUniqueId())) {
            PostalInit.inboxChanged.remove(inboxUUID);
            PostalInit.inboxOwnerBusy.add(inboxUUID);
        } else PostalInit.inboxViewerBusy.add(inboxUUID);

        if (!inboxPlayerStock.isEmpty()) {

            ArrayList<ItemStack> inboxItemStack = new ArrayList<>();

            for (Object inboxCurrentItem : inboxPlayerStock) {

                ItemStack inboxTaken = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
                ItemMeta inboxTakenMeta = inboxTaken.getItemMeta();

                inboxTakenMeta.setDisplayName(ChatColor.WHITE + "Slot Taken");
                inboxTaken.setItemMeta(inboxTakenMeta);

                if (inboxUUID.equals(inboxViewer.getUniqueId()) || PostalInit.inboxModification.contains(inboxUUID))
                    inboxItemStack.add((ItemStack) inboxCurrentItem);
                else inboxItemStack.add(inboxTaken);

            }

            inboxInventory.setContents(inboxItemStack.toArray(new ItemStack[0]));

        }

        inboxViewer.openInventory(inboxInventory);

    }

}