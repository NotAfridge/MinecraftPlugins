package com.ullarah.uchest.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

import static com.ullarah.uchest.ChestFunctions.createItemStack;
import static com.ullarah.uchest.ChestFunctions.validCommands;
import static com.ullarah.uchest.ChestInit.*;

public class ChestMenu {

    private static void showChestMenu(CommandSender sender) {

        Player player = (Player) sender;
        Inventory chestGUI = Bukkit.getServer().createInventory(
                null, 9, "" + ChatColor.GOLD + ChatColor.BOLD + "Mixed Chests");

        String menuColour = "" + ChatColor.GREEN + ChatColor.BOLD;

        chestGUI.setItem(0, createItemStack(Material.JUKEBOX, menuColour + "Donation Chest", Arrays.asList(
                        ChatColor.WHITE + "Opens the donation chest.",
                        ChatColor.WHITE + "Give what you can, take what you need!",
                        ChatColor.RESET + "",
                        ChatColor.RED + "This chest is player supported!")
        ));

        chestGUI.setItem(1, createItemStack(Material.CHEST, menuColour + "Hold Chest", Arrays.asList(
                        ChatColor.YELLOW + "[ Minimum of " + holdingAccessLevel + " levels to use! ]",
                        ChatColor.RESET + "",
                        ChatColor.WHITE + "Opens your personal hold chest.",
                        ChatColor.WHITE + "Store more items on the go!",
                        ChatColor.RESET + "",
                        ChatColor.RED + "Chest will reset back to 9 slots,",
                        ChatColor.RED + "and drop it's contents on death!",
                        ChatColor.RESET + "",
                        ChatColor.GREEN + "Hold chest will upgrade",
                        ChatColor.GREEN + "automatically as you gain XP!",
                        ChatColor.RESET + "",
                        "" + ChatColor.AQUA + ChatColor.ITALIC + "Upgrade Levels: 15, 25, 50, 75, 100")
        ));

        chestGUI.setItem(2, createItemStack(Material.EMERALD_BLOCK, menuColour + "Money Chest", Arrays.asList(
                        ChatColor.YELLOW + "[ Minimum of " + chestAccessLevel + " levels to use! ]",
                        ChatColor.RESET + "",
                        ChatColor.WHITE + "Opens the money chest.",
                        ChatColor.WHITE + "Allows you to convert items to money!",
                        ChatColor.RESET + "",
                        ChatColor.RED + "Some items do not return money!")
        ));

        chestGUI.setItem(3, createItemStack(Material.SPONGE, menuColour + "Random Chest", Arrays.asList(
                        ChatColor.YELLOW + "[ Minimum of " + chestAccessLevel + " levels to use! ]",
                        ChatColor.RESET + "",
                        ChatColor.WHITE + "Opens the random chest.",
                        ChatColor.WHITE + "Random items in random slots,",
                        ChatColor.WHITE + "at short random intervals!",
                        ChatColor.RESET + "",
                        ChatColor.RED + "You have to be quick to grab them!",
                        ChatColor.RED + "XP will be removed the more you stay!")
        ));

        chestGUI.setItem(4, createItemStack(Material.GLASS, menuColour + "Swap Chest", Arrays.asList(
                        ChatColor.WHITE + "Opens the swapping chest.",
                        ChatColor.WHITE + "Put random items in, get random items out!",
                        ChatColor.RESET + "",
                        ChatColor.RED + "This chest is player supported!")
        ));

        chestGUI.setItem(5, createItemStack(Material.ENDER_CHEST, menuColour + "Vault Chest", Arrays.asList(
                        ChatColor.YELLOW + "[ Will remove " + holdingAccessLevel + " levels to open! ]",
                        ChatColor.RESET + "",
                        ChatColor.WHITE + "Opens your personal vault chest.",
                        ChatColor.WHITE + "Safely store your items away at a price!",
                        ChatColor.RESET + "",
                        ChatColor.DARK_AQUA + "Upgrade your vault using " + ChatColor.AQUA + "/vchest upgrade")
        ));

        chestGUI.setItem(6, createItemStack(Material.SEA_LANTERN, menuColour + "Experience Chest", Arrays.asList(
                        ChatColor.YELLOW + "[ Minimum of " + chestAccessLevel + " levels to use! ]",
                        ChatColor.RESET + "",
                        ChatColor.WHITE + "Opens the experience chest.",
                        ChatColor.WHITE + "Allows you to convert items to XP!",
                        ChatColor.RESET + "",
                        ChatColor.RED + "Some items do not return XP!")
        ));

        player.openInventory(chestGUI);

    }

    public void runCommand(CommandSender sender, String[] args) {

        String consoleTools = getMsgPrefix() + ChatColor.WHITE + "maintenance | toggle";

        if (args.length == 0) if (!(sender instanceof Player))
            sender.sendMessage(consoleTools);
        else if (!getMaintenanceCheck())
            showChestMenu(sender);
        else
            sender.sendMessage(getMaintenanceMessage());

        else try {

            switch (validCommands.valueOf(args[0].toUpperCase())) {

                case HELP:
                    if (!(sender instanceof Player))
                        sender.sendMessage(getMsgNoConsole());
                    else
                        DisplayHelp.runHelp(sender);
                    break;

                case MAINTENANCE:
                    Maintenance.runMaintenance(sender, args);
                    break;

                case TOGGLE:
                    ToggleAccess.toggleChestAccess(sender, args);
                    break;

                default:
                    if (!(sender instanceof Player))
                        sender.sendMessage(consoleTools);
                    else
                        showChestMenu(sender);


            }

        } catch (IllegalArgumentException e) {

            if (!(sender instanceof Player))
                sender.sendMessage(consoleTools);
            else
                DisplayHelp.runHelp(sender);

        }

    }

}
