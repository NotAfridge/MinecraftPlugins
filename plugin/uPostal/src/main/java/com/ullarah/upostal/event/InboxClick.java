package com.ullarah.upostal.event;

import com.ullarah.ulib.function.ProfileUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.List;
import java.util.UUID;

import static com.ullarah.upostal.PostalInit.getInboxDataPath;
import static org.bukkit.ChatColor.stripColor;

public class InboxClick implements Listener {

    @EventHandler
    public void event(final InventoryClickEvent event) {

        if (event.getInventory().getName().matches("§4Inbox: §3(.*)")) {

            String inboxOwner = stripColor(event.getInventory().getTitle().replace("Inbox: ", ""));
            UUID inboxUUID = ProfileUtils.lookup(inboxOwner).getId();

            File inboxFile = new File(getInboxDataPath(), inboxUUID.toString() + ".yml");
            FileConfiguration inboxConfig = YamlConfiguration.loadConfiguration(inboxFile);

            UUID inboxViewerUUID = event.getInventory().getViewers().get(0).getUniqueId();
            UUID inboxOwnerUUID = UUID.fromString(inboxConfig.getString("uuid"));
            int inboxSlotSizeRaw = inboxConfig.getInt("slot") - 1;

            boolean isOwner = inboxViewerUUID.equals(inboxOwnerUUID);

            if (event.getRawSlot() == -999 || event.getClick().isRightClick())
                event.setCancelled(true);
            else {

                ItemStack hand = event.getWhoClicked().getItemOnCursor();
                ItemStack item = event.getCurrentItem();

                if (event.getRawSlot() > inboxSlotSizeRaw && isOwner && hand.getType() == Material.AIR)
                    event.setCancelled(true);

                if (event.getRawSlot() <= inboxSlotSizeRaw && isOwner && hand.getType() != Material.AIR)
                    event.setCancelled(true);

                if (item.getType() != Material.AIR) {

                    if (item.hasItemMeta()) {

                        if (item.getItemMeta().hasDisplayName()) {

                            if (item.getItemMeta().getDisplayName().equals(
                                    ChatColor.WHITE + "Slot Taken")
                                    && item.getType() == Material.STAINED_GLASS_PANE)
                                event.setCancelled(true);

                        }

                        if (item.getItemMeta().hasLore()) {

                            if (event.getRawSlot() < inboxSlotSizeRaw && isOwner) {

                                ItemMeta meta = item.getItemMeta();
                                List<String> lore = item.getItemMeta().getLore();

                                if (lore.size() <= 1) meta.setLore(null);
                                else {

                                    for (String line : item.getItemMeta().getLore()) {
                                        if (line.matches("" + ChatColor.GRAY + ChatColor.ITALIC + "From: .*")) {
                                            lore.remove(lore.size() - 1);
                                        }
                                    }

                                    meta.setLore(lore);

                                }

                                item.setItemMeta(meta);

                            }

                        }

                    }

                }

            }

        }

    }

}
