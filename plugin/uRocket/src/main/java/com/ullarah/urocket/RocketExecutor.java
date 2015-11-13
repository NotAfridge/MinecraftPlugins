package com.ullarah.urocket;

import com.ullarah.ulib.function.CommonString;
import com.ullarah.urocket.command.DisplayComponentChest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.urocket.RocketInit.getPlugin;
import static com.ullarah.urocket.command.DisplayHelp.runHelp;

class RocketExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (command.getName().equalsIgnoreCase("rocket")) rocketChest(sender, args);

        return true;

    }

    private void rocketChest(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) CommonString.messageNoConsole(getPlugin(), sender);
        else {

            if (args.length == 0) runHelp(sender);
            else {

                try {

                    switch (args[0].toUpperCase()) {

                        case "CHEST":
                            if (sender.hasPermission("rocket.chest"))
                                DisplayComponentChest.open(sender);
                            else
                                runHelp(sender);
                            break;

                        default:
                            runHelp(sender);
                            break;

                    }

                } catch (IllegalArgumentException e) {

                    runHelp(sender);

                }

            }

        }

    }

}
