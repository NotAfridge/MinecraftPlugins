package com.ullarah.uchest.command;

import com.ullarah.ulib.function.CommonString;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.uchest.ChestFunctions.openConvertChest;
import static com.ullarah.uchest.ChestInit.*;

public class ChestExperience {

    public void runCommand(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) new CommonString().messageNoConsole(getPlugin(), sender);
        else if (!getMaintenanceCheck()) {

            if (allowMoneyChest && chestTypeEnabled.get("xchest")) openConvertChest(sender, "XP");
            else
                new CommonString().messageSend(getPlugin(), sender, true, new String[]{"Experience Chest is currently unavailable."});

        } else new CommonString().messageMaintenance(getPlugin(), sender);

    }

}
