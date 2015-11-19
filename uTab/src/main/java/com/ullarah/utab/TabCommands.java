package com.ullarah.utab;

import com.ullarah.utab.function.CommonString;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static com.ullarah.utab.TabInit.*;

class TabCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        CommonString commonString = new CommonString();

        if (!sender.hasPermission("utab.commands")) {
            commonString.messagePermDeny(getPlugin(), sender);
            return true;
        }

        if (args.length == 0) commonString.messageSend(getPlugin(), sender,
                " HT:" + headerMessageTotal + " HC:" + headerMessageCurrent
                        + " FT:" + footerMessageTotal + " FC:" + footerMessageCurrent);
        else switch (args[0].toUpperCase()) {

            case "RELOAD":
                getTabTask().cancel();

                new TabFunctions().reloadTabConfig();
                new TabTask().startTabTimer();

                commonString.messageSend(getPlugin(), sender, "Messages Reloaded");
                break;

        }

        return true;

    }

}
