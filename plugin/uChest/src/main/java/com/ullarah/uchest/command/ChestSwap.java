package com.ullarah.uchest.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.uchest.ChestInit.*;
import static com.ullarah.ulib.function.CommonString.*;

public class ChestSwap {

    public void runCommand(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) messageNoConsole(getPlugin(), sender);
        else if (!getMaintenanceCheck()) {

            if (chestTypeEnabled.get("schest")) {

                if (chestSwapBusy)
                    messageSend(getPlugin(), sender, true, new String[]{"The swap chest is busy. Try again later!"});
                else ((Player) sender).openInventory(getChestSwapHolder().getInventory());

            } else messageSend(getPlugin(), sender, true, new String[]{"Swap Chest is currently unavailable."});

        } else messageMaintenance(getPlugin(), sender);

    }

}
