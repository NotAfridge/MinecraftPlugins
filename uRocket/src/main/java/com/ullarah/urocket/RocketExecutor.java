package com.ullarah.urocket;

import com.ullarah.urocket.command.DisplayComponentChest;
import com.ullarah.urocket.command.DisplayHelp;
import com.ullarah.urocket.function.CommonString;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.ullarah.urocket.RocketInit.getPlugin;

class RocketExecutor implements CommandExecutor {

    private final CommonString commonString = new CommonString();
    private final RocketFunctions rocketFunctions = new RocketFunctions();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (command.getName().equalsIgnoreCase("rocket")) rocketChest(sender, args);
        if (command.getName().equalsIgnoreCase("fuel")) rocketFuel(sender);

        return true;

    }

    private void rocketChest(CommandSender sender, String[] args) {

        DisplayComponentChest displayComponentChest = new DisplayComponentChest();
        DisplayHelp displayHelp = new DisplayHelp();

        if (!(sender instanceof Player)) commonString.messageNoConsole(getPlugin(), sender);
        else {

            if (args.length == 0) displayHelp.display(sender);
            else {

                try {

                    switch (args[0].toUpperCase()) {

                        case "CHEST":
                            if (sender.hasPermission("rocket.chest"))
                                displayComponentChest.open(sender);
                            else
                                displayHelp.display(sender);
                            break;

                        case "FUEL":
                            rocketFuel(sender);
                            break;

                        default:
                            displayHelp.display(sender);
                            break;

                    }

                } catch (IllegalArgumentException e) {

                    displayHelp.display(sender);

                }

            }

        }

    }

    private void rocketFuel(CommandSender sender) {

        if (!(sender instanceof Player)) commonString.messageNoConsole(getPlugin(), sender);
        else {

            Player player = (Player) sender;
            ItemStack fuelJacket = player.getInventory().getChestplate();

            if (fuelJacket == null) {
                commonString.messageSend(getPlugin(), player, true, ChatColor.RED + "Fuel Jacket not found!");
                return;
            }

            if (fuelJacket.hasItemMeta()) {
                if (fuelJacket.getItemMeta().hasDisplayName()) {
                    String fuelJacketMeta = fuelJacket.getItemMeta().getDisplayName();

                    if (fuelJacketMeta.equals(ChatColor.RED + "Rocket Boot Fuel Jacket")) {

                        if (player.isFlying()) {
                            commonString.messageSend(getPlugin(), player, true, ChatColor.RED + "Cannot access Fuel Jacket while flying!");
                            return;
                        }

                        File fuelFile = new File(getPlugin().getDataFolder() + File.separator + "fuel", player.getUniqueId().toString() + ".yml");
                        FileConfiguration fuelConfig = YamlConfiguration.loadConfiguration(fuelFile);

                        if (fuelFile.exists()) {

                            Material jacket = player.getInventory().getChestplate().getType();

                            int jacketSize = rocketFunctions.getFuelJacketSize(jacket);
                            String jacketType = rocketFunctions.getFuelJacketConfigString(jacket);

                            if (fuelConfig.get(jacketType) != null) {

                                Inventory fuelInventory;
                                ArrayList<ItemStack> itemStack = new ArrayList<>();
                                itemStack.addAll(fuelConfig.getList(jacketType).stream().map(fuelCurrentItem
                                        -> (ItemStack) fuelCurrentItem).collect(Collectors.toList()));

                                fuelInventory = Bukkit.createInventory(player, jacketSize,
                                        "" + ChatColor.DARK_RED + ChatColor.BOLD + "Rocket Boot Fuel Jacket");
                                fuelInventory.setContents(itemStack.toArray(new ItemStack[itemStack.size()]));

                                player.openInventory(fuelInventory);

                                return;

                            }

                        }

                    }
                }
            }

            commonString.messageSend(getPlugin(), player, true, ChatColor.RED + "Fuel Jacket not found!");

        }

    }

}
