package com.ullarah.uchest.command;

import com.ullarah.uchest.function.CommonString;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.uchest.ChestInit.*;

public class ChestSwap {

    public void runCommand(CommandSender sender, String[] args) {

        CommonString commonString = new CommonString();

        if (!(sender instanceof Player)) {
            commonString.messageNoConsole(getPlugin(), sender);
            return;
        }

        if (chestTypeEnabled.get("schest")) {

            if (chestSwapBusy)
                commonString.messageSend(getPlugin(), sender, "The swap chest is busy. Try again later!");
            else ((Player) sender).openInventory(getChestSwapHolder().getInventory());

        } else commonString.messageSend(getPlugin(), sender, "Swap Chest is currently unavailable.");

    }

}
