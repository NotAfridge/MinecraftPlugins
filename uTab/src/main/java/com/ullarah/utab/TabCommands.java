package com.ullarah.utab;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static com.ullarah.utab.TabFunctions.reloadTabConfig;
import static com.ullarah.utab.TabInit.*;
import static com.ullarah.utab.TabTask.startTabTimer;

class TabCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        String prefix = ChatColor.GOLD + "[" + getPlugin().getName() + "]" + ChatColor.RESET;

        if (sender.hasPermission("utab.commands")) {

            if (args.length == 0) sender.sendMessage(prefix
                    + " HT:" + headerMessageTotal + " HC:" + headerMessageCurrent
                    + " FT:" + footerMessageTotal + " FC:" + footerMessageCurrent);
            else switch (args[0].toUpperCase()) {

                case "RELOAD":
                    getTabTask().cancel();

                    reloadTabConfig();
                    startTabTimer();

                    sender.sendMessage(prefix + " Messages Reloaded");
                    break;

            }

        } else sender.sendMessage(prefix + ChatColor.RED + " No Permission.");

        return true;

    }

}
