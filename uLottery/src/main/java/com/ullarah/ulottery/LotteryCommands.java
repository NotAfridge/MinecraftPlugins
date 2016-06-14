package com.ullarah.ulottery;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.ulottery.LotteryInit.*;

class LotteryCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        String sep = ChatColor.RESET + " - ";
        String minute = "Minute";
        String hour = "Hour";

        String deathBank = ChatColor.RED + "Nobody has died yet!";
        if (deathLotteryBank > 0) {
            String bank = ChatColor.YELLOW + "Bank: " + ChatColor.GREEN;
            deathBank = economy != null ? bank + "$" + deathLotteryBank :
                    bank + deathLotteryBank + " " + winItemMaterial.name().replace("_", " ").toLowerCase()
                            + (deathLotteryBank > 1 ? "s" : "");
        }

        String deathRecent = "";
        if (!recentDeathName.equals("")) deathRecent = sep + ChatColor.YELLOW + "Recent Death: " + ChatColor.RED;

        String winner = ChatColor.YELLOW + "Recent Winner: " + ChatColor.RED + recentWinnerName +
                sep + ChatColor.GREEN;
        String deathWinner = economy != null ? winner + "$" + recentWinnerAmount :
                winner + recentWinnerAmount + " " +  winItemMaterial.name().replace("_", " ").toLowerCase()
                        + (recentWinnerAmount > 1 ? "s" : "");

        String deathCountdownMessage = ChatColor.YELLOW + "Countdown: " + ChatColor.RED + deathCountdown;
        String deathDurationMessage = "";

        if (deathDuration > 0) {
            int deathDurationHour = deathDuration / 60;
            int deathDurationMinute = deathDuration % 60;

            if (deathDurationHour > 1) hour += "s";
            if (deathDurationMinute > 1) minute += "s";

            String deathHourMinute = deathDuration / 60 < 1 ? deathDuration + " Minutes" :
                    String.format("%d " + hour + " %02d " + minute, deathDuration / 60, deathDuration % 60);
            deathDurationMessage = ChatColor.YELLOW + "Duration: " + ChatColor.RED + deathHourMinute + sep;
        }

        String deathTimeout = "";

        String pauseMessage = "There are less than " + ChatColor.YELLOW + totalPlayerPause + ChatColor.RESET +
                " players. Death Lottery is paused.";

        if (command.getName().equalsIgnoreCase("dlot")) {

            if (deathCountdown > 1) minute += "s";

            if (commandSender instanceof ConsoleCommandSender) {

                String consoleString = "" + ChatColor.RED + ChatColor.BOLD + "Death Lottery" + ChatColor.RESET +
                        ChatColor.stripColor(sep + deathBank + deathRecent + recentDeathName);

                if (deathLotteryBank > 0) {
                    if (recentWinnerAmount > 0)
                        commandSender.sendMessage(consoleString + ChatColor.stripColor(sep + deathCountdownMessage + " " +
                                minute + sep + deathWinner));
                    else
                        commandSender.sendMessage(consoleString + ChatColor.stripColor(sep + deathCountdownMessage + " " + minute));
                } else commandSender.sendMessage(consoleString);

                if (deathLotteryPaused) commandSender.sendMessage(pauseMessage);

            } else {

                Player player = (Player) commandSender;

                if (playerDeathSuspension.containsKey(((Player) commandSender).getUniqueId())) {

                    Integer playerTimeout = playerDeathSuspension.get(((Player) commandSender).getUniqueId());
                    if (playerTimeout > 1) minute += "s";
                    deathTimeout = ChatColor.YELLOW + "Suspension: " + ChatColor.RED + playerTimeout + " " + minute;

                }

                String winType = economy != null ? "$" + winVaultAmount :
                        winItemAmount + " " + winItemMaterial.name().replace("_", " ").toLowerCase()
                                + (winItemAmount > 1 ? "s" : "");

                String suspensionAmount = String.valueOf(deathSuspension);
                String countdownAmount = String.valueOf(deathCountdownReset);

                player.sendMessage(new String[]{
                        "" + ChatColor.RED + ChatColor.BOLD + "Death Lottery",
                        ChatColor.STRIKETHROUGH + "----------------------------------------------------",
                        "When you die, " + ChatColor.GREEN + winType + ChatColor.RESET + " will go to the Death Lottery bank.",
                        "You are then " + ChatColor.RED + "suspended" + ChatColor.RESET +
                                " from Death Lottery for " + ChatColor.YELLOW + suspensionAmount + " minutes" + ChatColor.RESET + ".",
                        "If nobody dies in " + ChatColor.YELLOW + countdownAmount + " minutes" + ChatColor.RESET + ", a random player will win!",
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

        return true;

    }

}
