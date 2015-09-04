package com.ullarah.uauction.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static com.ullarah.uauction.Init.*;

public class Bidding {

    public static void currentBid(CommandSender sender, String[] args) {

        if (args.length == 1 || args.length == 2) {
            String auctionBoxName = null;

            switch (args.length) {
                case 1:
                    auctionBoxName = args[0].toLowerCase();
                    break;
                case 2:
                    auctionBoxName = args[1].toLowerCase();
                    break;
            }

            assert auctionBoxName != null;

            File auctionBoxFile = new File(getPlugin().getDataFolder() + File.separator + "auction", auctionBoxName + ".yml");

            if (auctionBoxFile.exists()) {

                FileConfiguration auctionBox = YamlConfiguration.loadConfiguration(auctionBoxFile);

                Double auctionCurrentBid = (Double) auctionBox.get("bid");
                String auctionCurrentWinner = (String) auctionBox.get("winner");
                Boolean auctionRunning = (Boolean) auctionBox.get("running");
                Integer auctionTimer = (Integer) auctionBox.get("time");

                if (auctionRunning) {
                    if (auctionCurrentBid != 0.0) {

                        sender.sendMessage(getMsgPrefix() +
                                ChatColor.AQUA + auctionCurrentWinner + ChatColor.RESET + " is winning " +
                                ChatColor.GOLD + auctionBoxName + ChatColor.RESET + " with " +
                                ChatColor.GREEN + "$" + auctionCurrentBid);

                        sender.sendMessage(auctionTimer == 0 ? getMsgPrefix() +
                                "Less than an hour left for bidding!" : auctionTimer == 1 ? getMsgPrefix() +
                                "Only " + ChatColor.LIGHT_PURPLE + "1" + ChatColor.RESET + " hour left for bidding!" : getMsgPrefix() +
                                "Only " + ChatColor.LIGHT_PURPLE + auctionTimer + ChatColor.RESET + " hours left for bidding!");

                    } else sender.sendMessage(getMsgPrefix() + "There are no current bids on " +
                            ChatColor.GOLD + auctionBoxName);
                } else sender.sendMessage(getMsgPrefix() + "That auction is no longer running!");

            } else sender.sendMessage(getMsgPrefix() + "That auction doesn't exist!");

        } else sender.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "/ainfo <name>");

    }

    public static void auctionBid(CommandSender sender, String[] args) throws IOException {

        if (args.length == 2 || args.length == 3) {
            String auctionBoxName = null;
            Double auctionBidAmount = null;

            switch (args.length) {
                case 2:
                    auctionBoxName = args[0].toLowerCase();
                    auctionBidAmount = Double.parseDouble(args[1]);
                    break;
                case 3:
                    auctionBoxName = args[1].toLowerCase();
                    auctionBidAmount = Double.parseDouble(args[2]);
                    break;
            }

            assert auctionBoxName != null;
            assert auctionBidAmount != null;

            OfflinePlayer player = (OfflinePlayer) sender;
            Double currentBalance = economy.getBalance(player);

            if (auctionBidAmount <= currentBalance) {
                File auctionBoxFile = new File(getPlugin().getDataFolder() + File.separator + "auction", auctionBoxName + ".yml");

                if (auctionBoxFile.exists()) {

                    FileConfiguration auctionBox = YamlConfiguration.loadConfiguration(auctionBoxFile);

                    Double auctionReserve = (Double) auctionBox.get("reserve");
                    Double auctionMinBid = (Double) auctionBox.get("minbid");
                    Double auctionCurrentBid = (Double) auctionBox.get("bid");
                    UUID auctionCurrentWinnerId = UUID.fromString((String) auctionBox.get("winnerid"));
                    UUID auctionCreatorId = UUID.fromString((String) auctionBox.get("creatorid"));
                    Boolean auctionRunning = (Boolean) auctionBox.get("running");

                    if (!player.getUniqueId().equals(auctionCreatorId) || sender.hasPermission("uauction.forcebid"))
                        if (auctionRunning) if (auctionBidAmount > auctionReserve)
                            if (auctionBidAmount > (auctionMinBid + auctionCurrentBid)) {

                                economy.withdrawPlayer(player, auctionBidAmount);

                                sender.sendMessage(getMsgPrefix() +
                                        "Placed a bid of " + ChatColor.GREEN + "$" + auctionBidAmount +
                                        ChatColor.RESET + " on " + ChatColor.GOLD + auctionBoxName);

                                for (Player playerChat : chatMembers)
                                    playerChat.sendMessage(getMsgChatPrefix() + ChatColor.LIGHT_PURPLE +
                                            player.getName() + ChatColor.RESET + " is now winning the " + ChatColor.YELLOW +
                                            auctionBoxName + ChatColor.RESET + " auction!");

                                if (Bukkit.getOfflinePlayer(auctionCurrentWinnerId).isOnline() && !player.getUniqueId().equals(auctionCurrentWinnerId))
                                    Bukkit.getPlayer(auctionCurrentWinnerId).sendMessage(getMsgPrefix() + ChatColor.LIGHT_PURPLE +
                                            "You have been outbid on " + ChatColor.GOLD + auctionBoxName);

                                if (auctionCurrentBid != 0.0)
                                    economy.depositPlayer(Bukkit.getOfflinePlayer(auctionCurrentWinnerId), auctionCurrentBid);

                                auctionBox.set("winner", player.getName());
                                auctionBox.set("winnerid", player.getUniqueId().toString());
                                auctionBox.set("bid", auctionBidAmount);
                                auctionBox.save(auctionBoxFile);

                            } else sender.sendMessage(getMsgPrefix() + "Your bid needs to be higher than " +
                                    ChatColor.GREEN + "$" + (auctionMinBid + auctionCurrentBid));
                        else sender.sendMessage(getMsgPrefix() + "Your bid needs to be higher than " +
                                    ChatColor.GREEN + "$" + auctionReserve + ChatColor.RESET + " reserve!");
                        else sender.sendMessage(getMsgPrefix() + "That auction is no longer running!");
                    else sender.sendMessage(getMsgPrefix() + "You can't bid on your own auction!");

                } else sender.sendMessage(getMsgPrefix() + "That auction doesn't exist!");
            } else sender.sendMessage(getMsgPrefix() + ChatColor.RED + "You don't have enough money!");

        } else sender.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "/abid <name> <amount>");

    }

}
