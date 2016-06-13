package com.ullarah.uchest.function;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Broadcast {

    /**
     * Broadcasts a message to all players,
     * with plugin name and optional log to console.
     *
     * @param plugin   the plugin object, to grab the name of the plugin
     * @param messages the messages, in a string array
     */
    public void sendMessage(Plugin plugin, String[] messages) {

        for (Player player : plugin.getServer().getOnlinePlayers())
            for (String message : messages)
                player.sendMessage(ChatColor.GOLD + "[" + plugin.getName() + "] " + ChatColor.WHITE + message);

    }

}
