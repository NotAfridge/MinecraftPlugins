package com.ullarah.uchest.command;

import com.ullarah.uchest.ChestFunctions;
import com.ullarah.ulib.function.ProfileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

import static com.ullarah.uchest.ChestFunctions.validStorage.VAULT;
import static com.ullarah.uchest.ChestInit.*;

public class ChestVault {

    public void runCommand(CommandSender sender, String[] args) {

        if (args.length == 0) if (!(sender instanceof Player))
            sender.sendMessage(getMsgNoConsole());
        else if (!getMaintenanceCheck()) if (chestTypeEnabled.get("vchest")) ChestCreation.create(sender, VAULT, true);
        else sender.sendMessage(getMsgPrefix() + "Vault Chest is currently unavailable.");
        else
            sender.sendMessage(getMaintenanceMessage());

        else try {

            switch (ChestFunctions.validCommands.valueOf(args[0].toUpperCase())) {

                case VIEW:
                    if (!(sender instanceof Player))
                        sender.sendMessage(getMsgNoConsole());
                    else if (sender.hasPermission("chest.view")) {
                        if (args.length == 2) {
                            try {
                                ChestPrepare.prepare(Bukkit.getPlayer(ProfileUtils.lookup(args[1]).getId()),
                                        (Player) sender, VAULT);
                            } catch (Exception e) {
                                sender.sendMessage(getMsgPrefix() + ChatColor.RED +
                                        "Cannot view vault chest at this time. Try again later!");
                            }
                        } else sender.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "/vchest view <player>");
                    } else sender.sendMessage(getMsgPrefix() + getMsgPermDeny());
                    break;

                case UPGRADE:
                    if (!(sender instanceof Player))
                        sender.sendMessage(getMsgNoConsole());
                    else {
                        Player player = (Player) sender;
                        if (player.getLevel() >= 50) {

                            File chestFile = new File(getPlugin().getDataFolder() + File.separator + "vault",
                                    player.getUniqueId().toString() + ".yml");

                            if (chestFile.exists()) {

                                FileConfiguration chestConfig = YamlConfiguration.loadConfiguration(chestFile);

                                int chestPlayerSlot = chestConfig.getInt("slots");

                                if (chestPlayerSlot >= 54) {

                                    player.sendMessage(getMsgPrefix() + ChatColor.AQUA + "You have the maximum number of slots!");

                                } else {

                                    if (player.getLevel() >= 50) {

                                        chestConfig.set("slot", chestPlayerSlot + 9);
                                        player.setLevel(player.getLevel() - 50);

                                        try {
                                            chestConfig.save(chestFile);
                                        } catch (IOException e) {
                                            player.sendMessage(getMsgPrefix() + ChatColor.RED + "Slot Update Error!");
                                        }

                                        player.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "You upgraded your vault! You now have " +
                                                ChatColor.GREEN + (chestPlayerSlot + 9) + ChatColor.YELLOW + " slots!");

                                    } else {

                                        player.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "You need at least" +
                                                ChatColor.GOLD + " 50xp levels " + ChatColor.YELLOW + "to upgrade!");

                                    }

                                }

                            }

                        } else player.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "You need at least" +
                                ChatColor.GOLD + " 50xp levels " + ChatColor.YELLOW + "to upgrade!");
                    }
                    break;

                default:
                    if (!(sender instanceof Player))
                        sender.sendMessage(getMsgNoConsole());
                    else ChestCreation.create(sender, VAULT, true);

            }

        } catch (IllegalArgumentException e) {

            if (!(sender instanceof Player))
                sender.sendMessage(getMsgNoConsole());
            else if (!args[0].equalsIgnoreCase("view")) DisplayHelp.runHelp(sender);

        }

    }

}
