package com.ullarah.uchest.function;

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
     * An under maintenance message
     *
     * @param plugin the plugin object
     * @param sender the sender who receives the message
     */
    public void messageMaintenance(Plugin plugin, CommandSender sender) {

        sender.sendMessage(pluginPrefix(plugin) + ChatColor.RED + "Currently under maintenance.");

    }

    /**
     * A message that is sent to a {@code CommandSender} object
     *
     * @param plugin   the plugin object
     * @param sender   the sender who receives the message
     * @param prefix   the prefix of the current plugin
     * @param messages an array of messages to send
     */
    public void messageSend(Plugin plugin, CommandSender sender, boolean prefix, String[] messages) {

        for (String message : messages) sender.sendMessage(prefix ? pluginPrefix(plugin) + message : message);

    }

    /**
     * A message that is sent to a {@code CommandSender} object
     *
     * @param plugin  the plugin object
     * @param sender  the sender who receives the message
     * @param prefix  the prefix of the current plugin
     * @param message the message to send
     */
    public void messageSend(Plugin plugin, CommandSender sender, boolean prefix, String message) {

        sender.sendMessage(prefix ? pluginPrefix(plugin) + message : message);

    }

    /**
     * A message that is sent to a {@code Player} object
     *
     * @param plugin   the plugin object
     * @param player   the player who receives the message
     * @param prefix   the prefix of the current plugin
     * @param messages an array of messages to send
     */
    public void messageSend(Plugin plugin, Player player, boolean prefix, String[] messages) {

        for (String message : messages) player.sendMessage(prefix ? pluginPrefix(plugin) + message : message);

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
