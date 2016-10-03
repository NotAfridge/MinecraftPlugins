package com.ullarah.umagic;

import com.ullarah.umagic.recipe.MagicHoeNormal;
import com.ullarah.umagic.recipe.MagicHoeSuper;
import com.ullarah.umagic.recipe.MagicHoeUber;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class MagicExecutor extends MagicFunctions implements CommandExecutor {

    MagicExecutor() {
        super(false);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            getCommonString().messageNoConsole(sender);
            return true;
        }

        Player player = (Player) sender;

        MagicHoeNormal hoeNormal = new MagicHoeNormal();
        MagicHoeSuper hoeSuper = new MagicHoeSuper();
        MagicHoeUber hoeUber = new MagicHoeUber();

        switch (command.getName().toLowerCase()) {

            case "n-hoe":
                giveMagicHoe(player, hoeNormal.hoe());
                break;

            case "s-hoe":
                giveMagicHoe(player, hoeSuper.hoe());
                break;

            case "u-hoe":
                giveMagicHoe(player, hoeUber.hoe());
                break;

        }

        return true;

    }

}