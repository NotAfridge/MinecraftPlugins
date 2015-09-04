package com.ullarah.ulottery;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.ulottery.Init.*;

class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        String separator = ChatColor.RESET + " - ";
        String minute = "Minute";
        String hour = "Hour";

        String deathBank = ChatColor.RED + "Nobody has died yet!";
        if (deathLotteryBank > 0)
            deathBank = ChatColor.YELLOW + "Bank: " + ChatColor.GREEN + "$" + deathLotteryBank;

        String deathRecent = "";
        if (!recentDeathName.equals("")) deathRecent = separator + ChatColor.YELLOW + "Recent Death: " + ChatColor.RED;

        String deathWinner = ChatColor.YELLOW + "Recent Winner: " + ChatColor.RED + recentWinnerName +
                separator + ChatColor.GREEN + "$" + recentWinnerAmount;

        String deathCountdownMessage = ChatColor.YELLOW + "Countdown: " + ChatColor.RED + deathCountdown;
        String deathDurationMessage = "";

        if (deathDuration > 0) {
            int deathDurationHour = deathDuration / 60;
            int deathDurationMinute = deathDuration % 60;

            if (deathDurationHour > 1) hour = "Hours";
            if (deathDurationMinute > 1) minute = "Minutes";

            String deathHourMinute = deathDuration / 60 < 1 ? deathDuration + " Minutes" :
                    String.format("%d " + hour + " %02d " + minute, deathDuration / 60, deathDuration % 60);
            deathDurationMessage = ChatColor.YELLOW + "Duration: " + ChatColor.RED + deathHourMinute + separator;
        }

        String deathTimeout = "";

        String pauseMessage = "There are less than " + ChatColor.YELLOW + totalPlayerPause + ChatColor.RESET +
                " players. Death Lottery is paused.";

        if (command.getName().equalsIgnoreCase("dlot")) {

            if (deathCountdown > 1) minute = "Minutes";

            if (commandSender instanceof ConsoleCommandSender) {

                String consoleString = "" + ChatColor.RED + ChatColor.BOLD + "DEATH LOTTERY" + ChatColor.RESET +
                        ChatColor.stripColor(separator + deathBank + deathRecent + recentDeathName);

                if (deathLotteryBank > 0) {
                    if (recentWinnerAmount > 0)
                        commandSender.sendMessage(consoleString + ChatColor.stripColor(separator + deathCountdownMessage + " " +
                                minute + separator + deathWinner));
                    else
                        commandSender.sendMessage(consoleString + ChatColor.stripColor(separator + deathCountdownMessage + " " + minute));
                } else commandSender.sendMessage(consoleString);

                if (deathLotteryPaused) commandSender.sendMessage(pauseMessage);

            } else {

                Player player = (Player) commandSender;

                if (playerDeathSuspension.containsKey(((Player) commandSender).getUniqueId())) {

                    Integer playerTimeout = playerDeathSuspension.get(((Player) commandSender).getUniqueId());
                    if (playerTimeout > 1) minute = "Minutes";
                    deathTimeout = ChatColor.YELLOW + "Suspension: " + ChatColor.RED + playerTimeout + " " + minute;

                }

                player.sendMessage(new String[]{
                        "" + ChatColor.RED + ChatColor.BOLD + "DEATH LOTTERY" +
                                ChatColor.RESET + " - " + ChatColor.DARK_AQUA + "An experiment by Ullarah",
                        ChatColor.STRIKETHROUGH + "----------------------------------------------------",
                        "When you die, " + ChatColor.GREEN + "$5" + ChatColor.RESET + " goes into the Death Lottery bank.",
                        "You are then " + ChatColor.RED + "suspended" + ChatColor.RESET +
                                " from Death Lottery for " + ChatColor.YELLOW + "30+ minutes" + ChatColor.RESET + ".",
                        "Nobody dies in an hour? The money goes to a random player!",
                        ChatColor.STRIKETHROUGH + "----------------------------------------------------"
                });

                if (!deathRecent.equals("")) {

                    TextComponent deathBankMessage = new TextComponent(deathBank);
                    TextComponent deathRecentMessage = new TextComponent(deathRecent);
                    TextComponent deathNameHover = new TextComponent(recentDeathName);

                    deathNameHover.setColor(net.md_5.bungee.api.ChatColor.RED);
                    deathNameHover.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            new ComponentBuilder(recentDeathReason).create()));

                    deathBankMessage.addExtra(deathRecentMessage);
                    deathBankMessage.addExtra(deathNameHover);

                    player.spigot().sendMessage(deathBankMessage);

                } else player.sendMessage(deathBank);

                if (deathLotteryBank > 0) {
                    commandSender.sendMessage(deathDurationMessage + deathCountdownMessage + " " + minute);
                    if (!deathTimeout.equals("")) commandSender.sendMessage(deathTimeout);
                }

                if (recentWinnerAmount > 0) commandSender.sendMessage(deathWinner);

                if (deathLotteryPaused) commandSender.sendMessage(pauseMessage);

            }

        }

        return false;

    }

}
