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

    void sendConsoleStatistics(CommandSender sender) {

        String consoleString = "" + ChatColor.WHITE + ChatColor.BOLD + "Lottery" + ChatColor.RESET +
                ChatColor.stripColor(sep + bank.getMessage()
                        + sep + recentDeath.getMessage()
                        + sep + block.getAmount());

        if (bank.getAmount() > 0) sender.sendMessage(recentWinner.getAmount() > 0
                ? consoleString + ChatColor.stripColor(sep + countdown.getMessage() + sep + recentWinner.getName())
                : consoleString + ChatColor.stripColor(sep + countdown.getMessage()));
        else sender.sendMessage(consoleString);

    }

    void sendPlayerStatistics(Player player) {

        player.sendMessage(ChatColor.GOLD + "[" + LotteryInit.getPlugin().getName() + "] "
                + ChatColor.WHITE + "Lottery Statistics");

        player.sendMessage(bank.getMessage());

        TextComponent blockBreaks = new TextComponent("");
        TextComponent deathRecent = new TextComponent("");

        if (block.getAmount() > 0) {

            TextComponent blockBreakMessage = new TextComponent(block.getMessage());
            TextComponent blockBreakHover = new TextComponent(String.valueOf(block.getAmount()));

            blockBreakHover.setColor(net.md_5.bungee.api.ChatColor.RED);
            blockBreakHover.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder(String.valueOf(block.getTotal())).create()));

            blockBreakMessage.addExtra(blockBreakHover);
            blockBreaks = blockBreakMessage;

        }

        if (!recentDeath.getName().equals("")) {

            TextComponent deathRecentMessage = new TextComponent(recentDeath.getMessage());
            TextComponent deathNameHover = new TextComponent(recentDeath.getName());

            deathNameHover.setColor(net.md_5.bungee.api.ChatColor.RED);
            deathNameHover.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder(recentDeath.getReason()).create()));

            deathRecentMessage.addExtra(deathNameHover);
            deathRecent = deathRecentMessage;

        }

        if (blockBreaks.getText().length() >= 1 && deathRecent.getText().length() <= 0)
            player.spigot().sendMessage(blockBreaks);
        if (blockBreaks.getText().length() <= 0 && deathRecent.getText().length() >= 1) {
            TextComponent textSeparate = new TextComponent("  ");
            textSeparate.addExtra(deathRecent);
            player.spigot().sendMessage(textSeparate);
        }
        if (blockBreaks.getText().length() >= 1 && deathRecent.getText().length() >= 1) {
            TextComponent textSeparate = new TextComponent(sep);
            blockBreaks.addExtra(textSeparate);
            blockBreaks.addExtra(deathRecent);
            player.spigot().sendMessage(blockBreaks);
        }

        if (bank.getAmount() > 0) {

            player.sendMessage(duration.getMessage() + sep + countdown.getMessage());
            String suspensionMessage = suspension.getMessage(player);
            if (!suspensionMessage.equals("")) player.sendMessage(suspensionMessage);

        }

        if (recentWinner.getAmount() > 0) player.sendMessage(recentWinner.getMessage());

    }

}
