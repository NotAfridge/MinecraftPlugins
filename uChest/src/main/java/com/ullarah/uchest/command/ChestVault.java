package com.ullarah.uchest.command;

import com.ullarah.uchest.ChestFunctions;
import com.ullarah.uchest.ChestInit;
import com.ullarah.uchest.function.CommonString;
import com.ullarah.uchest.function.PlayerProfile;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class ChestVault {

    public void runCommand(CommandSender sender, String[] args) {

        CommonString commonString = new CommonString();

        if (!(sender instanceof Player)) {
            commonString.messageNoConsole(ChestInit.getPlugin(), sender);
            return;
        }

        if (args.length == 0) {

            if (ChestInit.chestTypeEnabled.get("vchest"))
                new ChestCreation().create(sender, ChestFunctions.ValidChest.VAULT, true);
            else commonString.messageSend(ChestInit.getPlugin(), sender, "Vault Chest is currently unavailable.");
            return;

        }

        try {

            switch (ChestFunctions.validCommands.valueOf(args[0].toUpperCase())) {

                case VIEW:
                    if (!sender.hasPermission("chest.view")) {
                        commonString.messagePermDeny(ChestInit.getPlugin(), sender);
                        return;
                    }

                    if (args.length != 2) {
                        commonString.messageSend(ChestInit.getPlugin(), sender, ChatColor.YELLOW + "/vchest view <player>");
                        return;
                    }

                    new ChestPrepare().prepare((Player) sender, new PlayerProfile().lookup(args[1]).getId(), ChestFunctions.ValidChest.VAULT);
                    break;

                case RESET:
                    if (!sender.hasPermission("chest.reset")) {
                        commonString.messagePermDeny(ChestInit.getPlugin(), sender);
                        return;
                    }

                    if (args.length != 2) {
                        commonString.messageSend(ChestInit.getPlugin(), sender, ChatColor.YELLOW + "/vchest reset <player>");
                        return;
                    }

                    new ChestPrepare().reset((Player) sender, new PlayerProfile().lookup(args[1]).getId(), ChestFunctions.ValidChest.VAULT);
                    break;

                case UPGRADE:
                    Player player = (Player) sender;
                    int upgradeLevel = ChestInit.getPlugin().getConfig().getInt("vchest.upgradelevel");

                    if (player.getLevel() < upgradeLevel) {
                        commonString.messageSend(ChestInit.getPlugin(), player, ChatColor.YELLOW + "You need at least " +
                                ChatColor.GOLD + upgradeLevel + "xp levels " + ChatColor.YELLOW + "to upgrade!");
                        return;
                    }

                    File chestFile = new File(ChestInit.getPlugin().getDataFolder() + File.separator + "vault", player.getUniqueId().toString() + ".yml");

                    if (chestFile.exists()) {

                        FileConfiguration chestConfig = YamlConfiguration.loadConfiguration(chestFile);
                        int chestPlayerSlot = chestConfig.getInt("slots");

                        if (chestPlayerSlot >= 54) {

                            commonString.messageSend(ChestInit.getPlugin(), player, ChatColor.AQUA + "You have the maximum number of slots!");
                            return;

                        }

                        if (player.getLevel() >= upgradeLevel) {

                            chestConfig.set("slots", chestPlayerSlot + 9);
                            player.setLevel(player.getLevel() - upgradeLevel);

                            try {
                                chestConfig.save(chestFile);
                            } catch (IOException e) {
                                commonString.messageSend(ChestInit.getPlugin(), player, ChatColor.RED + "Slot Update Error!");
                            }

                            commonString.messageSend(ChestInit.getPlugin(), player,
                                    ChatColor.YELLOW + "You upgraded your vault! You now have " +
                                            ChatColor.GREEN + (chestPlayerSlot + 9) + ChatColor.YELLOW + " slots!");

                        }

                    }
                    break;

                default:
                    new ChestCreation().create(sender, ChestFunctions.ValidChest.VAULT, true);

            }

        } catch (IllegalArgumentException e) {

            new DisplayHelp().runHelp(sender);

        }

    }

}
