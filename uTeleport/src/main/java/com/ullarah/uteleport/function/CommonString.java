package com.ullarah.uteleport.function;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommonString {

    /**
     * The name of the plugin placed in prefix format
     *
     * @param plugin the plugin object
     * @return a fancy prefix style string
     */
    public String pluginPrefix(Plugin plugin) {

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
     * A no console usage message
     *
     * @param plugin the plugin object
     * @param sender the sender who receives the message
     */
    public void messageNoConsole(Plugin plugin, CommandSender sender) {

        sender.sendMessage(pluginPrefix(plugin) + ChatColor.RED + "No console usage.");

    }

    /**
     * A message that is sent to a {@code Player} object
     *
     * @param plugin  the plugin object
     * @param player  the sender who receives the message
     * @param prefix  the prefix of the current plugin
     * @param message the message to send
     */
    public void messageSend(Plugin plugin, Player player, boolean prefix, String message) {

        player.sendMessage(prefix ? pluginPrefix(plugin) + message : message);

    }

}
