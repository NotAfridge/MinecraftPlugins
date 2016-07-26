package com.ullarah.ulottery;

import com.ullarah.ulottery.message.Pause;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

class LotteryCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (command.getName().equalsIgnoreCase("lottery")) {

            Pause pause = LotteryInit.pause;
            LotteryFunction function = new LotteryFunction();

            if (pause.getPaused()) {
                sender.sendMessage(pause.getMessage());
                return true;
            }

            if (sender instanceof ConsoleCommandSender) function.sendConsoleDeathStatistics(sender);
            else function.sendDeathStatistics(sender);

        }

        return true;

    }

}
