package com.ullarah.uchest.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.uchest.ChestFunctions.openRandomChest;
import static com.ullarah.uchest.ChestInit.*;
import static com.ullarah.ulib.function.CommonString.*;

public class ChestRandom {

    public void runCommand(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) messageNoConsole(getPlugin(), sender);
        else if (!getMaintenanceCheck()) {

            if (chestTypeEnabled.get("rchest")) openRandomChest(sender);
            else messagePlayer(getPlugin(), (Player) sender, new String[]{"Random Chest is currently unavailable."});

        } else messageMaintenance(getPlugin(), sender);

    }

}
