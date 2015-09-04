package com.ullarah.ujoinquit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.ujoinquit.JoinQuitFunctions.*;
import static com.ullarah.ujoinquit.JoinQuitFunctions.messageType.JOIN;
import static com.ullarah.ujoinquit.JoinQuitFunctions.messageType.QUIT;
import static com.ullarah.ujoinquit.JoinQuitInit.getMsgNoConsole;
import static com.ullarah.ujoinquit.JoinQuitInit.getMsgPermDeny;

class JoinQuitExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {

            sender.sendMessage(getMsgNoConsole());
            return true;

        } else {

            Player player = (Player) sender;

            if (player.hasPermission("jq.access")) {

                if (args.length == 0) displayHelp(player);
                else switch (args[0].toLowerCase()) {

                    case "join":
                        if (player.hasPermission("jq.join"))
                            listMessages(player, JOIN);
                        else player.sendMessage(getMsgPermDeny());
                        break;

                    case "quit":
                        if (player.hasPermission("jq.quit"))
                            listMessages(player, QUIT);
                        else player.sendMessage(getMsgPermDeny());
                        break;

                    case "clear":
                        clearMessage(player);
                        break;

                    default:
                        displayHelp(player);
                        break;

                }

            } else player.sendMessage(getMsgPermDeny());

        }

        return true;

    }

}
