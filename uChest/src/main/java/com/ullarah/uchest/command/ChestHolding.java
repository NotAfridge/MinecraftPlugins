package com.ullarah.uchest.command;

import com.ullarah.uchest.ChestFunctions;
import com.ullarah.uchest.function.CommonString;
import com.ullarah.uchest.function.PlayerProfile;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.uchest.ChestFunctions.validStorage.HOLD;
import static com.ullarah.uchest.ChestInit.chestTypeEnabled;
import static com.ullarah.uchest.ChestInit.getPlugin;

public class ChestHolding {

    public void runCommand(CommandSender sender, String[] args) {

        CommonString commonString = new CommonString();

        if (!(sender instanceof Player)) {
            commonString.messageNoConsole(getPlugin(), sender);
            return;
        }

        if (args.length == 0) {

            if (chestTypeEnabled.get("hchest")) new ChestCreation().create(sender, HOLD, true);
            else commonString.messageSend(getPlugin(), sender, "Hold Chest is currently unavailable.");
            return;

        }

        try {

            switch (ChestFunctions.validCommands.valueOf(args[0].toUpperCase())) {

                case VIEW:
                    if (!sender.hasPermission("chest.view")) {
                        commonString.messagePermDeny(getPlugin(), sender);
                        return;
                    }

                    if (args.length != 2) {
                        commonString.messageSend(getPlugin(), sender, ChatColor.YELLOW + "/hchest view <player>");
                        return;
                    }

                    new ChestPrepare().prepare((Player) sender, new PlayerProfile().lookup(args[1]).getId(), HOLD);
                    break;

                default:
                    new ChestCreation().create(sender, HOLD, true);

            }

        } catch (IllegalArgumentException e) {

            new DisplayHelp().runHelp(sender);

        }

    }

}
