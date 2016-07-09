package com.ullarah.uchest.command;

import com.ullarah.uchest.ChestFunctions;
import com.ullarah.uchest.ChestInit;
import com.ullarah.uchest.function.CommonString;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChestEnchantment {

    public void runCommand(CommandSender sender, String[] args) {

        CommonString commonString = new CommonString();
        ChestFunctions chestFunctions = new ChestFunctions();

        if (!(sender instanceof Player)) {
            commonString.messageNoConsole(ChestInit.getPlugin(), sender);
            return;
        }

        if (ChestInit.chestTypeEnabled.get("echest")) chestFunctions.openEnchantmentChest(sender);
        else commonString.messageSend(ChestInit.getPlugin(), sender, "Enchantment Chest is currently unavailable.");

    }

}
