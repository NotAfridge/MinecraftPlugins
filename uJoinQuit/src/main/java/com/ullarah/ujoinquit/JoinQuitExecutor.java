package com.ullarah.ujoinquit;

import com.ullarah.ujoinquit.function.CommonString;
import com.ullarah.ujoinquit.function.PermissionCheck;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.ujoinquit.JoinQuitFunctions.*;
import static com.ullarah.ujoinquit.JoinQuitFunctions.messageType.JOIN;
import static com.ullarah.ujoinquit.JoinQuitFunctions.messageType.QUIT;
import static com.ullarah.ujoinquit.JoinQuitInit.getPlugin;

class JoinQuitExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {

            new CommonString().messageNoConsole(getPlugin(), sender);
            return true;

        } else {

            Player player = (Player) sender;

            if (args.length == 0) displayHelp(player);
            else switch (args[0].toUpperCase()) {

                case "JOIN":
                    if (new PermissionCheck().check(player, "jq.access", "jq.join")) listMessages(player, JOIN);
                    else new CommonString().messagePermDeny(getPlugin(), sender);
                    break;

                case "QUIT":
                    if (new PermissionCheck().check(player, "jq.access", "jq.quit")) listMessages(player, QUIT);
                    else new CommonString().messagePermDeny(getPlugin(), sender);
                    break;

                case "EXTRA":
                    if (new PermissionCheck().check(player, "jq.access", "jq.extra")) showExtra(player);
                    else new CommonString().messagePermDeny(getPlugin(), sender);
                    break;

                case "CLEAR":
                    clearMessage(player);
                    break;

                default:
                    displayHelp(player);
                    break;

            }

        }

        return true;

    }

}
