package com.ullarah.uchest.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.uchest.ChestFunctions.openConvertChest;
import static com.ullarah.uchest.ChestInit.*;
import static com.ullarah.ulib.function.CommonString.*;

public class ChestExperience {

    public void runCommand(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) messageNoConsole(getPlugin(), sender);
        else if (!getMaintenanceCheck()) {

            if (allowMoneyChest && chestTypeEnabled.get("xchest")) openConvertChest(sender, "XP");
            else messageSend(getPlugin(), sender, true, new String[]{"Experience Chest is currently unavailable."});

        } else messageMaintenance(getPlugin(), sender);

    }

}
