package com.ullarah.utab;

import com.ullarah.utab.function.CommonString;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

class TabCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        CommonString commonString = new CommonString();

        if (!sender.hasPermission("utab.commands")) {
            commonString.messagePermDeny(TabInit.getPlugin(), sender);
            return true;
        }

        if (args.length == 0) commonString.messageSend(TabInit.getPlugin(), sender,
                " HT:" + TabInit.headerMessageTotal + " HC:" + TabInit.headerMessageCurrent
                        + " FT:" + TabInit.footerMessageTotal + " FC:" + TabInit.footerMessageCurrent);
        else switch (args[0].toUpperCase()) {

            case "RELOAD":
                TabInit.getTabTask().cancel();

                new TabFunctions().reloadTabConfig();
                new TabTask().startTabTimer();

                commonString.messageSend(TabInit.getPlugin(), sender, "Messages Reloaded");
                break;

        }

        return true;

    }

}
