package com.ullarah.upostal.function;

import net.minecraft.server.v1_14_R1.IChatBaseComponent;
import net.minecraft.server.v1_14_R1.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TitleSubtitle {

    /**
     * Displays a title in front of players client
     *  @param player   the player object
     * @param subtitle the text of the subtitle
     */
    public void subtitle(Player player, String subtitle) {

        CraftPlayer craftPlayer = (CraftPlayer) player;

        IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + "" + "\"}");
        IChatBaseComponent chatSubTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");

        PacketPlayOutTitle packetTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle);
        PacketPlayOutTitle packetSubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatSubTitle);
        PacketPlayOutTitle packetLength = new PacketPlayOutTitle(20, 100, 20);

        craftPlayer.getHandle().playerConnection.sendPacket(packetTitle);
        craftPlayer.getHandle().playerConnection.sendPacket(packetSubTitle);
        craftPlayer.getHandle().playerConnection.sendPacket(packetLength);

    }

}
