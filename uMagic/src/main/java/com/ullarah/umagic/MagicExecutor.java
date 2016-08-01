package com.ullarah.umagic;

import com.ullarah.umagic.function.CommonString;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

class MagicExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (command.getName().equalsIgnoreCase("hoe")) {

            if (sender.hasPermission("umagic.gethoe")) {

                Player player = (Player) sender;

                PlayerInventory playerInventory = player.getInventory();
                int firstEmpty = playerInventory.firstEmpty();

                if (firstEmpty >= 0) playerInventory.setItem(firstEmpty, new MagicRecipe().hoe());
                else new CommonString().messageSend(player, "Your inventory is full.");

            }

        }

        return true;

    }

}