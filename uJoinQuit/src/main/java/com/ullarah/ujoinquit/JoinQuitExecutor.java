package com.ullarah.ujoinquit;

import com.ullarah.ujoinquit.function.CommonString;
import com.ullarah.ujoinquit.function.PermissionCheck;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.ujoinquit.JoinQuitFunctions.Message.JOIN;
import static com.ullarah.ujoinquit.JoinQuitFunctions.Message.QUIT;
import static com.ullarah.ujoinquit.JoinQuitInit.getPlugin;

class JoinQuitExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        CommonString commonString = new CommonString();

        if (!(sender instanceof Player)) {

            commonString.messageNoConsole(getPlugin(), sender);
            return true;

        } else {

            PermissionCheck permissionCheck = new PermissionCheck();
            JoinQuitFunctions joinQuitFunctions = new JoinQuitFunctions();

            Player player = (Player) sender;

            if (args.length == 0) joinQuitFunctions.displayHelp(player);
            else switch (args[0].toUpperCase()) {

                case "JOIN":
                    if (permissionCheck.check(player, "jq.access", "jq.join"))
                        joinQuitFunctions.listMessages(player, JOIN);
                    else commonString.messagePermDeny(getPlugin(), sender);
                    break;

                case "QUIT":
                    if (permissionCheck.check(player, "jq.access", "jq.quit"))
                        joinQuitFunctions.listMessages(player, QUIT);
                    else commonString.messagePermDeny(getPlugin(), sender);
                    break;

                case "EXTRA":
                    if (permissionCheck.check(player, "jq.access", "jq.extra")) joinQuitFunctions.showExtra(player);
                    else commonString.messagePermDeny(getPlugin(), sender);
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
