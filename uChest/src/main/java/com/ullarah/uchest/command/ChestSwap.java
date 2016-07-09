package com.ullarah.uchest.command;

import com.ullarah.uchest.ChestFunctions;
import com.ullarah.uchest.ChestInit;
import com.ullarah.uchest.function.CommonString;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChestSwap {

    public void runCommand(CommandSender sender, String[] args) {

        CommonString commonString = new CommonString();

        if (!(sender instanceof Player)) {
            commonString.messageNoConsole(ChestInit.getPlugin(), sender);
            return;
        }

        Player player = ((Player) sender).getPlayer();
        String chestType = "wchest";

        if (ChestInit.chestTypeEnabled.get(chestType)) {

            if (ChestInit.chestSwapBusy)
                commonString.messageSend(ChestInit.getPlugin(), sender, "The swap chest is busy. Try again later!");
            else {

                int playerLevel = player.getLevel();
                int accessLevel = ChestInit.getPlugin().getConfig().getInt(chestType + ".access");
                int lockTimer = ChestInit.getPlugin().getConfig().getInt(chestType + ".lockout");
                boolean removeLevel = ChestInit.getPlugin().getConfig().getBoolean(chestType + ".removelevel");

                if (player.getLevel() < accessLevel) {
                    String s = accessLevel > 1 ? "s" : "";
                    commonString.messageSend(ChestInit.getPlugin(), player, "You need more than " + accessLevel + " level" + s + " to open this chest.");
                    return;
                }

                if (player.hasPermission("chest.bypass")) {
                    ((Player) sender).openInventory(ChestInit.getChestSwapInventory());
                    return;
                }

                if (!ChestInit.chestLockoutMap.get(chestType).containsKey(player.getUniqueId())) {
                    if (removeLevel) player.setLevel(playerLevel - accessLevel);
                    ((Player) sender).openInventory(ChestInit.getChestSwapInventory());
                }

                if (lockTimer > 0) new ChestFunctions().chestLockout(player, lockTimer, chestType);

            }

        } else commonString.messageSend(ChestInit.getPlugin(), sender, "Swap Chest is currently unavailable.");

    }

}
