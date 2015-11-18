package com.ullarah.uchest.command;

import com.ullarah.uchest.function.CommonString;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.uchest.ChestFunctions.openRandomChest;
import static com.ullarah.uchest.ChestInit.*;

public class ChestRandom {

    public void runCommand(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) new CommonString().messageNoConsole(getPlugin(), sender);
        else if (!getMaintenanceCheck()) {

            if (chestTypeEnabled.get("rchest")) openRandomChest(sender);
            else
                new CommonString().messageSend(getPlugin(), sender, true, new String[]{"Random Chest is currently unavailable."});

        } else new CommonString().messageMaintenance(getPlugin(), sender);

    }

}