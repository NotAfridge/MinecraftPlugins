package com.ullarah.urocket.function;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

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

        IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
        IChatBaseComponent chatSubTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");

        PacketPlayOutTitle packetTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle);
        PacketPlayOutTitle packetSubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatSubTitle);
        PacketPlayOutTitle packetLength = new PacketPlayOutTitle(20, seconds * 20, 20);

        craftPlayer.getHandle().playerConnection.sendPacket(packetTitle);
        craftPlayer.getHandle().playerConnection.sendPacket(packetSubTitle);
        craftPlayer.getHandle().playerConnection.sendPacket(packetLength);

    }

}
