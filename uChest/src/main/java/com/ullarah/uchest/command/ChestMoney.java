package com.ullarah.uchest.command;

import com.ullarah.uchest.ChestFunctions;
import com.ullarah.uchest.ChestInit;
import com.ullarah.uchest.function.CommonString;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChestMoney {

    public void runCommand(CommandSender sender, String[] args) {

        CommonString commonString = new CommonString();
        ChestFunctions chestFunctions = new ChestFunctions();

        if (!(sender instanceof Player)) {
            commonString.messageNoConsole(ChestInit.getPlugin(), sender);
            return;
        }

        if (ChestInit.chestTypeEnabled.get("mchest"))
            chestFunctions.openConvertChest(sender, ChestFunctions.ValidChest.MONEY);
        else commonString.messageSend(ChestInit.getPlugin(), sender, "Money Chest is currently unavailable.");

    }

}
