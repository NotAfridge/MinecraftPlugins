package com.ullarah.upostal;

import com.ullarah.ulib.function.CommonString;
import com.ullarah.ulib.function.PlayerProfile;
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

import static com.ullarah.upostal.PostalInit.getMaintenanceCheck;
import static com.ullarah.upostal.PostalInit.getPlugin;
import static com.ullarah.upostal.command.Help.display;

class PostalExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        switch (command.getName().toUpperCase()) {

            case "POSTAL":
                postalCommands(sender, args);
                break;

            case "POST":
                if ((sender instanceof Player)) {
                    if (!getMaintenanceCheck()) {
                        if (args.length >= 1) {
                            if (args[0].matches("[\\w\\d_]{1,16}")) {

                                try {
                                    UUID playerID = new PlayerProfile().lookup(args[0]).getId();
                                    if (playerID != null) Prepare.run((Player) sender, playerID);
                                } catch (Exception e) {
                                    new CommonString().messageSend(getPlugin(), sender, true, new String[]{
                                            ChatColor.YELLOW + "That player does not have an inbox!"
                                    });
                                }

                            } else
                                new CommonString().messageSend(getPlugin(), sender, true, new String[]{ChatColor.RED + "Not a valid player name!"});
                        } else
                            new CommonString().messageSend(getPlugin(), sender, true, new String[]{ChatColor.YELLOW + "Usage: /post <player>"});
                    } else new CommonString().messageMaintenance(getPlugin(), sender);
                } else new CommonString().messageNoConsole(getPlugin(), sender);
                break;

            case "INBOX":
                if (sender instanceof Player) {
                    if (!getMaintenanceCheck()) {

                        if (args.length >= 1) {
                            if (args[0].toUpperCase().equals("UPGRADE")) Upgrade.run(sender);
                        } else Prepare.run((Player) sender, ((Player) sender).getUniqueId());

                    } else new CommonString().messageMaintenance(getPlugin(), sender);
                } else new CommonString().messageNoConsole(getPlugin(), sender);
                break;

        }

        return true;

    }

    private void postalCommands(CommandSender sender, String[] args) {

        String consoleTools = new CommonString().pluginPrefix(getPlugin()) + "clear | maintenance";

        if (args.length == 0) if (!(sender instanceof Player))
            sender.sendMessage(consoleTools);
        else
            display(sender);

        else try {

            switch (args[0].toUpperCase()) {

                case "HELP":
                    if (!(sender instanceof Player))
                        new CommonString().messageNoConsole(getPlugin(), sender);
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
                        new CommonString().messageMaintenance(getPlugin(), sender);
                    break;

                case "BLACKLIST":
                    Blacklist.toggle(sender, args);
                    break;

                default:
                    if (!(sender instanceof Player))
                        new CommonString().messageNoConsole(getPlugin(), sender);
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
