package com.ullarah.upostal.command.inbox;

import com.ullarah.upostal.PostalInit;
import com.ullarah.upostal.function.CommonString;
import com.ullarah.upostal.function.PlayerProfile;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.UUID;

public class Reset {

    public void run(CommandSender sender, String[] args) {

        CommonString commonString = new CommonString();

        if (!sender.hasPermission("postal.reset")) {
            commonString.messagePermDeny(PostalInit.getPlugin(), sender);
            return;
        }

        if (args.length >= 2) {

            File inboxFile = new File(PostalInit.getInboxDataPath(), new PlayerProfile().lookup(args[1]).getId().toString() + ".yml");

            FileConfiguration inboxConfig = YamlConfiguration.loadConfiguration(inboxFile);

            UUID inboxOwnerUUID = UUID.fromString(inboxConfig.getString("uuid"));

            if (inboxFile.exists()) {

                if (PostalInit.inboxViewerBusy.contains(inboxOwnerUUID))
                    PostalInit.inboxViewerBusy.remove(inboxOwnerUUID);

                if (PostalInit.inboxOwnerBusy.contains(inboxOwnerUUID))
                    PostalInit.inboxOwnerBusy.remove(inboxOwnerUUID);

                if (PostalInit.inboxModification.contains(inboxOwnerUUID))
                    PostalInit.inboxModification.remove(inboxOwnerUUID);

                commonString.messageSend(PostalInit.getPlugin(), sender, ChatColor.GREEN + "Inbox reset successfully!");

            } else commonString.messageSend(PostalInit.getPlugin(), sender, "That player does not have an inbox!");

        } else commonString.messageSend(PostalInit.getPlugin(), sender, ChatColor.YELLOW + "/postal reset <player>");

    }

}
