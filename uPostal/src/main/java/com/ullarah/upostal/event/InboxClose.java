package com.ullarah.upostal.event;

import com.ullarah.upostal.command.inbox.Update;
import com.ullarah.upostal.function.PlayerProfile;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.io.File;
import java.util.UUID;

import static com.ullarah.upostal.PostalInit.*;
import static org.bukkit.ChatColor.stripColor;

public class InboxClose implements Listener {

    @EventHandler
    public void event(final InventoryCloseEvent event) {

        if (event.getInventory().getName().matches("§4Inbox: §3(.*)")) {

            String inboxOwner = stripColor(event.getInventory().getTitle().replace("Inbox: ", ""));
            UUID inboxUUID = new PlayerProfile().lookup(inboxOwner).getId();

            File inboxConfigFile = new File(getInboxDataPath(), inboxUUID.toString() + ".yml");
            FileConfiguration inboxConfig = YamlConfiguration.loadConfiguration(inboxConfigFile);

            UUID inboxViewerUUID = event.getInventory().getViewers().get(0).getUniqueId();
            UUID inboxOwnerUUID = UUID.fromString(inboxConfig.getString("uuid"));

            new Update().run(inboxViewerUUID, inboxOwnerUUID, event.getInventory());

            if (inboxViewerBusy.contains(inboxOwnerUUID))
                inboxViewerBusy.remove(inboxOwnerUUID);

            if (inboxOwnerBusy.contains(inboxOwnerUUID)) {
                if (inboxChanged.containsKey(inboxUUID)) {
                    inboxChanged.get(inboxUUID).cancel();
                    inboxChanged.remove(inboxUUID);
                }
                inboxOwnerBusy.remove(inboxOwnerUUID);
            }

        }

    }

}
