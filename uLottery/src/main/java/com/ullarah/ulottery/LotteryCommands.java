package com.ullarah.ulottery;

import com.ullarah.ulottery.function.CommonString;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

class LotteryCommands extends LotteryFunction implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (command.getName().equalsIgnoreCase("lottery")) {

            CommonString commonString = new CommonString(LotteryInit.getPlugin());

            if (sender instanceof ConsoleCommandSender) sendConsoleStatistics(sender);
            else {

                Player player = (Player) sender;

                @SuppressWarnings("SpellCheckingInspection")
                String adminPermission = "ulottery.admin";

                if (args.length >= 1) {

                    switch (args[0].toLowerCase()) {

                        case "b":
                        case "bank":
                            if (player.hasPermission(adminPermission)) {
                                if (args.length >= 2)
                                    commonString.messageSend(player, getBank().modify(player, args[1], false));
                                else
                                    commonString.messageSend(player, "Add or subtract how much?");
                            } else commonString.messagePermDeny(sender);
                            break;

                        case "x":
                        case "e":
                        case "exclude":
                            if (player.hasPermission(adminPermission)) {
                                if (args.length >= 2)
                                    commonString.messageSend(player, getExclude().addPlayer(args[1]));
                                else commonString.messageSend(player, "Exclude who?");
                            } else commonString.messagePermDeny(sender);
                            break;

                        case "f":
                        case "force":
                            if (player.hasPermission(adminPermission)) {
                                if (getBank().getAmount() > 0) lotteryFinished();
                                else
                                    commonString.messageSend(player, "Nothing in the bank to win.");
                            } else commonString.messagePermDeny(sender);
                            break;

                        case "h":
                        case "history":
                            getHistory().showHistory(player);
                            break;

                        case "i":
                        case "include":
                            if (player.hasPermission(adminPermission)) {
                                if (args.length >= 2)
                                    commonString.messageSend(player, getExclude().removePlayer(args[1]));
                                else commonString.messageSend(player, "Include who?");
                            } else commonString.messagePermDeny(sender);
                            break;

                        case "r":
                        case "reset":
                            if (player.hasPermission(adminPermission)) {
                                resetStatistics();
                                commonString.messageSend(player, ChatColor.RED + "Lottery has been reset.");
                            } else commonString.messagePermDeny(sender);
                            break;

                        case "d":
                        case "donate":
                            if (args.length >= 2)
                                commonString.messageSend(player, getBank().modify(player, args[1], true));
                            else commonString.messageSend(player, "Donate how much?");
                            break;

                        case "v":
                        case "version":
                            commonString.messageSend(player, LotteryInit.getPlugin().getDescription().getVersion());
                            break;

                    }

                } else {

                    if (getPause().isPaused()) {
                        commonString.messageSend(sender, getPause().getMessage());
                        return true;
                    }

                    sendPlayerStatistics(player);

                }

            }

        }

        return true;

    }

}
