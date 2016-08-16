package com.ullarah.utab;

import com.ullarah.utab.function.CommonString;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

class TabCommands implements CommandExecutor {

    private final Plugin plugin;
    private final TabFunctions tabFunctions;

    TabCommands(Plugin instance, TabFunctions functions) {
        plugin = instance;
        tabFunctions = functions;
    }

    private Plugin getPlugin() {
        return plugin;
    }

    private TabFunctions getTabFunctions() {
        return tabFunctions;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        CommonString commonString = new CommonString(getPlugin());

        if (!sender.hasPermission("utab.commands")) {
            commonString.messagePermDeny(sender);
            return true;
        }

        String headerTotal = "HT:" + (getTabFunctions().headerMessageTotal + 1),
                headerCurrent = " HC:" + getTabFunctions().headerMessageCurrent,
                footerTotal = " FT:" + (getTabFunctions().footerMessageTotal + 1),
                footerCurrent = " FC:" + getTabFunctions().footerMessageCurrent;

        if (args.length == 0) {
            commonString.messageSend(sender, new String[]{
                    commonString.pluginPrefix() + headerTotal + headerCurrent + footerTotal + footerCurrent,
                    "  " + getTabFunctions().getCurrentHeader(),
                    "  " + getTabFunctions().getCurrentFooter()
            });
            return true;
        }

        switch (args[0].toUpperCase()) {

            case "RELOAD":
                getTabFunctions().getTabTask().cancel();
                getTabFunctions().reloadTabConfig();

                new TabTask(getPlugin(), getTabFunctions()).startTabTimer();

                commonString.messageSend(sender, "Messages Reloaded");
                break;

        }

        return true;

    }

}
