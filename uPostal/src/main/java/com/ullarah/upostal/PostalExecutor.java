package com.ullarah.upostal;

import com.ullarah.upostal.command.Blacklist;
import com.ullarah.upostal.command.Help;
import com.ullarah.upostal.command.inbox.Clear;
import com.ullarah.upostal.command.inbox.Prepare;
import com.ullarah.upostal.command.inbox.Upgrade;
import com.ullarah.upostal.function.CommonString;
import com.ullarah.upostal.function.PlayerProfile;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import static com.ullarah.upostal.PostalInit.getPlugin;

class PostalExecutor implements CommandExecutor {

    private final CommonString commonString = new CommonString();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        Prepare prepare = new Prepare();
        Upgrade upgrade = new Upgrade();

        switch (command.getName().toUpperCase()) {

            case "POSTAL":
                postalCommands(sender, args);
                break;

            case "POST":
                if (sender instanceof Player) {
                    if (args.length >= 1) {
                        if (args[0].matches("[\\w\\d_]{1,16}")) {

                            try {
                                UUID playerID = new PlayerProfile().lookup(args[0]).getId();
                                if (playerID != null) prepare.run((Player) sender, playerID);
                            } catch (Exception e) {
                                commonString.messageSend(getPlugin(), sender, ChatColor.YELLOW + "That player does not have an inbox!");
                            }

                        } else
                            commonString.messageSend(getPlugin(), sender, ChatColor.RED + "Not a valid player name!");
                    } else
                        commonString.messageSend(getPlugin(), sender, ChatColor.YELLOW + "Usage: /post <player>");
                } else commonString.messageNoConsole(getPlugin(), sender);
                break;

            case "INBOX":
                if (sender instanceof Player) {

                    if (args.length >= 1) {
                        if (args[0].toUpperCase().equals("UPGRADE")) upgrade.run(sender);
                    } else prepare.run((Player) sender, ((Player) sender).getUniqueId());

                } else commonString.messageNoConsole(getPlugin(), sender);
                break;

        }

        return true;

    }

    private void postalCommands(CommandSender sender, String[] args) {

        Help help = new Help();
        Blacklist blacklist = new Blacklist();
        Clear clear = new Clear();

        String consoleTools = new CommonString().pluginPrefix(getPlugin()) + "blacklist | clear";

        if (args.length == 0) if (!(sender instanceof Player)) sender.sendMessage(consoleTools);
        else help.display(sender);

        else try {

            switch (args[0].toUpperCase()) {

                case "BLACKLIST":
                    blacklist.toggle(sender, args);
                    break;

                case "CLEAR":
                    clear.run(sender, args);
                    break;

                default:
                    if (!(sender instanceof Player)) commonString.messageNoConsole(getPlugin(), sender);
                    else help.display(sender);
                    break;

            }

        } catch (IllegalArgumentException e) {

            if (!(sender instanceof Player))
                sender.sendMessage(consoleTools);

        }

    }

}
