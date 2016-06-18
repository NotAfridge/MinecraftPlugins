package com.ullarah.uchest.command;

import com.ullarah.uchest.ChestFunctions;
import com.ullarah.uchest.function.CommonString;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ullarah.uchest.ChestFunctions.validCommands;
import static com.ullarah.uchest.ChestInit.getPlugin;

public class ChestMenu {

    private void showChestMenu(CommandSender sender) {

        ChestFunctions chestFunctions = new ChestFunctions();

        Player player = (Player) sender;
        Inventory chestGUI = Bukkit.getServer().createInventory(null, 9, "" + ChatColor.GOLD + ChatColor.BOLD + "Mixed Chests");
        String menuColour = "" + ChatColor.GREEN + ChatColor.BOLD;

        chestGUI.setItem(0, chestFunctions.createItemStack(Material.JUKEBOX,
                menuColour + "Donation Chest", Arrays.asList(
                        accessLevelRequirement("dchest"),
                        ChatColor.RESET + "",
                        ChatColor.WHITE + "Opens the donation chest.",
                        ChatColor.WHITE + "Give what you can, take what you need!",
                        ChatColor.RESET + "",
                        ChatColor.RED + "This chest is player supported!")
        ));

        chestGUI.setItem(1, chestFunctions.createItemStack(Material.WORKBENCH,
                menuColour + "Enchantment Chest", Arrays.asList(
                        accessLevelRequirement("echest"),
                        ChatColor.RESET + "",
                        ChatColor.WHITE + "Opens the enchantment chest.",
                        ChatColor.WHITE + "Enchant weapons or armour with a",
                        ChatColor.WHITE + "completely random enchantment!",
                        ChatColor.RESET + "",
                        ChatColor.RED + "Not all items can be enchanted!")
        ));

        List<String> holdChestMessage = new ArrayList<>();

        List<String> holdChestStartMessage = Arrays.asList(
                accessLevelRequirement("hchest"),
                ChatColor.RESET + "",
                ChatColor.WHITE + "Opens your personal hold chest.",
                ChatColor.WHITE + "Store more items on the go!"
        );

        List<String> holdChestDeathMessage = Arrays.asList(
                ChatColor.RESET + "",
                ChatColor.RED + "Chest will reset back to 9 slots,",
                ChatColor.RED + "and drop it's contents on death!"
        );

        List<String> holdChestEndMessage = Arrays.asList(
                ChatColor.RESET + "",
                ChatColor.GREEN + "Hold chest will upgrade",
                ChatColor.GREEN + "automatically as you gain XP!",
                ChatColor.RESET + "",
                "" + ChatColor.AQUA + ChatColor.ITALIC + "Upgrade Levels: 15, 25, 50, 75, 100"
        );

        holdChestMessage.addAll(holdChestStartMessage);
        if (getPlugin().getConfig().getBoolean("hchest.keepondeath")) holdChestMessage.addAll(holdChestDeathMessage);
        holdChestMessage.addAll(holdChestEndMessage);

        chestGUI.setItem(2, chestFunctions.createItemStack(Material.CHEST,
                menuColour + "Hold Chest", holdChestMessage
        ));

        chestGUI.setItem(3, chestFunctions.createItemStack(Material.EMERALD_BLOCK,
                menuColour + "Money Chest", Arrays.asList(
                        accessLevelRequirement("mchest"),
                        ChatColor.RESET + "",
                        ChatColor.WHITE + "Opens the money chest.",
                        ChatColor.WHITE + "Allows you to convert items to money!",
                        ChatColor.RESET + "",
                        ChatColor.RED + "Some items do not return money!")
        ));

        int rChestTimer = getPlugin().getConfig().getInt("rchest.timer");

        chestGUI.setItem(4, chestFunctions.createItemStack(Material.SPONGE,
                menuColour + "Random Chest", Arrays.asList(
                        accessLevelRequirement("rchest"),
                        ChatColor.RESET + "",
                        ChatColor.WHITE + "Opens the random chest.",
                        ChatColor.WHITE + "Random items in random slots,",
                        ChatColor.WHITE + "at short random intervals!",
                        ChatColor.RESET + "",
                        ChatColor.RED + "You have to be quick to grab them!",
                        ChatColor.RED + "You have " + ChatColor.YELLOW + rChestTimer + " seconds" + ChatColor.RED + "!")
        ));

        chestGUI.setItem(5, chestFunctions.createItemStack(Material.GLASS,
                menuColour + "Swap Chest", Arrays.asList(
                        accessLevelRequirement("schest"),
                        ChatColor.RESET + "",
                        ChatColor.WHITE + "Opens the swapping chest.",
                        ChatColor.WHITE + "Put random items in, get random items out!",
                        ChatColor.RESET + "",
                        ChatColor.RED + "This chest is player supported!")
        ));

        chestGUI.setItem(6, chestFunctions.createItemStack(Material.ENDER_CHEST,
                menuColour + "Vault Chest", Arrays.asList(
                        accessLevelRequirement("vchest"),
                        ChatColor.RESET + "",
                        ChatColor.WHITE + "Opens your personal vault chest.",
                        ChatColor.WHITE + "Safely store your items away at a price!",
                        ChatColor.RESET + "",
                        ChatColor.DARK_AQUA + "Upgrade your vault using " + ChatColor.AQUA + "/vchest upgrade")
        ));

        chestGUI.setItem(7, chestFunctions.createItemStack(Material.SEA_LANTERN,
                menuColour + "Experience Chest", Arrays.asList(
                        accessLevelRequirement("xchest"),
                        ChatColor.RESET + "",
                        ChatColor.WHITE + "Opens the experience chest.",
                        ChatColor.WHITE + "Allows you to convert items to XP!",
                        ChatColor.RESET + "",
                        ChatColor.RED + "Some items do not return XP!")
        ));

        player.openInventory(chestGUI);

    }

    private String accessLevelRequirement(String type) {

        int accessLevel = getPlugin().getConfig().getInt(type + ".access");
        boolean accessRemoveLevel = getPlugin().getConfig().getBoolean(type + ".removelevel");

        if (accessLevel > 0) {
            String s = accessLevel > 1 ? "s" : "";
            if (accessRemoveLevel) return ChatColor.YELLOW + "[ Will remove " + accessLevel + " level" + s + " ]";
            else return ChatColor.YELLOW + "[ Minimum of " + accessLevel + " level" + s + " ]";
        } else return ChatColor.YELLOW + "[ No requirements ]";

    }

    public void runCommand(CommandSender sender, String[] args) {

        CommonString commonString = new CommonString();

        String consoleTools = commonString.pluginPrefix(getPlugin()) + ChatColor.WHITE + "toggle";

        if (args.length == 0) {

            if (!(sender instanceof Player)) {
                sender.sendMessage(consoleTools);
                return;
            }

            showChestMenu(sender);

        } else try {

            switch (validCommands.valueOf(args[0].toUpperCase())) {

                case HELP:
                    if (!(sender instanceof Player)) commonString.messageNoConsole(getPlugin(), sender);
                    else new DisplayHelp().runHelp(sender);
                    break;

                case TOGGLE:
                    new ToggleAccess().toggleChestAccess(sender, args);
                    break;

                default:
                    if (!(sender instanceof Player)) sender.sendMessage(consoleTools);
                    else showChestMenu(sender);


            }

        } catch (IllegalArgumentException e) {

            if (!(sender instanceof Player))
                sender.sendMessage(consoleTools);
            else
                new DisplayHelp().runHelp(sender);

        }

    }

}
