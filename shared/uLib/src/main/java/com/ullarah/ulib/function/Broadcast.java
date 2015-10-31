package com.ullarah.ulib.function;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Broadcast {

    /**
     * Broadcasts a message to all players,
     * with plugin name and optional log to console.
     *
     * @param plugin   the plugin object, to grab the name of the plugin
     * @param console  the option to log the message(s) to console
     * @param messages the messages, in a string array
     */
    public static void sendMessage(Plugin plugin, boolean console, String[] messages) {

        for (Player player : Bukkit.getOnlinePlayers())
            for (String message : messages)
                player.sendMessage(ChatColor.GOLD + "[" + plugin.getName() + "] " + ChatColor.WHITE + message);

    }

    /**
     * Broadcasts a message to specific players,
     * with plugin name and optional log to console.
     *
     * @param plugin   the plugin object, to grab the name of the plugin
     * @param console  the option to log the message(s) to console
     * @param messages the messages, in a string array
     * @param players  the players who will receive the message
     */
    public static void sendMessage(Plugin plugin, boolean console, String[] messages, Player... players) {

        for (Player player : players)
            for (String message : messages)
                player.sendMessage(ChatColor.GOLD + "[" + plugin.getName() + "] " + ChatColor.WHITE + message);

    }

    /**
     * Broadcasts a message to players with specific permissions,
     * with plugin name and optional log to console.
     *
     * @param plugin      the plugin object, to grab the name of the plugin
     * @param console     the option to log the message(s) to console
     * @param messages    the messages, in a string array
     * @param permissions the players who have this given permission
     */
    public static void sendMessage(Plugin plugin, boolean console, String[] messages, String... permissions) {

        Bukkit.getOnlinePlayers().stream().filter(player -> PermissionCheck.check(player, permissions)).forEach(player -> {
            for (String message : messages)
                player.sendMessage(ChatColor.GOLD + "[" + plugin.getName() + "] " + ChatColor.WHITE + message);
        });

    }

}
