package com.ullarah.upostal.function;

import net.minecraft.server.v1_9_R2.IChatBaseComponent;
import net.minecraft.server.v1_9_R2.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import static net.minecraft.server.v1_9_R2.IChatBaseComponent.ChatSerializer;
import static net.minecraft.server.v1_9_R2.PacketPlayOutTitle.EnumTitleAction;

public class TitleSubtitle {

    /**
     * Displays a title in front of players client
     *  @param player   the player object
     * @param subtitle the text of the subtitle
     */
    public void subtitle(Player player, String subtitle) {

        CraftPlayer craftPlayer = (CraftPlayer) player;

        IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"" + "" + "\"}");
        IChatBaseComponent chatSubTitle = ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");

        PacketPlayOutTitle packetTitle = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
        PacketPlayOutTitle packetSubTitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, chatSubTitle);
        PacketPlayOutTitle packetLength = new PacketPlayOutTitle(20, 100, 20);

        craftPlayer.getHandle().playerConnection.sendPacket(packetTitle);
        craftPlayer.getHandle().playerConnection.sendPacket(packetSubTitle);
        craftPlayer.getHandle().playerConnection.sendPacket(packetLength);

    }

}
