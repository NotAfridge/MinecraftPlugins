package com.ullarah.ulottery.function;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommonString {

    private Plugin plugin;

    public CommonString(Plugin plugin) {
        setPlugin(plugin);
    }

    private Plugin getPlugin() {
        return this.plugin;
    }

    private void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * The name of the plugin placed in prefix format
     *
     * @return a fancy prefix style string
     */
    private String pluginPrefix() {

        return ChatColor.GOLD + "[" + getPlugin().getName() + "] " + ChatColor.WHITE;

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
     * A no console usage message
     *
     * @param sender the sender who receives the message
     */
    public void messageNoConsole(CommandSender sender) {

        sender.sendMessage(pluginPrefix() + ChatColor.RED + "No console usage.");

    }

    /**
     * A message that is sent to a {@code CommandSender} object
     *
     * @param sender   the sender who receives the message
     * @param prefix   the prefix of the current plugin
     * @param messages an array of messages to send
     */
    public void messageSend(CommandSender sender, boolean prefix, String[] messages) {

        for (String message : messages) sender.sendMessage(prefix ? pluginPrefix() + message : message);

    }

    /**
     * A message that is sent to a {@code CommandSender} object
     *
     * @param sender  the sender who receives the message
     * @param message the message to send
     */
    public void messageSend(CommandSender sender, String message) {

        sender.sendMessage(pluginPrefix() + message);

    }

    /**
     * A message that is sent to a {@code Player} object
     *
     * @param player   the player who receives the message
     * @param prefix   the prefix of the current plugin
     * @param messages an array of messages to send
     */
    public void messageSend(Player player, boolean prefix, String[] messages) {

        for (String message : messages) player.sendMessage(prefix ? pluginPrefix() + message : message);

    }

    /**
     * A message that is sent to a {@code Player} object
     *
     * @param player  the sender who receives the message
     * @param message the message to send
     */
    public void messageSend(Player player, String message) {

        player.sendMessage(pluginPrefix() + message);

    }

}
