package com.ullarah.uauction.command;

import com.ullarah.uauction.Init;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

import static com.ullarah.uauction.Init.*;

public class Purge {

    public static void removeAllAuctions(CommandSender sender) {

        if (sender.hasPermission("uauction.purge") || !(sender instanceof Player)) {

            File auctionBoxFolder = new File(getPlugin().getDataFolder() + File.separator + "auction");
            File[] listOfFiles = auctionBoxFolder.listFiles();
            Boolean delSuccess = true;
            String currentFile = "";

            if (listOfFiles != null) {
                for (File listOfFile : listOfFiles)
                    if (listOfFile.isFile()) {
                        currentFile = listOfFile.getName();
                        delSuccess = listOfFile.delete();
                        if (!delSuccess) break;
                    }
                if (!delSuccess) sender.sendMessage(getMsgPrefix() + ChatColor.RED +
                        "Error removing " + ChatColor.GOLD + currentFile);
            } else sender.sendMessage(getMsgPrefix() + "No auctions to remove.");

            Bukkit.broadcast(Init.getMsgPrefix() + ChatColor.RED + "All auctions have been removed", "uauction.staff");

        } else sender.sendMessage(Init.getMsgPermDeny());

    }

    public static void removeSingleAuction(CommandSender sender, String[] args) {

        if (sender.hasPermission("uauction.delete") || !(sender instanceof Player)) if (args.length == 2) {

            String auctionBoxName = args[1].toLowerCase();

            final File auctionBoxFile = new File(getPlugin().getDataFolder() + File.separator + "auction", auctionBoxName + ".yml");

            if (auctionBoxFile.exists()) {

                Boolean delSuccess = auctionBoxFile.delete();

                if (winnerAlerts.containsKey(auctionBoxName)) winnerAlerts.remove(auctionBoxName);
                if (activeAuctions.contains(auctionBoxName)) activeAuctions.remove(auctionBoxName);

                if (delSuccess)
                    Bukkit.broadcast(Init.getMsgPrefix() + ChatColor.GOLD + auctionBoxName + ChatColor.RESET +
                            " auction has been removed", "uauction.staff");
                else sender.sendMessage(getMsgPrefix() + ChatColor.RED +
                        "Error removing " + ChatColor.GOLD + auctionBoxName);

            } else sender.sendMessage(getMsgPrefix() + "That auction doesn't exist!");

        } else sender.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "/auction delete <auctionname>");
        else sender.sendMessage(Init.getMsgPermDeny());

    }

}

