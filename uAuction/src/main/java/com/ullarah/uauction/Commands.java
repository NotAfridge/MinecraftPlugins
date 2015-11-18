package com.ullarah.uauction;

import com.ullarah.uauction.command.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

import static com.ullarah.uauction.Init.*;

public class Commands implements CommandExecutor {

    private final String consolSingleCommandMessage = getMsgPrefix() + " Please use /auction";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (command.getName().equalsIgnoreCase("auction")) commandAuction(sender, args);
        if (command.getName().equalsIgnoreCase("alist")) commandAuctionList(sender, args);
        if (command.getName().equalsIgnoreCase("abid")) commandAuctionBid(sender, args);
        if (command.getName().equalsIgnoreCase("aview")) commandAuctionView(sender, args);
        if (command.getName().equalsIgnoreCase("ainfo")) commandAuctionInfo(sender, args);
        if (command.getName().equalsIgnoreCase("acollect")) commandAuctionCollect(sender, args);
        if (command.getName().equalsIgnoreCase("achat")) commandAuctionChat(sender);
        if (command.getName().equalsIgnoreCase("awon")) commandAuctionWon(sender);

        return true;

    }

    private void commandAuction(CommandSender sender, String[] args) {

        String consoleToolMessage = getMsgPrefix() + ChatColor.WHITE + "maintenance | list | give | open | close | purge";

        if (args.length == 0) if (!(sender instanceof Player))
            sender.sendMessage(consoleToolMessage);
        else if (!getMaintenanceCheck())
            Help.runHelp(sender);
        else
            sender.sendMessage(getMaintenanceMessage());

        else try {

            switch (validCommands.valueOf(args[0].toUpperCase())) {

                case HELP:
                    if (!(sender instanceof Player))
                        sender.sendMessage(getMsgNoConsole());
                    else
                        Help.runHelp(sender);
                    break;

                case MAINTENANCE:
                    Maintenance.runMaintenance(sender, args);
                    break;

                case CREATE:
                    if (!getMaintenanceCheck())
                        Creation.createAuctionBox(sender, args);
                    else
                        sender.sendMessage(getMaintenanceMessage());
                    break;

                case EDIT:
                    if (!getMaintenanceCheck())
                        Creation.editAuctionBox(sender, args);
                    else
                        sender.sendMessage(getMaintenanceMessage());
                    break;

                case CLOSE:
                    if (!getMaintenanceCheck())
                        Creation.closeAuctionBox(sender, args);
                    else
                        sender.sendMessage(getMaintenanceMessage());
                    break;

                case OPEN:
                    if (!getMaintenanceCheck())
                        Creation.openAuctionBox(sender, args);
                    else
                        sender.sendMessage(getMaintenanceMessage());
                    break;

                case LIST:
                    if (!getMaintenanceCheck())
                        commandAuctionList(sender, args);
                    else
                        sender.sendMessage(getMaintenanceMessage());
                    break;

                case BID:
                    if (!getMaintenanceCheck())
                        commandAuctionBid(sender, args);
                    else
                        sender.sendMessage(getMaintenanceMessage());
                    break;

                case VIEW:
                    if (!getMaintenanceCheck())
                        commandAuctionView(sender, args);
                    else
                        sender.sendMessage(getMaintenanceMessage());
                    break;

                case COLLECT:
                    if (!getMaintenanceCheck())
                        commandAuctionCollect(sender, args);
                    else
                        sender.sendMessage(getMaintenanceMessage());
                    break;

                case GIVE:
                    if (!getMaintenanceCheck())
                        Collection.giveBox(sender, args);
                    else
                        sender.sendMessage(getMaintenanceMessage());
                    break;

                case INFO:
                    if (!getMaintenanceCheck())
                        commandAuctionInfo(sender, args);
                    else
                        sender.sendMessage(getMaintenanceMessage());
                    break;

                case PURGE:
                    if (!getMaintenanceCheck())
                        Purge.removeAllAuctions(sender);
                    else
                        sender.sendMessage(getMaintenanceMessage());
                    break;

                case DELETE:
                    if (!getMaintenanceCheck())
                        Purge.removeSingleAuction(sender, args);
                    else
                        sender.sendMessage(getMaintenanceMessage());
                    break;

                case CHAT:
                    if (!getMaintenanceCheck())
                        commandAuctionChat(sender);
                    else
                        sender.sendMessage(getMaintenanceMessage());
                    break;

                case WON:
                    if (!getMaintenanceCheck())
                        Collection.hasWon(sender);
                    else
                        sender.sendMessage(getMaintenanceMessage());
                    break;

                default:
                    if (!(sender instanceof Player))
                        sender.sendMessage(consoleToolMessage);
                    else
                        Help.runHelp(sender);
                    break;

            }

        } catch (Exception e) {

            if (!(sender instanceof Player)) sender.sendMessage(consoleToolMessage);
            else Help.runHelp(sender);

        }

    }

    private void commandAuctionList(CommandSender sender, String[] args) {

        if (args.length == 0) if (!(sender instanceof Player)) sender.sendMessage(consolSingleCommandMessage);
        else if (!getMaintenanceCheck())
            ListAuctions.auctionList(sender, 1);
        else sender.sendMessage(getMaintenanceMessage());

        else try {

            ListAuctions.auctionList(sender, args[0].equals("list") ? Integer.valueOf(args[1]) : Integer.valueOf(args[0]));

        } catch (IllegalArgumentException e) {

            sender.sendMessage(!(sender instanceof Player) ?
                    consolSingleCommandMessage : getMsgPrefix() + ChatColor.YELLOW + "/alist [page]");

        }

    }

    private void commandAuctionBid(CommandSender sender, String[] args) {

        if (args.length == 0)
            sender.sendMessage(!(sender instanceof Player) ? consolSingleCommandMessage : !getMaintenanceCheck() ?
                    getMsgPrefix() + ChatColor.YELLOW + "/abid <name> <amount>" : getMaintenanceMessage());

        else try {

            Bidding.auctionBid(sender, args);

        } catch (IllegalArgumentException | IOException e) {

            sender.sendMessage(!(sender instanceof Player) ?
                    consolSingleCommandMessage : getMsgPrefix() + ChatColor.YELLOW + "/abid <name> <amount>");

        }

    }

    private void commandAuctionView(CommandSender sender, String[] args) {

        if (args.length == 0)
            sender.sendMessage(!(sender instanceof Player) ? consolSingleCommandMessage : !getMaintenanceCheck() ?
                    getMsgPrefix() + ChatColor.YELLOW + "/aview <name>" : getMaintenanceMessage());

        else try {

            Creation.viewAuctionBox(sender, args);

        } catch (IllegalArgumentException e) {

            sender.sendMessage(!(sender instanceof Player) ?
                    consolSingleCommandMessage : getMsgPrefix() + ChatColor.YELLOW + "/aview <name>");

        }

    }

    private void commandAuctionInfo(CommandSender sender, String[] args) {

        if (args.length == 0)
            sender.sendMessage(!(sender instanceof Player) ? consolSingleCommandMessage : !getMaintenanceCheck() ?
                    getMsgPrefix() + ChatColor.YELLOW + "/ainfo <name>" : getMaintenanceMessage());

        else try {

            Bidding.currentBid(sender, args);

        } catch (IllegalArgumentException e) {

            sender.sendMessage(!(sender instanceof Player) ?
                    consolSingleCommandMessage : getMsgPrefix() + ChatColor.YELLOW + "/ainfo <name>");

        }

    }

    private void commandAuctionCollect(CommandSender sender, String[] args) {

        if (args.length == 0)
            sender.sendMessage(!(sender instanceof Player) ? consolSingleCommandMessage : !getMaintenanceCheck() ?
                    getMsgPrefix() + ChatColor.YELLOW + "/acollect <name>" : getMaintenanceMessage());

        else try {

            Collection.collectBox(sender, args);

        } catch (IllegalArgumentException e) {

            sender.sendMessage(!(sender instanceof Player) ?
                    consolSingleCommandMessage : getMsgPrefix() + ChatColor.YELLOW + "/acollect <name>");

        }

    }

    private void commandAuctionChat(CommandSender sender) {

        if (!getMaintenanceCheck())
            if (!(sender instanceof Player)) sender.sendMessage(consolSingleCommandMessage);
            else {
                Player player = (Player) sender;

                if (chatMembers.contains(player)) {
                    chatMembers.remove(player);
                    sender.sendMessage(getMsgPrefix() + ChatColor.RED + "Auction Chat has been turned off.");
                } else {
                    chatMembers.add(player);
                    sender.sendMessage(getMsgPrefix() + ChatColor.GREEN + "Auction Chat has been turned on.");
                }

            }
        else sender.sendMessage(!(sender instanceof Player) ? consolSingleCommandMessage : getMaintenanceMessage());

    }

    private void commandAuctionWon(CommandSender sender) {

        if (!getMaintenanceCheck())
            if (!(sender instanceof Player)) sender.sendMessage(consolSingleCommandMessage);
            else Collection.hasWon(sender);
        else sender.sendMessage(!(sender instanceof Player) ? consolSingleCommandMessage : getMaintenanceMessage());

    }

    private enum validCommands {
        HELP, MAINTENANCE, CREATE,
        EDIT, CLOSE, OPEN, LIST,
        BID, VIEW, INFO, COLLECT,
        GIVE, PURGE, CHAT, WON,
        DELETE
    }

}

