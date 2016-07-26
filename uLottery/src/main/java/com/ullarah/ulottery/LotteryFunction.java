package com.ullarah.ulottery;

import com.ullarah.ulottery.message.*;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LotteryFunction {

    private String sep = ChatColor.RESET + " - ";

    private Bank bank = LotteryInit.bank;
    private Block block = LotteryInit.block;
    private Countdown countdown = LotteryInit.countdown;
    private Duration duration = LotteryInit.duration;
    private RecentDeath recentDeath = LotteryInit.recentDeath;
    private RecentWinner recentWinner = LotteryInit.recentWinner;
    private Suspension suspension = LotteryInit.suspension;

    void sendConsoleDeathStatistics(CommandSender sender) {

        String consoleString = "" + ChatColor.WHITE + ChatColor.BOLD + "Lottery" + ChatColor.RESET +
                ChatColor.stripColor(sep + bank.getMessage()
                        + sep + recentDeath.getMessage()
                        + sep + block.getAmount());

        if (bank.getAmount() > 0) sender.sendMessage(recentWinner.getAmount() > 0
                ? consoleString + ChatColor.stripColor(sep + countdown.getMessage() + sep + recentWinner.getName())
                : consoleString + ChatColor.stripColor(sep + countdown.getMessage()));
        else sender.sendMessage(consoleString);

    }

    void sendDeathStatistics(CommandSender sender) {

        Player player = (Player) sender;

        player.sendMessage(ChatColor.GOLD + "[" + LotteryInit.getPlugin().getName() + "] "
                + ChatColor.WHITE + "Lottery Statistics");

        player.sendMessage(bank.getMessage());

        String blockBreaks = "";
        if (block.getAmount() > 0) blockBreaks = block.getMessage();

        if (!recentDeath.getName().equals("")) {

            TextComponent blockBreakage = new TextComponent(blockBreaks + sep);
            TextComponent deathRecentMessage = new TextComponent(recentDeath.getMessage());
            TextComponent deathNameHover = new TextComponent(recentDeath.getName());

            deathNameHover.setColor(net.md_5.bungee.api.ChatColor.RED);
            deathNameHover.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder(recentDeath.getReason()).create()));

            blockBreakage.addExtra(deathRecentMessage);
            blockBreakage.addExtra(deathNameHover);

            player.spigot().sendMessage(deathRecentMessage);

        } else if (!blockBreaks.equals("")) player.sendMessage(blockBreaks);

        if (bank.getAmount() > 0) {

            player.sendMessage(duration.getMessage() + sep + countdown.getMessage());
            String suspensionMessage = suspension.getMessage(player);
            if (!suspensionMessage.equals("")) player.sendMessage(suspensionMessage);

        }

        if (recentWinner.getAmount() > 0) player.sendMessage(recentWinner.getMessage());

    }

}
