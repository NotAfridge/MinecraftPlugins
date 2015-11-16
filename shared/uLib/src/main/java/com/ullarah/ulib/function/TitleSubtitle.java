package com.ullarah.ulib.function;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import static net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import static net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;

public class TitleSubtitle {

    /**
     * Displays a title in front of players client
     *
     * @param player  the player object
     * @param seconds the amount of seconds to display
     * @param title   the text of the title
     */
    public void title(Player player, int seconds, String title) {

        both(player, seconds, title, "");

    }

    /**
     * Displays a title in front of players client
     *
     * @param player   the player object
     * @param seconds  the amount of seconds to display
     * @param subtitle the text of the subtitle
     */
    public void subtitle(Player player, int seconds, String subtitle) {

        both(player, seconds, "", subtitle);

    }

    /**
     * Displays a title in front of players client
     *
     * @param player   the player object
     * @param seconds  the amount of seconds to display
     * @param title    the text of the title
     * @param subtitle the text of the subtitle
     */
    public void both(Player player, int seconds, String title, String subtitle) {

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
