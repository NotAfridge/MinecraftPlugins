package com.ullarah.ulib.function;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommonString {

    public static String pluginPrefix(Plugin plugin) {

        return ChatColor.GOLD + "[" + plugin.getName() + "] " + ChatColor.WHITE;

    }

    public static void messagePermDeny(Plugin plugin, CommandSender sender) {

        sender.sendMessage(pluginPrefix(plugin) + ChatColor.RED + "No permission.");

    }

    public static void messageNoConsole(Plugin plugin, CommandSender sender) {

        sender.sendMessage(pluginPrefix(plugin) + ChatColor.RED + "No console usage.");

    }

    public static void messageMaintenance(Plugin plugin, CommandSender sender) {

        sender.sendMessage(pluginPrefix(plugin) + ChatColor.RED + "Currently under maintenance.");

    }

    public static void messagePlayer(Plugin plugin, Player player, String[] messages) {

        for (String message : messages) player.sendMessage(pluginPrefix(plugin) + message);

    }

}
