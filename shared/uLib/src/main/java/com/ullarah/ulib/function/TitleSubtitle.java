package com.ullarah.ulib.function;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import static net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import static net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;

public class TitleSubtitle {

    public static void title(Player player, int seconds, String title) {

        CraftPlayer craftPlayer = (CraftPlayer) player;

        IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"" + title + "\"}");

        PacketPlayOutTitle packetTitle = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
        PacketPlayOutTitle packetLength = new PacketPlayOutTitle(20, seconds * 20, 20);

        craftPlayer.getHandle().playerConnection.sendPacket(packetTitle);
        craftPlayer.getHandle().playerConnection.sendPacket(packetLength);

    }

    public static void subtitle(Player player, int seconds, String subtitle) {

        CraftPlayer craftPlayer = (CraftPlayer) player;

        IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \" \"}");
        IChatBaseComponent chatSubTitle = ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");

        PacketPlayOutTitle packetTitle = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
        PacketPlayOutTitle packetSubTitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, chatSubTitle);
        PacketPlayOutTitle packetLength = new PacketPlayOutTitle(20, seconds * 20, 20);

        craftPlayer.getHandle().playerConnection.sendPacket(packetTitle);
        craftPlayer.getHandle().playerConnection.sendPacket(packetSubTitle);
        craftPlayer.getHandle().playerConnection.sendPacket(packetLength);

    }

    public static void both(Player player, int seconds, String title, String subtitle) {

        CraftPlayer craftPlayer = (CraftPlayer) player;

        IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"" + title + "\"}");
        IChatBaseComponent chatSubTitle = ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");

        PacketPlayOutTitle packetTitle = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
        PacketPlayOutTitle packetSubTitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, chatSubTitle);
        PacketPlayOutTitle packetLength = new PacketPlayOutTitle(20, seconds * 20, 20);

        craftPlayer.getHandle().playerConnection.sendPacket(packetTitle);
        craftPlayer.getHandle().playerConnection.sendPacket(packetSubTitle);
        craftPlayer.getHandle().playerConnection.sendPacket(packetLength);

    }

}
