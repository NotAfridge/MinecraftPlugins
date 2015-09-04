package com.ullarah.uauction.command;

import com.ullarah.uauction.Init;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import static com.ullarah.uauction.Init.*;

public class Creation {

    public static void viewAuctionBox(CommandSender sender, String[] args) {

        if (args.length == 1 || args.length == 2) {
            String auctionBoxName = args.length == 1 ? args[0] : args[1];
            if (!getMaintenanceCheck()) {

                File auctionBoxFile = new File(getPlugin().getDataFolder() + File.separator + "auction",
                        auctionBoxName.toLowerCase() + ".yml");

                if (auctionBoxFile.exists()) {

                    FileConfiguration auctionBox = YamlConfiguration.loadConfiguration(auctionBoxFile);

                    Boolean auctionBoxSilent = (Boolean) auctionBox.get("silent");
                    Boolean auctionRunning = (Boolean) auctionBox.get("running");

                    if (auctionRunning) if (auctionBoxSilent)
                        sender.sendMessage(getMsgPrefix() + "This is a silent auction! You cannot view the chest!");
                    else showAuctionBox(auctionBox, auctionBoxName.toLowerCase(), sender);
                    else sender.sendMessage(getMsgPrefix() + "That auction is no longer running!");

                } else sender.sendMessage(getMsgPrefix() + "That auction doesn't exist!");

            } else sender.sendMessage(getMaintenanceMessage());
        } else {
            sender.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "/auction view <name>");
            sender.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "/aview <name>");
        }

    }

    public static void editAuctionBox(CommandSender sender, String[] args) {

        if (sender.hasPermission("uauction.edit") || !(sender instanceof Player)) if (args.length == 2) {

            String auctionBoxName = args[1].toLowerCase();
            File auctionBoxFile = new File(getPlugin().getDataFolder() + File.separator + "auction", auctionBoxName + ".yml");

            if (auctionBoxFile.exists()) {

                Player player;
                if (sender instanceof Player) {

                    player = (Player) sender;
                    FileConfiguration auctionBox = YamlConfiguration.loadConfiguration(auctionBoxFile);

                    Boolean auctionRunning = (Boolean) auctionBox.get("running");
                    String auctionCreator = (String) auctionBox.get("creator");
                    UUID auctionCreatorId = UUID.fromString((String) auctionBox.get("creatorid"));

                    if (player.getUniqueId().equals(auctionCreatorId) || player.hasPermission("uauction.forceedit"))
                        if (!auctionRunning) showAuctionBox(auctionBox, auctionBoxName, sender);
                        else sender.sendMessage(getMsgPrefix() + ChatColor.RED + "Auction is currently running!");
                    else sender.sendMessage(getMsgPrefix() + ChatColor.LIGHT_PURPLE + auctionCreator +
                            ChatColor.RED + " owns this auction. You cannot edit it.");

                } else sender.sendMessage(getMsgNoConsole());

            }

        } else sender.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "/auction edit <name>");
        else sender.sendMessage(Init.getMsgPermDeny());

    }

    private static void showAuctionBox(FileConfiguration auctionBox, String auctionBoxName, CommandSender auctionBoxViewer) {

        if ((Boolean) auctionBox.get("running") || auctionBoxViewer.hasPermission("uauction.edit")) {
            Inventory auctionBoxContents = Bukkit.createInventory(null, 54,
                    ChatColor.DARK_BLUE + "Auction: " + ChatColor.DARK_AQUA + auctionBoxName);

            if (auctionBox.get("item") != null) {
                ArrayList<ItemStack> boxItemStack = new ArrayList<>();

                for (Object inboxCurrentItem : auctionBox.getList("item"))
                    boxItemStack.add((ItemStack) inboxCurrentItem);

                auctionBoxContents.setContents(boxItemStack.toArray(new ItemStack[boxItemStack.size()]));
            }

            Player auctionBoxPlayer = (Player) auctionBoxViewer;
            auctionBoxPlayer.openInventory(auctionBoxContents);
        } else auctionBoxViewer.sendMessage(getMsgPrefix() + "That auction is no longer running!");

    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void createAuctionBox(CommandSender sender, String[] args) throws IOException {

        if (sender.hasPermission("uauction.create")) if (args.length >= 2) {
            String auctionBoxName = args[1].toLowerCase();
            Integer auctionBoxTime = Init.getPlugin().getConfig().getInt("time");
            Double auctionBoxReserve = Init.getPlugin().getConfig().getDouble("reserve");
            Double auctionBoxMinBid = Init.getPlugin().getConfig().getDouble("minbid");
            Boolean auctionIsSilent = false;

            switch (args.length) {
                case 3:
                    auctionBoxTime = Integer.parseInt(args[2]);
                    break;
                case 4:
                    auctionBoxTime = Integer.parseInt(args[2]);
                    auctionBoxReserve = Double.parseDouble(args[3]);
                    break;
                case 5:
                    auctionBoxTime = Integer.parseInt(args[2]);
                    auctionBoxReserve = Double.parseDouble(args[3]);
                    auctionBoxMinBid = Double.parseDouble(args[4]);
                    break;
                case 6:
                    auctionBoxTime = Integer.parseInt(args[2]);
                    auctionBoxReserve = Double.parseDouble(args[3]);
                    auctionBoxMinBid = Double.parseDouble(args[4]);
                    if (args[5].toLowerCase().equals("true")) auctionIsSilent = true;
                    break;
            }

            if (auctionBoxName.length() <= 16 && auctionBoxName.matches("[a-zA-Z0-9_-]+")) {
                Player player = (Player) sender;

                File auctionBoxFile = new File(getPlugin().getDataFolder() + File.separator + "auction", auctionBoxName + ".yml");

                if (!auctionBoxFile.exists()) {

                    File dataDir = getPlugin().getDataFolder();
                    if (!dataDir.exists()) dataDir.mkdir();

                    File auctionDir = new File(dataDir + File.separator + "auction");
                    if (!auctionDir.exists()) auctionDir.mkdir();

                    File auctionBoxFileNew = new File(auctionDir, auctionBoxName + ".yml");
                    if (!auctionBoxFileNew.exists()) auctionBoxFileNew.createNewFile();

                    FileConfiguration auctionBox = YamlConfiguration.loadConfiguration(auctionBoxFileNew);

                    auctionBox.set("name", auctionBoxName);
                    auctionBox.set("creator", player.getName());
                    auctionBox.set("creatorid", player.getUniqueId().toString());
                    auctionBox.set("silent", auctionIsSilent);
                    auctionBox.set("reserve", auctionBoxReserve);
                    auctionBox.set("minbid", auctionBoxMinBid);
                    auctionBox.set("bid", 0.0);
                    auctionBox.set("winner", "nobody");
                    auctionBox.set("winnerid", "00000000-0000-0000-0000-000000000000");
                    auctionBox.set("running", false);
                    auctionBox.set("collected", false);
                    auctionBox.set("time", auctionBoxTime);
                    auctionBox.set("ended", false);
                    auctionBox.set("item", new ArrayList<>());

                    auctionBox.save(auctionBoxFileNew);

                    showAuctionBox(auctionBox, auctionBoxName, player);

                } else
                    sender.sendMessage(getMsgPrefix() + ChatColor.RED + "An auction of that name already exists!");
            } else
                sender.sendMessage(getMsgPrefix() + ChatColor.RED + "Auction name must be 16 characters or less!");
        } else
            sender.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "/auction create <name> [hours] [reserve] [minbid] [silent]");
        else sender.sendMessage(Init.getMsgPermDeny());

    }

    public static void openAuctionBox(CommandSender sender, String[] args) throws IOException {

        if (sender.hasPermission("uauction.open")) if (args.length >= 2) {

            String auctionBoxName = args[1].toLowerCase();
            Boolean auctionBoxBroadcast = true;
            if (args.length == 3) auctionBoxBroadcast = Boolean.parseBoolean(args[2]);

            File auctionBoxFile = new File(getPlugin().getDataFolder() + File.separator + "auction", auctionBoxName + ".yml");

            if (auctionBoxFile.exists()) {

                FileConfiguration auctionBox = YamlConfiguration.loadConfiguration(auctionBoxFile);

                Boolean auctionRunning = (Boolean) auctionBox.get("running");
                String auctionCreator = (String) auctionBox.get("creator");
                UUID auctionCreatorId = UUID.fromString((String) auctionBox.get("creatorid"));

                Player player;
                if (sender instanceof Player) {

                    player = (Player) sender;

                    if (player.getUniqueId().equals(auctionCreatorId) || player.hasPermission("uauction.forceopen"))

                        if (!auctionRunning) {

                            Init.activeAuctions.add(auctionBoxName);
                            auctionBox.set("running", true);
                            sender.sendMessage(getMsgPrefix() + ChatColor.GOLD + auctionBoxName + ChatColor.RESET + " is now open.");

                            if (auctionBoxBroadcast)
                                Bukkit.broadcastMessage(getMsgPrefix() + ChatColor.GREEN + "Auction " +
                                        ChatColor.GOLD + auctionBoxName + ChatColor.GREEN + " is now open for bidding!");

                            auctionBox.save(auctionBoxFile);

                        } else sender.sendMessage(getMsgPrefix() + ChatColor.RED + "Auction is already running!");

                    else sender.sendMessage(getMsgPrefix() + ChatColor.LIGHT_PURPLE + auctionCreator +
                            ChatColor.RED + " owns this auction. You cannot open it.");

                } else {

                    Init.activeAuctions.add(auctionBoxName);
                    auctionBox.set("running", true);
                    sender.sendMessage(getMsgPrefix() + ChatColor.GOLD + auctionBoxName + ChatColor.RESET + " is now open.");

                    if (auctionBoxBroadcast)
                        Bukkit.broadcastMessage(getMsgPrefix() + ChatColor.GREEN + "Auction " +
                                ChatColor.GOLD + auctionBoxName + ChatColor.GREEN + " is now open for bidding!");

                    auctionBox.save(auctionBoxFile);

                }

            }

        } else sender.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "/auction open <name> [broadcast]");
        else sender.sendMessage(Init.getMsgPermDeny());

    }

    public static void closeAuctionBox(CommandSender sender, String[] args) throws IOException {

        if (sender.hasPermission("uauction.close")) if (args.length == 2) {

            String auctionBoxName = args[1].toLowerCase();
            File auctionBoxFile = new File(getPlugin().getDataFolder() + File.separator + "auction", auctionBoxName + ".yml");

            if (auctionBoxFile.exists()) {

                FileConfiguration auctionBox = YamlConfiguration.loadConfiguration(auctionBoxFile);

                Boolean auctionRunning = (Boolean) auctionBox.get("running");
                String auctionCreator = (String) auctionBox.get("creator");
                UUID auctionCreatorId = UUID.fromString((String) auctionBox.get("creatorid"));

                Player player;
                if (sender instanceof Player) {

                    player = (Player) sender;

                    if (player.getUniqueId().equals(auctionCreatorId) || player.hasPermission("uauction.forceclose"))

                        if (auctionRunning) {

                            Init.activeAuctions.remove(auctionBoxName);
                            auctionBox.set("running", false);
                            sender.sendMessage(getMsgPrefix() + ChatColor.GOLD + auctionBoxName + ChatColor.RESET + " is now closed.");
                            auctionBox.save(auctionBoxFile);

                        } else sender.sendMessage(getMsgPrefix() + ChatColor.RED + "Auction is already closed!");

                    else sender.sendMessage(getMsgPrefix() + ChatColor.LIGHT_PURPLE + auctionCreator +
                            ChatColor.RED + " owns this auction. You cannot close it.");

                } else {

                    Init.activeAuctions.remove(auctionBoxName);
                    auctionBox.set("running", false);
                    sender.sendMessage(getMsgPrefix() + ChatColor.GOLD + auctionBoxName + ChatColor.RESET + " is now closed.");
                    auctionBox.save(auctionBoxFile);

                }

            }

        } else sender.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "/auction close <name>");
        else sender.sendMessage(Init.getMsgPermDeny());

    }

}
