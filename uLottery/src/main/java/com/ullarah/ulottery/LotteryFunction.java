package com.ullarah.ulottery;

import com.ullarah.ulottery.function.CommonString;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class LotteryFunction extends LotteryMessageInit {

    private final String sep = ChatColor.RESET + " - ";

    void sendConsoleStatistics(CommandSender sender) {

        String consoleString = "" + ChatColor.WHITE + ChatColor.BOLD + "Lottery" + ChatColor.RESET +
                ChatColor.stripColor(sep + getBank().getMessage()
                        + sep + getRecentDeath().getMessage()
                        + sep + getBlock().getAmount());

        if (getBank().getWinAmount() > 0) sender.sendMessage(getRecentWinner().getWinAmount() > 0
                ? consoleString + ChatColor.stripColor(sep + getCountdown().getMessage()
                + sep + getRecentWinner().getWinName())
                : consoleString + ChatColor.stripColor(sep + getCountdown().getMessage()));
        else sender.sendMessage(consoleString);

    }

    void sendPlayerStatistics(Player player) {

        CommonString commonString = new CommonString();

        commonString.messageSend(LotteryInit.getPlugin(), player, "Lottery Statistics");

        player.sendMessage(getBank().getMessage());

        TextComponent blockBreaks = new TextComponent("");
        TextComponent deathRecent = new TextComponent("");

        if (getBlock().getAmount() > 0) {

            TextComponent blockBreakMessage = new TextComponent(getBlock().getMessage());
            TextComponent blockBreakHover = new TextComponent(String.valueOf(getBlock().getAmount()));

            blockBreakHover.setColor(net.md_5.bungee.api.ChatColor.RED);
            blockBreakHover.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder(String.valueOf(getBlock().getTotal())).create()));

            blockBreakMessage.addExtra(blockBreakHover);
            blockBreaks = blockBreakMessage;

        }

        if (!getRecentDeath().getName().equals("")) {

            TextComponent deathRecentMessage = new TextComponent(getRecentDeath().getMessage());
            TextComponent deathNameHover = new TextComponent(getRecentDeath().getName());

            deathNameHover.setColor(net.md_5.bungee.api.ChatColor.RED);
            deathNameHover.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder(getRecentDeath().getReason()).create()));

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

        if (getBank().getWinAmount() > 0) {

            player.sendMessage(getDuration().getMessage() + sep + getCountdown().getMessage());
            String suspensionMessage = getSuspension().getMessage(player);
            if (!suspensionMessage.equals("")) player.sendMessage(suspensionMessage);

        }

        if (getRecentWinner().getWinAmount() > 0) player.sendMessage(getRecentWinner().getMessage());

    }

}
