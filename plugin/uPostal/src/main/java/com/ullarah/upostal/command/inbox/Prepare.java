package com.ullarah.upostal.command.inbox;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import static com.ullarah.upostal.PostalInit.*;

public class Prepare {

    public static void run(Player player, UUID inbox) {

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
                    else View.run(inboxPlayerStock, player, inboxPlayerUUID, inboxPlayerName, inboxPlayerSlot);

                } else {

                    if (inboxBlacklist)
                        player.sendMessage(getMsgPrefix() + ChatColor.RED + "Their inbox has been blacklisted.");
                    else View.run(inboxPlayerStock, player, inboxPlayerUUID, inboxPlayerName, inboxPlayerSlot);

                }

            } else {

                player.sendMessage(getMsgPrefix() + "That player does not have an inbox!");

            }

        } else player.sendMessage(getMaintenanceMessage());

    }

}
