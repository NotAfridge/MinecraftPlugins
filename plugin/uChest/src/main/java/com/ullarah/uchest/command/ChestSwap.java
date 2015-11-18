package com.ullarah.uchest.command;

import com.ullarah.ulib.function.CommonString;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.uchest.ChestInit.*;

public class ChestSwap {

    public void runCommand(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) new CommonString().messageNoConsole(getPlugin(), sender);
        else if (!getMaintenanceCheck()) {

            if (chestTypeEnabled.get("schest")) {

                if (chestSwapBusy)
                    new CommonString().messageSend(getPlugin(), sender, true, new String[]{"The swap chest is busy. Try again later!"});
                else ((Player) sender).openInventory(getChestSwapHolder().getInventory());

            } else
                new CommonString().messageSend(getPlugin(), sender, true, new String[]{"Swap Chest is currently unavailable."});

        } else new CommonString().messageMaintenance(getPlugin(), sender);

    }

}
