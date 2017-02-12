package com.ullarah.umagic;

import com.ullarah.umagic.recipe.MagicHoeCosmic;
import com.ullarah.umagic.recipe.MagicHoeNormal;
import com.ullarah.umagic.recipe.MagicHoeSuper;
import com.ullarah.umagic.recipe.MagicHoeUber;
import org.bukkit.ChatColor;
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
        MagicHoeCosmic hoeCosmic = new MagicHoeCosmic();

        String hoeType = "What type? " + ChatColor.YELLOW + "/hoe (n)ormal | (s)uper | (u)ber | (c)osmic";

        if (command.getName().toLowerCase().equals("hoe")) {

            if (args.length >= 1) {

                switch (args[0].toLowerCase()) {

                    case "n":
                    case "normal":
                        giveMagicHoe(player, hoeNormal.hoe());
                        break;

                    case "s":
                    case "super":
                        giveMagicHoe(player, hoeSuper.hoe());
                        break;

                    case "u":
                    case "uber":
                        giveMagicHoe(player, hoeUber.hoe());
                        break;

                    case "c":
                    case "cosmic":
                        giveMagicHoe(player, hoeCosmic.hoe());
                        break;

                    default:
                        getCommonString().messageSend(player, hoeType);
                        break;

                }

            } else getCommonString().messageSend(player, hoeType);

        }

        return true;

    }

}