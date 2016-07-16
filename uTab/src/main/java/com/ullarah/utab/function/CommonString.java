package com.ullarah.utab.function;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class CommonString {

    /**
     * The name of the plugin placed in prefix format
     *
     * @param plugin the plugin object
     * @return a fancy prefix style string
     */
    private String pluginPrefix(Plugin plugin) {

        return ChatColor.GOLD + "[" + plugin.getName() + "] " + ChatColor.WHITE;

    }

    /**
     * A permission denied message
     *
     * @param plugin the plugin object
     * @param sender the sender who receives the message
     */
    public void messagePermDeny(Plugin plugin, CommandSender sender) {

        sender.sendMessage(pluginPrefix(plugin) + ChatColor.RED + "No permission.");

    }

    /**
     * A message that is sent to a {@code CommandSender} object
     *
     * @param plugin  the plugin object
     * @param sender  the sender who receives the message
     * @param message the message to send
     */
    public void messageSend(Plugin plugin, CommandSender sender, String message) {

        sender.sendMessage(pluginPrefix(plugin) + message);

    }

}
