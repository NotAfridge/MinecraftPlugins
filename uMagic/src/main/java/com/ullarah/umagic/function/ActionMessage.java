package com.ullarah.umagic.function;

import net.minecraft.server.v1_13_R2.ChatMessageType;
import net.minecraft.server.v1_13_R2.IChatBaseComponent;
import net.minecraft.server.v1_13_R2.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
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

        PacketPlayOutChat packetChat = new PacketPlayOutChat(component, ChatMessageType.GAME_INFO);

        craftPlayer.getHandle().playerConnection.sendPacket(packetChat);

    }

}
