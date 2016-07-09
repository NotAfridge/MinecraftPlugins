package com.ullarah.uchest.command;

import com.ullarah.uchest.ChestFunctions;
import com.ullarah.uchest.ChestInit;
import com.ullarah.uchest.function.CommonString;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChestDonation {

    public void runCommand(CommandSender sender, String[] args) {

        CommonString commonString = new CommonString();
        String consoleTools = new CommonString().pluginPrefix(ChestInit.getPlugin()) + ChatColor.WHITE + "random | reset";

        if (args.length == 0) {

            if (!(sender instanceof Player)) {
                new CommonString().messageNoConsole(ChestInit.getPlugin(), sender);
                return;
            }

            Player player = ((Player) sender).getPlayer();
            String chestType = "dchest";

            if (ChestInit.chestTypeEnabled.get(chestType)) {

                int playerLevel = player.getLevel();
                int accessLevel = ChestInit.getPlugin().getConfig().getInt(chestType + ".access");
                int lockTimer = ChestInit.getPlugin().getConfig().getInt(chestType + ".lockout");
                boolean removeLevel = ChestInit.getPlugin().getConfig().getBoolean(chestType + ".removelevel");

                if (playerLevel < accessLevel) {
                    String s = accessLevel > 1 ? "s" : "";
                    commonString.messageSend(ChestInit.getPlugin(), player, "You need more than " + accessLevel + " level" + s + " to open this chest.");
                    return;
                }

                if (player.hasPermission("chest.bypass")) {
                    player.openInventory(ChestInit.getChestDonationInventory());
                    return;
                }

                if (!ChestInit.chestLockoutMap.get(chestType).containsKey(player.getUniqueId())) {
                    if (removeLevel) player.setLevel(playerLevel - accessLevel);
                    ((Player) sender).openInventory(ChestInit.getChestDonationInventory());
                }

                if (lockTimer > 0) new ChestFunctions().chestLockout(player, lockTimer, chestType);

            } else commonString.messageSend(ChestInit.getPlugin(), sender, "Donation Chest is currently unavailable.");

        } else try {

            switch (ChestFunctions.validCommands.valueOf(args[0].toUpperCase())) {

                case RANDOM:
                    new DonationRandom().checkPermission(sender);
                    break;

                case RESET:
                    new DonationReset().resetDonationChest(sender);
                    break;

                default:
                    if (!(sender instanceof Player)) sender.sendMessage(consoleTools);
                    else ((Player) sender).openInventory(ChestInit.getChestDonationInventory());

            }

        } catch (IllegalArgumentException e) {

            if (!(sender instanceof Player)) sender.sendMessage(consoleTools);
            else new DisplayHelp().runHelp(sender);

        }

    }

}
