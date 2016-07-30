package com.ullarah.ulottery;

import com.ullarah.ulottery.function.CommonString;
import com.ullarah.ulottery.message.Exclude;
import com.ullarah.ulottery.message.History;
import com.ullarah.ulottery.message.Pause;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

class LotteryCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (command.getName().equalsIgnoreCase("lottery")) {

            Plugin plugin = LotteryInit.getPlugin();
            History history = LotteryInit.history;
            Pause pause = LotteryInit.pause;
            LotteryFunction function = new LotteryFunction();
            CommonString commonString = new CommonString();
            Exclude exclude = LotteryInit.getExclude();

            if (pause.getPaused()) {
                sender.sendMessage(pause.getMessage());
                return true;
            }

            if (sender instanceof ConsoleCommandSender) function.sendConsoleStatistics(sender);
            else {

                Player player = (Player) sender;

                if (args.length >= 1) {

                    switch (args[0].toLowerCase()) {

                        case "x":
                        case "e":
                        case "exclude":
                            if (player.hasPermission("ulottery.admin")) {
                                if (args.length >= 2)
                                    commonString.messageSend(plugin, player, exclude.addPlayer(args[1]));
                                else commonString.messageSend(plugin, player, "Exclude who?");
                            } else commonString.messagePermDeny(plugin, sender);
                            break;

                        case "i":
                        case "include":
                            if (player.hasPermission("ulottery.admin")) {
                                if (args.length >= 2)
                                    commonString.messageSend(plugin, player, exclude.removePlayer(args[1]));
                                else commonString.messageSend(plugin, player, "Include who?");
                            } else commonString.messagePermDeny(plugin, sender);
                            break;

                        case "h":
                        case "history":
                            history.showHistory(player);
                            break;

                    }

                } else function.sendPlayerStatistics(player);

            }

        }

        return true;

    }

}
