package com.ullarah.uchest.command;

import com.ullarah.uchest.ChestFunctions;
import com.ullarah.uchest.function.CommonString;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.uchest.ChestInit.*;

public class ChestMoney {

    public void runCommand(CommandSender sender, String[] args) {

        CommonString commonString = new CommonString();
        ChestFunctions chestFunctions = new ChestFunctions();

        if (!(sender instanceof Player)) {
            commonString.messageNoConsole(getPlugin(), sender);
            return;
        }

        if (allowMoneyChest && chestTypeEnabled.get("mchest")) chestFunctions.openConvertChest(sender, "MONEY");
        else commonString.messageSend(getPlugin(), sender, "Money Chest is currently unavailable.");

    }

}
