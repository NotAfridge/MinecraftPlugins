package com.ullarah.upostal.command.inbox;

import com.ullarah.upostal.function.CommonString;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import static com.ullarah.upostal.PostalInit.*;

public class Prepare {

    public void run(Player player, UUID inbox) {

        CommonString commonString = new CommonString();
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

                if (inboxBlacklist) {
                    commonString.messageSend(getPlugin(), player, ChatColor.RED + "Your inbox has been blacklisted.");
                    return;
                }

                if (inboxPlayerStock.isEmpty()) {
                    commonString.messageSend(getPlugin(), player, "You have no items in your inbox!");
                    return;
                }

            } else {

                if (inboxBlacklist) {
                    commonString.messageSend(getPlugin(), player, ChatColor.RED + "Their inbox has been blacklisted.");
                    return;
                }

            }

            new View().run(inboxPlayerStock, player, inboxPlayerUUID, inboxPlayerName, inboxPlayerSlot);

        } else commonString.messageSend(getPlugin(), player, "That player does not have an inbox!");

    }

}
