package com.ullarah.ulottery;

import com.ullarah.ulottery.function.CommonString;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

class LotteryCommands extends LotteryMessageInit implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (command.getName().equalsIgnoreCase("lottery")) {

            CommonString commonString = new CommonString();
            LotteryFunction lotteryFunction = new LotteryFunction();

            if (getPause().isPaused()) {
                commonString.messageSend(LotteryInit.getPlugin(), sender, getPause().getMessage());
                return true;
            }

            if (sender instanceof ConsoleCommandSender) lotteryFunction.sendConsoleStatistics(sender);
            else {

                Player player = (Player) sender;

                @SuppressWarnings("SpellCheckingInspection")
                String adminPermission = "ulottery.admin";

                if (args.length >= 1) {

                    switch (args[0].toLowerCase()) {

                        case "x":
                        case "e":
                        case "exclude":
                            if (player.hasPermission(adminPermission)) {
                                if (args.length >= 2)
                                    commonString.messageSend(LotteryInit.getPlugin(), player, getExclude().addPlayer(args[1]));
                                else commonString.messageSend(LotteryInit.getPlugin(), player, "Exclude who?");
                            } else commonString.messagePermDeny(LotteryInit.getPlugin(), sender);
                            break;

                        case "i":
                        case "include":
                            if (player.hasPermission(adminPermission)) {
                                if (args.length >= 2)
                                    commonString.messageSend(LotteryInit.getPlugin(), player, getExclude().removePlayer(args[1]));
                                else commonString.messageSend(LotteryInit.getPlugin(), player, "Include who?");
                            } else commonString.messagePermDeny(LotteryInit.getPlugin(), sender);
                            break;

                        case "h":
                        case "history":
                            getHistory().showHistory(player);
                            break;

                    }

                } else lotteryFunction.sendPlayerStatistics(player);

            }

        }

        return true;

    }

}
