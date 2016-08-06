package com.ullarah.umagic;

import com.ullarah.umagic.function.CommonString;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class MagicExecutor extends MagicFunctions implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            new CommonString().messageNoConsole(sender);
            return true;
        }

        Player player = (Player) sender;
        MagicRecipe recipe = new MagicRecipe();

        switch (command.getName().toLowerCase()) {

            case "hoe":
                giveMagicHoe(player, recipe.hoeStable());
                break;

            case "xhoe":
                giveMagicHoe(player, recipe.hoeExperimental());
                break;

        }

        return true;

    }

}