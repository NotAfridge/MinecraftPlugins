package com.ullarah.ujoinquit;

import com.ullarah.ujoinquit.function.CommonString;
import com.ullarah.ujoinquit.function.PermissionCheck;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class JoinQuitExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        CommonString commonString = new CommonString();

        if (!(sender instanceof Player)) {

            commonString.messageNoConsole(JoinQuitInit.getPlugin(), sender);
            return true;

        } else {

            PermissionCheck permissionCheck = new PermissionCheck();
            JoinQuitFunctions joinQuitFunctions = new JoinQuitFunctions();

            Player player = (Player) sender;

            if (args.length == 0) joinQuitFunctions.displayHelp(player);
            else switch (args[0].toUpperCase()) {

                case "JOIN":
                    if (permissionCheck.check(player, "jq.access", "jq.join"))
                        joinQuitFunctions.listMessages(player, JoinQuitFunctions.Message.JOIN);
                    else commonString.messagePermDeny(JoinQuitInit.getPlugin(), sender);
                    break;

                case "QUIT":
                    if (permissionCheck.check(player, "jq.access", "jq.quit"))
                        joinQuitFunctions.listMessages(player, JoinQuitFunctions.Message.QUIT);
                    else commonString.messagePermDeny(JoinQuitInit.getPlugin(), sender);
                    break;

                case "EXTRA":
                    if (permissionCheck.check(player, "jq.access", "jq.extra")) joinQuitFunctions.showExtra(player);
                    else commonString.messagePermDeny(JoinQuitInit.getPlugin(), sender);
                    break;

                case "CLEAR":
                    joinQuitFunctions.clearMessage(player);
                    break;

                default:
                    joinQuitFunctions.displayHelp(player);
                    break;

            }

        }

        return true;

    }

}
