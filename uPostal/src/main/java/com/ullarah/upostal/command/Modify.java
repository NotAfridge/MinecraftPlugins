package com.ullarah.upostal.command;

import com.ullarah.upostal.PostalInit;
import com.ullarah.upostal.command.inbox.Prepare;
import com.ullarah.upostal.function.CommonString;
import com.ullarah.upostal.function.PlayerProfile;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;

public class Modify {

    public void inbox(CommandSender sender, String[] args) {

        CommonString commonString = new CommonString();

        if (!sender.hasPermission("postal.modify")) {
            commonString.messagePermDeny(PostalInit.getPlugin(), sender);
            return;
        }

        if (args.length >= 2) {

            UUID inboxUUID = new PlayerProfile().lookup(args[1]).getId();
            File inboxFile = new File(PostalInit.getInboxDataPath(), inboxUUID.toString() + ".yml");

            if (inboxFile.exists()) {
                PostalInit.inboxModification.add(inboxUUID);
                new Prepare().run((Player) sender, inboxUUID);
            } else
                commonString.messageSend(PostalInit.getPlugin(), (Player) sender, "That player does not have an inbox!");

        } else
            commonString.messageSend(PostalInit.getPlugin(), (Player) sender, ChatColor.YELLOW + "/postal modify <player>");

    }

}
