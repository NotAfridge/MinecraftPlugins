package com.ullarah.upostal.event;

import com.ullarah.ulib.function.ProfileUtils;
import com.ullarah.upostal.PostalInit;
import com.ullarah.upostal.command.Inbox;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.util.UUID;

import static com.ullarah.upostal.PostalInit.getInboxDataPath;
import static org.bukkit.ChatColor.stripColor;

public class InboxClose implements Listener {

    @EventHandler
    public void event(final InventoryCloseEvent event) {

        Inventory chestInventory = event.getInventory();

        if (chestInventory.getName().matches("§4Inbox: §3(.*)")) {

            String inboxOwner = stripColor(event.getInventory().getTitle().replace("Inbox: ", ""));
            UUID inboxUUID = ProfileUtils.lookup(inboxOwner).getId();

            File inboxFile = new File(getInboxDataPath(), inboxUUID.toString() + ".yml");
            FileConfiguration inboxConfig = YamlConfiguration.loadConfiguration(inboxFile);

            UUID inboxViewerUUID = event.getInventory().getViewers().get(0).getUniqueId();
            UUID inboxOwnerUUID = UUID.fromString(inboxConfig.getString("uuid"));

            Inbox.update(inboxViewerUUID, inboxOwnerUUID, chestInventory);

            if (PostalInit.inboxViewerBusy.contains(inboxOwnerUUID))
                PostalInit.inboxViewerBusy.remove(inboxOwnerUUID);

            if (PostalInit.inboxOwnerBusy.contains(inboxOwnerUUID))
                PostalInit.inboxOwnerBusy.remove(inboxOwnerUUID);

        }

    }

}
