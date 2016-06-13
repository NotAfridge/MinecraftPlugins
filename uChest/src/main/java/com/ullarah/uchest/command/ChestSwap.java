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

        Player player = ((Player) sender).getPlayer();

        if (chestTypeEnabled.get("schest")) {

            if (chestSwapBusy)
                commonString.messageSend(getPlugin(), sender, "The swap chest is busy. Try again later!");
            else {
                int accessLevel = getPlugin().getConfig().getInt("schest.access");
                if (player.getLevel() < accessLevel) {
                    String s = accessLevel > 1 ? "s" : "";
                    commonString.messageSend(getPlugin(), player, "You need more than " + accessLevel + " level" + s + " to open this chest.");
                    return;
                }
                ((Player) sender).openInventory(getChestSwapHolder().getInventory());
            }

        } else commonString.messageSend(getPlugin(), sender, "Swap Chest is currently unavailable.");

    }

}
