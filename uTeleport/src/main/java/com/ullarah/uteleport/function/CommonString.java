package com.ullarah.uteleport.function;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
    private String pluginPrefix() {

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
     * A no console usage message
     *
     * @param sender the sender who receives the message
     */
    public void messageNoConsole(CommandSender sender) {

        sender.sendMessage(pluginPrefix() + ChatColor.RED + "No console usage.");

    }

    /**
     * A message that is sent to a {@code Player} object
     *
     * @param player  the sender who receives the message
     * @param prefix  the prefix of the current plugin
     * @param message the message to send
     */
    public void messageSend(Player player, boolean prefix, String message) {

        player.sendMessage(prefix ? pluginPrefix() + message : message);

    }

}
