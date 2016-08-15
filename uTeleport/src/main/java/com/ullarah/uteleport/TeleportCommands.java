package com.ullarah.uteleport;

import com.ullarah.uteleport.function.CommonString;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

class TeleportCommands implements CommandExecutor {

    private final Plugin plugin;
    private final TeleportFunctions teleportFunctions;

    TeleportCommands(Plugin instance, TeleportFunctions functions) {
        plugin = instance;
        teleportFunctions = functions;
    }

    private Plugin getPlugin() {
        return plugin;
    }

    private TeleportFunctions getTeleportFunctions() {
        return teleportFunctions;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        CommonString commonString = new CommonString(getPlugin());

        if (!(sender instanceof Player)) {
            commonString.messageNoConsole(sender);
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("uteleport.use")) {
            commonString.messagePermDeny(player);
            return true;
        }

        if (!getTeleportFunctions().getHistoryMap().containsKey(player.getUniqueId())) {
            commonString.messageSend(player, true, "No teleport history found.");
            return true;
        }

        if (args.length == 2) if (args[0].equals("go")) {
            getTeleportFunctions().sendPlayer(args, player);
            return true;
        }

        getTeleportFunctions().displayHistory(player);

        return true;

    }

}