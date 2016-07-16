package com.ullarah.utab;

import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_10_R1.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

class TabFunctions {

    private static Field tabField;

    void sendHeaderFooter(Player player, String header, String footer) {

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

    String getCurrentHeader() {

        try {
            return ChatColor.translateAlternateColorCodes('&',
                    (String) TabInit.getHeaderMessages().get(TabInit.headerMessageCurrent));
        } catch (Exception e) {
            return "";
        }

    }

    String getCurrentFooter() {

        try {
            return ChatColor.translateAlternateColorCodes('&',
                    (String) TabInit.getFooterMessages().get(TabInit.footerMessageCurrent));
        } catch (Exception e) {
            return "";
        }

    }

    boolean reloadTabConfig() {

        try {

            for (Player player : Bukkit.getOnlinePlayers()) sendHeaderFooter(player, "", "");

            TabInit.headerMessageCurrent = 0;
            TabInit.footerMessageCurrent = 0;

            TabInit.getPlugin().reloadConfig();

            TabInit.setTabTimer(TabInit.getPlugin().getConfig().getInt("timer"));

            TabInit.setHeaderMessages(TabInit.getPlugin().getConfig().getStringList("header.messages"));
            TabInit.setFooterMessages(TabInit.getPlugin().getConfig().getStringList("footer.messages"));

            TabInit.headerMessageTotal = TabInit.getHeaderMessages().size() - 1;
            TabInit.footerMessageTotal = TabInit.getFooterMessages().size() - 1;

        } catch (Exception e) {

            e.printStackTrace();
            return false;

        }

        return true;

    }

}
