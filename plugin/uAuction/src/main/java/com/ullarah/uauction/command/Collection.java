package com.ullarah.uauction.command;

import com.google.common.io.Files;
import com.ullarah.uauction.Init;
import com.ullarah.ulib.function.PlayerProfile;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.ullarah.uauction.Init.*;
import static com.ullarah.uauction.command.Bidding.currentBid;

public class Collection {

    public static void collectBox(final CommandSender sender, String[] args) {

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

            final File auctionBoxFile = new File(getPlugin().getDataFolder() + File.separator + "auction", auctionBoxName + ".yml");

            if (auctionBoxFile.exists()) {

                final Player player = (Player) sender;
                final ArrayList<ItemStack> boxItemStack = new ArrayList<>();

                final FileConfiguration auctionBox = YamlConfiguration.loadConfiguration(auctionBoxFile);

                Boolean auctionRunning = (Boolean) auctionBox.get("running");
                UUID auctionWinnerId = UUID.fromString((String) auctionBox.get("winnerid"));

                if (!auctionRunning) if (player.getUniqueId().equals(auctionWinnerId)) {

                    Inventory auctionBoxContents = Bukkit.createInventory(null, 54,
                            ChatColor.DARK_BLUE + "Auction: " + ChatColor.DARK_GREEN + auctionBoxName);

                    if (auctionBox.get("item") != null) {

                        for (Object inboxCurrentItem : auctionBox.getList("item"))
                            boxItemStack.add((ItemStack) inboxCurrentItem);

                        auctionBoxContents.setContents(boxItemStack.toArray(new ItemStack[boxItemStack.size()]));
                    }

                    player.openInventory(auctionBoxContents);

                } else sender.sendMessage(getMsgPrefix() + ChatColor.RED + "You did not win this auction!");
                else {
                    sender.sendMessage(getMsgPrefix() + "This auction is still running!");
                    currentBid(sender, new String[]{auctionBoxName});
                }

            } else sender.sendMessage(getMsgPrefix() + "That auction doesn't exist!");
        }

    }

    public static void giveBox(final CommandSender sender, String[] args) throws Exception {

        if (sender.hasPermission("uauction.give") || !(sender instanceof Player)) if (args.length == 3) {
            final String auctionBoxName = args[2].toLowerCase();
            final String auctionBoxReceiver = args[1];

            final UUID receiverUUID = new PlayerProfile().lookup(auctionBoxReceiver).getId();

            if (Bukkit.getPlayer(receiverUUID).isOnline()) {

                File auctionBoxFileReal = new File(getPlugin().getDataFolder() + File.separator + "auction", auctionBoxName + ".yml");
                File auctionBoxFileCopy = new File(getPlugin().getDataFolder() + File.separator + "auction", auctionBoxName + ".temp.yml");
                Files.copy(auctionBoxFileReal, auctionBoxFileCopy);

                if (auctionBoxFileCopy.exists()) {

                    final Player player = Bukkit.getPlayer(receiverUUID);
                    final ArrayList<ItemStack> boxItemStack = new ArrayList<>();

                    final FileConfiguration auctionBox = YamlConfiguration.loadConfiguration(auctionBoxFileCopy);

                    Inventory auctionBoxContents = Bukkit.createInventory(null, 54,
                            ChatColor.DARK_BLUE + "Auction: " + ChatColor.GOLD + auctionBoxName);

                    if (auctionBox.get("item") != null) {

                        for (Object inboxCurrentItem : auctionBox.getList("item"))
                            boxItemStack.add((ItemStack) inboxCurrentItem);

                        auctionBoxContents.setContents(boxItemStack.toArray(new ItemStack[boxItemStack.size()]));
                    }

                    player.openInventory(auctionBoxContents);

                } else sender.sendMessage(getMsgPrefix() + "That auction doesn't exist!");

            } else sender.sendMessage(getMsgPrefix() + "That player is not online!");

        } else sender.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "/auction give <player> <name>");
        else sender.sendMessage(Init.getMsgPermDeny());
    }

    public static void hasWon(final CommandSender sender) {

        final Player winner = (Player) sender;

        final List<String> auctions = new ArrayList<>();

        for (Map.Entry<String, UUID> entry : winnerAlerts.entrySet())
            if (winner.getUniqueId().equals(entry.getValue())) auctions.add(entry.getKey());

        if (auctions.size() > 0) {

            String auctionText = " auction!";

            if (auctions.size() > 1) auctionText = " auctions!";

            final String finalAuctionText = auctionText;

            Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(getPlugin(),
                    () -> winner.sendMessage(new String[]{
                            getMsgPrefix() + "You have won " + ChatColor.LIGHT_PURPLE +
                                    auctions.size() + ChatColor.RESET + finalAuctionText,
                            getMsgPrefix() + ChatColor.GREEN + StringUtils.join(auctions, ","),
                            getMsgPrefix() + "Use '" + ChatColor.YELLOW + "/acollect auctionname" +
                                    ChatColor.RESET + "' to collect them."
                    }), 40);

        }

    }

}
