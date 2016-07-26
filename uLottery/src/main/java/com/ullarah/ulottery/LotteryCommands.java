package com.ullarah.ulottery;

import com.ullarah.ulottery.message.*;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

class LotteryCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (command.getName().equalsIgnoreCase("lottery")) {

            String sep = ChatColor.RESET + " - ";

            Bank bank = LotteryInit.bank;
            Countdown countdown = LotteryInit.countdown;
            Duration duration = LotteryInit.duration;
            Pause pause = LotteryInit.pause;
            RecentDeath recentDeath = LotteryInit.recentDeath;
            RecentWinner recentWinner = LotteryInit.recentWinner;
            Suspension suspension = LotteryInit.suspension;

            if (pause.getPaused()) {
                commandSender.sendMessage(pause.getMessage());
                return true;
            }

            if (commandSender instanceof ConsoleCommandSender) {

                String consoleString = "" + ChatColor.WHITE + ChatColor.BOLD + "Lottery" + ChatColor.RESET +
                        ChatColor.stripColor(sep + bank.getMessage() + recentDeath.getMessage());

                if (bank.getAmount() > 0) commandSender.sendMessage(recentWinner.getAmount() > 0
                        ? consoleString + ChatColor.stripColor(sep + countdown.getMessage() + sep + recentWinner.getName())
                        : consoleString + ChatColor.stripColor(sep + countdown.getMessage()));
                else commandSender.sendMessage(consoleString);

            } else {

                Player player = (Player) commandSender;

                player.sendMessage(ChatColor.GOLD + "[" + LotteryInit.getPlugin().getName() + "]"
                        + ChatColor.WHITE + "Death Lottery Statistics");

                if (!recentDeath.getName().equals("")) {

                    TextComponent deathBankMessage = new TextComponent(bank.getMessage() + sep);
                    TextComponent deathRecentMessage = new TextComponent(recentDeath.getMessage());
                    TextComponent deathNameHover = new TextComponent(recentDeath.getName());

                    deathNameHover.setColor(net.md_5.bungee.api.ChatColor.RED);
                    deathNameHover.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            new ComponentBuilder(recentDeath.getReason()).create()));

                    deathBankMessage.addExtra(deathRecentMessage);
                    deathBankMessage.addExtra(deathNameHover);

                    player.spigot().sendMessage(deathBankMessage);

                } else player.sendMessage(bank.getMessage());

                if (bank.getAmount() > 0) {
                    player.sendMessage(duration.getMessage() + sep + countdown.getMessage());
                    String suspensionMessage = suspension.getMessage(player);
                    if (!suspensionMessage.equals("")) player.sendMessage(suspensionMessage);
                }

                if (recentWinner.getAmount() > 0) player.sendMessage(recentWinner.getMessage());

            }

        }

        return true;

    }

}
