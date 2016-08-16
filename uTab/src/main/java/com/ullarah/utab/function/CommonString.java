package com.ullarah.utab.function;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class CommonString {

    private final Plugin plugin;

    public CommonString(Plugin instance) {
        plugin = instance;
    }

    /**
     * The name of the plugin placed in prefix format
     *
     * @return a fancy prefix style string
     */
    public String pluginPrefix() {

        return ChatColor.GOLD + "[" + plugin.getName() + "] " + ChatColor.WHITE;

    }

    /**
     * A permission denied message
     *
     * @param sender the sender who receives the message
     */
    public void messagePermDeny(CommandSender sender) {

        sender.sendMessage(pluginPrefix() + ChatColor.RED + "No permission.");

    }

    /**
     * A message that is sent to a {@code CommandSender} object
     *
     * @param sender  the sender who receives the message
     * @param message the message to send
     */
    @SuppressWarnings("SameParameterValue")
    public void messageSend(CommandSender sender, String message) {

        sender.sendMessage(pluginPrefix() + message);

    }

    /**
     * A message that is sent to a {@code CommandSender} object
     *
     * @param sender   the sender who receives the message
     * @param messages an array of messages to send
     */
    public void messageSend(CommandSender sender, String[] messages) {

        for (String message : messages) sender.sendMessage(message);

    }

}
