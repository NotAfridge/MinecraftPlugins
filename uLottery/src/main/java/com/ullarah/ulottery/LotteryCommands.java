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

class LotteryCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        String sep = ChatColor.RESET + " - ";
        String minute = "Minute";
        String hour = "Hour";

        String deathBank = ChatColor.RED + "Nobody has died yet!";
        if (LotteryInit.deathLotteryBank > 0) {
            String bank = ChatColor.YELLOW + "Bank: " + ChatColor.GREEN;
            deathBank = LotteryInit.economy != null ? bank + "$" + LotteryInit.deathLotteryBank :
                    bank + LotteryInit.deathLotteryBank + " "
                            + LotteryInit.winItemMaterial.name().replace("_", " ").toLowerCase()
                            + (LotteryInit.deathLotteryBank > 1 ? "s" : "");
        }

        if (!LotteryInit.recentDeathName.equals(""))
            LotteryInit.deathRecent = sep + ChatColor.YELLOW + "Recent Death: " + ChatColor.RED;

        String winner = ChatColor.YELLOW + "Recent Winner: " + ChatColor.RED + LotteryInit.recentWinnerName +
                sep + ChatColor.GREEN;
        String deathWinner = LotteryInit.economy != null ? winner + "$" + LotteryInit.recentWinnerAmount :
                winner + LotteryInit.recentWinnerAmount + " "
                        + LotteryInit.winItemMaterial.name().replace("_", " ").toLowerCase()
                        + (LotteryInit.recentWinnerAmount > 1 ? "s" : "");

        String deathCountdownMessage = ChatColor.YELLOW + "Countdown: " + ChatColor.RED + LotteryInit.deathCountdown;
        String deathDurationMessage = "";

        if (LotteryInit.deathDuration > 0) {
            int deathDurationHour = LotteryInit.deathDuration / 60;
            int deathDurationMinute = LotteryInit.deathDuration % 60;

            String dHour = hour;
            String dMinute = minute;
            if (deathDurationHour > 1) dHour += "s";
            if (deathDurationMinute > 1) dMinute += "s";

            String deathHourMinute = LotteryInit.deathDuration / 60 < 1 ? LotteryInit.deathDuration + " Minutes" :
                    String.format("%d " + dHour + " %02d "
                            + dMinute, LotteryInit.deathDuration / 60, LotteryInit.deathDuration % 60);
            deathDurationMessage = ChatColor.YELLOW + "Duration: " + ChatColor.RED + deathHourMinute + sep;
        }

        String deathTimeout = "";

        String pauseMessage = "There are less than " + ChatColor.YELLOW +
                LotteryInit.totalPlayerPause + ChatColor.RESET + " players. Death Lottery is paused.";

        if (command.getName().equalsIgnoreCase("dlot")) {

            String cMinute = minute;
            if (LotteryInit.deathCountdown > 1) cMinute += "s";

            if (commandSender instanceof ConsoleCommandSender) {

                String consoleString = "" + ChatColor.RED + ChatColor.BOLD + "Death Lottery" + ChatColor.RESET +
                        ChatColor.stripColor(sep + deathBank + LotteryInit.deathRecent + LotteryInit.recentDeathName);

                if (LotteryInit.deathLotteryBank > 0) {
                    if (LotteryInit.recentWinnerAmount > 0)
                        commandSender.sendMessage(consoleString
                                + ChatColor.stripColor(sep + deathCountdownMessage + " " +
                                cMinute + sep + deathWinner));
                    else
                        commandSender.sendMessage(consoleString
                                + ChatColor.stripColor(sep + deathCountdownMessage + " " + cMinute));
                } else commandSender.sendMessage(consoleString);

                if (LotteryInit.deathLotteryPaused) commandSender.sendMessage(pauseMessage);

            } else {

                Player player = (Player) commandSender;

                if (LotteryInit.playerDeathSuspension.containsKey(((Player) commandSender).getUniqueId())) {

                    Integer playerTimeout = LotteryInit.playerDeathSuspension.get(
                            ((Player) commandSender).getUniqueId());

                    String sMinute = minute;
                    if (playerTimeout > 1) sMinute += "s";

                    deathTimeout = ChatColor.YELLOW + "Suspension: " + ChatColor.RED + playerTimeout + " " + sMinute;

                }

                if (!LotteryInit.deathRecent.equals("")) {

                    TextComponent deathBankMessage = new TextComponent(deathBank);
                    TextComponent deathRecentMessage = new TextComponent(LotteryInit.deathRecent);
                    TextComponent deathNameHover = new TextComponent(LotteryInit.recentDeathName);

                    deathNameHover.setColor(net.md_5.bungee.api.ChatColor.RED);
                    deathNameHover.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            new ComponentBuilder(LotteryInit.recentDeathReason).create()));

                    deathBankMessage.addExtra(deathRecentMessage);
                    deathBankMessage.addExtra(deathNameHover);

                    player.spigot().sendMessage(deathBankMessage);

                } else player.sendMessage(deathBank);

                if (LotteryInit.deathLotteryBank > 0) {
                    commandSender.sendMessage(deathDurationMessage + deathCountdownMessage + " " + minute);
                    if (!deathTimeout.equals("")) commandSender.sendMessage(deathTimeout);
                }

                if (LotteryInit.recentWinnerAmount > 0) commandSender.sendMessage(deathWinner);

                if (LotteryInit.deathLotteryPaused) commandSender.sendMessage(pauseMessage);

            }

        }

        return true;

    }

}
