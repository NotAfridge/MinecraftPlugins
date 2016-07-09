package com.ullarah.uchest.command;

import com.ullarah.uchest.ChestFunctions;
import com.ullarah.uchest.ChestInit;
import com.ullarah.uchest.function.CommonString;
import com.ullarah.uchest.function.PlayerProfile;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChestHold {

    public void runCommand(CommandSender sender, String[] args) {

        CommonString commonString = new CommonString();

        if (!(sender instanceof Player)) {
            commonString.messageNoConsole(ChestInit.getPlugin(), sender);
            return;
        }

        if (args.length == 0) {

            if (ChestInit.chestTypeEnabled.get("hchest"))
                new ChestCreation().create(sender, ChestFunctions.ValidChest.HOLD, true);
            else commonString.messageSend(ChestInit.getPlugin(), sender, "Hold Chest is currently unavailable.");
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
                        commonString.messageSend(ChestInit.getPlugin(), sender, ChatColor.YELLOW + "/hchest view <player>");
                        return;
                    }

                    new ChestPrepare().prepare((Player) sender, new PlayerProfile().lookup(args[1]).getId(), ChestFunctions.ValidChest.HOLD);
                    break;

                case RESET:
                    if (!sender.hasPermission("chest.reset")) {
                        commonString.messagePermDeny(ChestInit.getPlugin(), sender);
                        return;
                    }

                    if (args.length != 2) {
                        commonString.messageSend(ChestInit.getPlugin(), sender, ChatColor.YELLOW + "/hchest reset <player>");
                        return;
                    }

                    new ChestPrepare().reset((Player) sender, new PlayerProfile().lookup(args[1]).getId(), ChestFunctions.ValidChest.HOLD);
                    break;

                default:
                    new ChestCreation().create(sender, ChestFunctions.ValidChest.HOLD, true);

            }

        } catch (IllegalArgumentException e) {

            new DisplayHelp().runHelp(sender);

        }

    }

}
