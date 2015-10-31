package com.ullarah.uchest.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ullarah.uchest.ChestInit.chestTypeEnabled;
import static com.ullarah.uchest.ChestInit.getPlugin;
import static com.ullarah.ulib.function.CommonString.messagePermDeny;
import static com.ullarah.ulib.function.CommonString.messagePlayer;

public class ToggleAccess {

    public static void toggleChestAccess(CommandSender sender, String[] args) {

        if (sender.hasPermission("chest.maintenance"))
            if (args.length == 2) if (args[1].toLowerCase().matches("[dhmrvx]chest")) {

                switch (chestTypeEnabled.get(args[1].toLowerCase()) ? 0 : 1) {

                    case 0:
                        getPlugin().getConfig().set(args[1].toLowerCase() + "enabled", false);
                        chestTypeEnabled.put(args[1].toLowerCase(), false);
                        messagePlayer(getPlugin(), (Player) sender, new String[]{
                                ChatColor.YELLOW + args[1].toLowerCase() + ChatColor.RED + " is now disabled."
                        });
                        break;

                    case 1:
                        getPlugin().getConfig().set(args[1] + "enabled", true);
                        chestTypeEnabled.put(args[1].toLowerCase(), true);
                        messagePlayer(getPlugin(), (Player) sender, new String[]{
                                ChatColor.YELLOW + args[1].toLowerCase() + ChatColor.GREEN + " is now enabled."
                        });
                        break;

                }

                getPlugin().saveConfig();

            } else messagePlayer(getPlugin(), (Player) sender, new String[]{
                    ChatColor.RED + "That type of chest does not exist!"
            });
            else messagePlayer(getPlugin(), (Player) sender, new String[]{
                    ChatColor.YELLOW + "/chest toggle <chest type> <on|off>"
            });
        else messagePermDeny(getPlugin(), sender);

    }

}
