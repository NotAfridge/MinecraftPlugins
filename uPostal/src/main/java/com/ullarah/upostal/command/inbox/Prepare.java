package com.ullarah.upostal.command.inbox;

import com.ullarah.upostal.PostalInit;
import com.ullarah.upostal.function.CommonString;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class Prepare {

    public void run(Player player, UUID inbox) {

        CommonString commonString = new CommonString();
        File inboxFile = new File(PostalInit.getInboxDataPath(), inbox.toString() + ".yml");

        if (inboxFile.exists()) {

            FileConfiguration inboxConfig = YamlConfiguration.loadConfiguration(inboxFile);

            String inboxPlayerName = inboxConfig.getString("name");
            UUID inboxPlayerUUID = UUID.fromString(inboxConfig.getString("uuid"));
            Integer inboxPlayerSlot = inboxConfig.getInt("slot");
            ArrayList inboxPlayerStock = (ArrayList) inboxConfig.getList("item");
            Boolean inboxBlacklist = inboxConfig.getBoolean("blacklist");

            if (player.getUniqueId().equals(inboxPlayerUUID)) {

                if (PostalInit.inboxChanged.containsKey(inboxPlayerUUID)) {
                    PostalInit.inboxChanged.get(inboxPlayerUUID).cancel();
                    PostalInit.inboxChanged.remove(inboxPlayerUUID);
                }

                if (inboxBlacklist) {
                    commonString.messageSend(PostalInit.getPlugin(), player, ChatColor.RED + "Your inbox has been blacklisted.");
                    return;
                }

                if (inboxPlayerStock.isEmpty()) {
                    commonString.messageSend(PostalInit.getPlugin(), player, "You have no items in your inbox!");
                    return;
                }

            } else {

                if (inboxBlacklist) {
                    commonString.messageSend(PostalInit.getPlugin(), player, ChatColor.RED + "Their inbox has been blacklisted.");
                    return;
                }

            }

            new View().run(inboxPlayerStock, player, inboxPlayerUUID, inboxPlayerName, inboxPlayerSlot);

        } else commonString.messageSend(PostalInit.getPlugin(), player, "That player does not have an inbox!");

    }

}
