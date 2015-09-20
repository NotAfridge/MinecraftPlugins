package com.ullarah.upostal;

import com.ullarah.ulib.function.ProfileUtils;
import com.ullarah.upostal.command.Blacklist;
import com.ullarah.upostal.command.Maintenance;
import com.ullarah.upostal.command.inbox.Clear;
import com.ullarah.upostal.command.inbox.Prepare;
import com.ullarah.upostal.command.inbox.Upgrade;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import static com.ullarah.upostal.PostalInit.*;
import static com.ullarah.upostal.command.Help.display;

class PostalExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        switch (command.getName().toUpperCase()) {

            case "POSTAL":
                postalCommands(sender, args);
                break;

            case "POST":
                if (!getMaintenanceCheck())
                    if (args.length >= 1) {

                        UUID playerID = ProfileUtils.lookup(args[0]).getId();

                        if (playerID != null) Prepare.run((Player) sender, playerID);
                        else sender.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "That player does not have an inbox.");

                    } else sender.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "Usage: /post <player>");
                break;

            case "INBOX":
                if (!getMaintenanceCheck()) {

                    if (args.length >= 1) {
                        if (args[0].toUpperCase().equals("UPGRADE")) Upgrade.run(sender);
                    } else Prepare.run((Player) sender, ((Player) sender).getUniqueId());

                }
                break;

        }

        return true;

    }

    private void postalCommands(CommandSender sender, String[] args) {

        String consoleTools = getMsgPrefix() + ChatColor.WHITE + "clear | maintenance";

        if (args.length == 0) if (!(sender instanceof Player))
            sender.sendMessage(consoleTools);
        else
            display(sender);

        else try {

            switch (args[0].toUpperCase()) {

                case "HELP":
                    if (!(sender instanceof Player))
                        sender.sendMessage(getMsgNoConsole());
                    else if (!getMaintenanceCheck())
                        display(sender);
                    break;

                case "MAINTENANCE":
                    Maintenance.toggle(sender, args);
                    break;

                case "CLEAR":
                    if (!getMaintenanceCheck())
                        Clear.run(sender, args);
                    else
                        sender.sendMessage(getMaintenanceMessage());
                    break;

                case "BLACKLIST":
                    Blacklist.toggle(sender, args);
                    break;

                default:
                    if (!(sender instanceof Player))
                        sender.sendMessage(getMsgNoConsole());
                    else if (!getMaintenanceCheck())
                        display(sender);
                    break;

            }

        } catch (IllegalArgumentException e) {

            if (!(sender instanceof Player))
                sender.sendMessage(consoleTools);

        }

    }

}
