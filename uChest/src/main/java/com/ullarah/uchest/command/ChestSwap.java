package com.ullarah.uchest.command;

import com.ullarah.uchest.ChestFunctions;
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
        String chestType = "schest";

        if (chestTypeEnabled.get(chestType)) {

            if (chestSwapBusy)
                commonString.messageSend(getPlugin(), sender, "The swap chest is busy. Try again later!");
            else {

                int playerLevel = player.getLevel();
                int accessLevel = getPlugin().getConfig().getInt(chestType + ".access");
                int lockTimer = getPlugin().getConfig().getInt(chestType + ".lockout");
                boolean removeLevel = getPlugin().getConfig().getBoolean(chestType + ".removelevel");

                if (player.getLevel() < accessLevel) {
                    String s = accessLevel > 1 ? "s" : "";
                    commonString.messageSend(getPlugin(), player, "You need more than " + accessLevel + " level" + s + " to open this chest.");
                    return;
                }

                if (player.hasPermission("chest.bypass")) {
                    ((Player) sender).openInventory(getChestSwapHolder().getInventory());
                    return;
                }

                if (!chestLockoutMap.get(chestType).containsKey(player.getUniqueId())) {
                    if (removeLevel) player.setLevel(playerLevel - accessLevel);
                    ((Player) sender).openInventory(getChestSwapHolder().getInventory());
                }

                if (lockTimer > 0) new ChestFunctions().chestLockout(player, lockTimer, chestType);

            }

        } else commonString.messageSend(getPlugin(), sender, "Swap Chest is currently unavailable.");

    }

}
