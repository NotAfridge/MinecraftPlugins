package com.ullarah.umagic.function;

import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionMessage {

    /**
     * Displays a message on the clients action bar
     *
     * @param player  the player object
     * @param message the text of the message
     */
    public void message(Player player, String message) {

        CraftPlayer craftPlayer = (CraftPlayer) player;

        IChatBaseComponent component = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");

        PacketPlayOutChat packetChat = new PacketPlayOutChat(component, (byte) 2);

        craftPlayer.getHandle().playerConnection.sendPacket(packetChat);

    }

}
