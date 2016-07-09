package com.ullarah.uchest.command;

import com.ullarah.uchest.ChestFunctions;
import com.ullarah.uchest.ChestInit;
import com.ullarah.uchest.function.CommonString;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChestExperience {

    public void runCommand(CommandSender sender, String[] args) {

        CommonString commonString = new CommonString();
        ChestFunctions chestFunctions = new ChestFunctions();

        if (!(sender instanceof Player)) {
            commonString.messageNoConsole(ChestInit.getPlugin(), sender);
            return;
        }

        if (ChestInit.chestTypeEnabled.get("xchest"))
            chestFunctions.openConvertChest(sender, ChestFunctions.ValidChest.XP);
        else commonString.messageSend(ChestInit.getPlugin(), sender, "Experience Chest is currently unavailable.");

    }

}
