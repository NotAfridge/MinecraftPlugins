package com.ullarah.uchest.command;

import com.ullarah.uchest.ChestFunctions;
import com.ullarah.uchest.function.CommonString;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.uchest.ChestFunctions.validConvert.XP;
import static com.ullarah.uchest.ChestInit.chestTypeEnabled;
import static com.ullarah.uchest.ChestInit.getPlugin;

public class ChestExperience {

    public void runCommand(CommandSender sender, String[] args) {

        CommonString commonString = new CommonString();
        ChestFunctions chestFunctions = new ChestFunctions();

        if (!(sender instanceof Player)) {
            commonString.messageNoConsole(getPlugin(), sender);
            return;
        }

        if (chestTypeEnabled.get("xchest")) chestFunctions.openConvertChest(sender, XP);
        else commonString.messageSend(getPlugin(), sender, "Experience Chest is currently unavailable.");

    }

}
