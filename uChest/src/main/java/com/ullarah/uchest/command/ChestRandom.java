package com.ullarah.uchest.command;

import com.ullarah.uchest.ChestFunctions;
import com.ullarah.uchest.function.CommonString;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.uchest.ChestInit.chestTypeEnabled;
import static com.ullarah.uchest.ChestInit.getPlugin;

public class ChestRandom {

    public void runCommand(CommandSender sender, String[] args) {

        CommonString commonString = new CommonString();
        ChestFunctions chestFunctions = new ChestFunctions();

        if (!(sender instanceof Player)) {
            commonString.messageNoConsole(getPlugin(), sender);
            return;
        }

        if (chestTypeEnabled.get("rchest")) chestFunctions.openRandomChest(sender);
        else commonString.messageSend(getPlugin(), sender, "Random Chest is currently unavailable.");

    }

}