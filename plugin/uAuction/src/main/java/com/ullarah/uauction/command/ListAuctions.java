package com.ullarah.uauction.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.ullarah.uauction.Init.activeAuctions;
import static com.ullarah.uauction.Init.getMsgPrefix;

public class ListAuctions {

    public static void auctionList(CommandSender sender, Integer page) {

        if (page == 0) page = 1;

        if (String.valueOf(page).matches("[0-9]+")) {

            SortedMap<Integer, String> map = new TreeMap<>();

            for (String activeAuction : activeAuctions) {
                map.put(activeAuctions.indexOf(activeAuction), activeAuction);
            }

            paginate(sender, map, page);

        } else sender.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "/alist [page]");

    }

    private static void paginate(CommandSender sender, SortedMap<Integer, String> map, int page) {

        int totalPage = (((map.size() % 6) == 0) ? map.size() / 6 : (map.size() / 6) + 1);

        if (page <= totalPage) {

            sender.sendMessage(ChatColor.YELLOW + "Auctions: Page [" + String.valueOf(page) + " of " + totalPage + "]");

            int i = 0;
            int k = 0;

            page--;

            for (final Map.Entry<Integer, String> e : map.entrySet()) {

                k++;

                if ((((page * 6) + i + 1) == k) && (k != ((page * 6) + 6 + 1))) {

                    i++;

                    sender.sendMessage(ChatColor.YELLOW + " ▪ " + e.getValue().toLowerCase());

                }

            }

        } else {

            sender.sendMessage(getMsgPrefix() + "There are only " + ChatColor.YELLOW + totalPage + " pages of auctions.");

        }


    }

}

