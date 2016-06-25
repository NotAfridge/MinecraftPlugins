package com.ullarah.utab;

import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_10_R1.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

import static com.ullarah.utab.TabInit.*;

class TabFunctions {

    private static Field tabField;

    public void sendHeaderFooter(Player player, String header, String footer) {

        header = header.replaceAll("\\{player\\}", player.getPlayerListName());
        footer = footer.replaceAll("\\{player\\}", player.getPlayerListName());

        CraftPlayer craftPlayer = (CraftPlayer) player;
        PlayerConnection playerConnection = craftPlayer.getHandle().playerConnection;

        try {

            IChatBaseComponent headerMessage = IChatBaseComponent.ChatSerializer.a("{\"text\":\" " + header + " \"}");
            IChatBaseComponent footerMessage = IChatBaseComponent.ChatSerializer.a("{\"text\":\" " + footer + " \"}");

            PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(headerMessage);

            if (tabField == null) {
                tabField = packet.getClass().getDeclaredField("b");
                tabField.setAccessible(true);
            }

            tabField.set(packet, footerMessage);
            playerConnection.sendPacket(packet);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public String getCurrentHeader() {

        try {
            return ChatColor.translateAlternateColorCodes('&', (String) getHeaderMessages().get(headerMessageCurrent));
        } catch (Exception e) {
            return "";
        }

    }

    public String getCurrentFooter() {

        try {
            return ChatColor.translateAlternateColorCodes('&', (String) getFooterMessages().get(footerMessageCurrent));
        } catch (Exception e) {
            return "";
        }

    }

    public boolean reloadTabConfig() {

        try {

            for (Player player : Bukkit.getOnlinePlayers()) sendHeaderFooter(player, "", "");

            headerMessageCurrent = 0;
            footerMessageCurrent = 0;

            getPlugin().reloadConfig();

            setTabTimer(getPlugin().getConfig().getInt("timer"));

            setHeaderMessages(getPlugin().getConfig().getStringList("header.messages"));
            setFooterMessages(getPlugin().getConfig().getStringList("footer.messages"));

            headerMessageTotal = getHeaderMessages().size() - 1;
            footerMessageTotal = getFooterMessages().size() - 1;

        } catch (Exception e) {

            e.printStackTrace();
            return false;

        }

        return true;

    }

}
