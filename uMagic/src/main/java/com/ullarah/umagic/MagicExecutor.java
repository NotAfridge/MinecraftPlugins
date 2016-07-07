package com.ullarah.umagic;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MagicExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (command.getName().equalsIgnoreCase("hoe")) {

            if (sender.hasPermission("magic.gethoe")) {
                Player player = (Player) sender;
                player.getWorld().dropItemNaturally(player.getLocation(), new MagicRecipe().hoe());
            }

        }

        return true;

    }

}