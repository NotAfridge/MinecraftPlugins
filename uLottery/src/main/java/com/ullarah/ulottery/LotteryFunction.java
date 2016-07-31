package com.ullarah.ulottery;

import com.ullarah.ulottery.function.Broadcast;
import com.ullarah.ulottery.function.CommonString;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

class LotteryFunction extends LotteryMessageInit {

    private final String sep = ChatColor.RESET + " - ";

    LotteryFunction(FileConfiguration config) {
        super(config);
    }

    LotteryFunction() {
    }

    void sendConsoleStatistics(CommandSender sender) {

        String consoleString = "" + ChatColor.WHITE + ChatColor.BOLD + "Lottery" + ChatColor.RESET +
                ChatColor.stripColor(sep + getBank().getMessage()
                        + sep + getRecentDeath().getMessage()
                        + sep + getBlock().getAmount());

        if (getBank().getAmount() > 0) sender.sendMessage(getRecentWinner().getWinAmount() > 0
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

        if (getBank().getAmount() > 0) {

            player.sendMessage(getDuration().getMessage() + sep + getCountdown().getMessage());
            String suspensionMessage = getSuspension().getMessage(player);
            if (!suspensionMessage.equals("")) player.sendMessage(suspensionMessage);

        }

        if (getRecentWinner().getWinAmount() > 0) player.sendMessage(getRecentWinner().getMessage());

    }

    private Player getRandomWinner() {

        Collection onlinePlayers = LotteryInit.getPlugin().getServer().getOnlinePlayers();
        ArrayList<Player> validPlayers = new ArrayList<>();

        for (Object player : onlinePlayers) {
            Player p = (Player) player;
            if (getSuspension().getMap().containsKey(p.getUniqueId())) continue;
            if (getExclude().hasPlayer(p.getPlayerListName())) continue;
            if (getRecentWinner().getWinName().equalsIgnoreCase(p.getPlayerListName()))
                continue;
            validPlayers.add((Player) player);
        }

        if (validPlayers.isEmpty()) {
            getCountdown().setCount(getCountdown().getOriginal());
            return null;
        }

        return validPlayers.get(new Random().nextInt(validPlayers.size()));

    }

    private String depositWinnings(Player player) {

        if (LotteryInit.getEconomy() != null) {

            LotteryInit.getEconomy().depositPlayer(player, getBank().getAmount());
            return "$" + getBank().getAmount();

        } else {

            LotteryInit.getPlugin().getServer().getScheduler().runTask(
                    LotteryInit.getPlugin(), () ->
                            player.getWorld().dropItemNaturally(player.getEyeLocation(),
                                    new ItemStack(getBank().getItemMaterial(),
                                            getBank().getAmount())));

            return getBank().getAmount() + " " + getBank().getItemMaterial().name()
                    .replace("_", " ").toLowerCase() + (getBank().getAmount() > 1 ? "s" : "");
        }

    }

    void lotteryFinished() {

        if (getBank().getAmount() > 0) {

            Player player = getRandomWinner();

            if (player == null) return;

            new Broadcast().sendMessage(LotteryInit.getPlugin(), new String[]{
                    ChatColor.YELLOW + player.getPlayerListName()
                            + ChatColor.RESET + " won " + ChatColor.GREEN
                            + depositWinnings(player)
                            + ChatColor.RESET + " from the Lottery!"
            });

            getRecentWinner().setWinName(player.getPlayerListName());
            getRecentWinner().setWinAmount(getBank().getAmount());

            getHistory().addHistory(player.getPlayerListName(), getBank().getAmount());

        }

        resetStatistics();

    }

    void resetStatistics() {

        getCountdown().reset();
        getDuration().reset();
        getBank().reset();
        getBlock().reset();
        getRecentDeath().reset();
        getSuspension().reset();

    }

    void forceWin() {

        lotteryFinished();

    }

}
