package com.ullarah.utab;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.Field;
import java.util.List;

import static net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;

public class Init extends JavaPlugin {

    private static Plugin plugin;
    private static BukkitTask tabTask;
    private static int headerMessageTotal = 0;
    private static int headerMessageCurrent = 0;
    private static int footerMessageTotal = 0;
    private static int footerMessageCurrent = 0;

    private static Plugin getPlugin() {
        return plugin;
    }

    private static void setPlugin(Plugin plugin) {
        Init.plugin = plugin;
    }

    private static void sendHeaderFooter(Player player, String header, String footer) {

        header = header.replaceAll("\\{player\\}", player.getPlayerListName());
        footer = footer.replaceAll("\\{player\\}", player.getPlayerListName());

        CraftPlayer craftPlayer = (CraftPlayer) player;
        PlayerConnection playerConnection = craftPlayer.getHandle().playerConnection;

        IChatBaseComponent headerComponent = ChatSerializer.a("{'text':'" + header + "'}");
        IChatBaseComponent footerComponent = ChatSerializer.a("{'text':'" + footer + "'}");

        try {

            PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

            Field headerField = packet.getClass().getDeclaredField("a");
            Field footerField = packet.getClass().getDeclaredField("b");

            headerField.setAccessible(true);
            footerField.setAccessible(true);

            headerField.set(packet, headerComponent);
            footerField.set(packet, footerComponent);

            playerConnection.sendPacket(packet);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public void onEnable() {

        setPlugin(this);

        getConfig().options().copyDefaults(true);
        saveConfig();

        changeHeaderFooter();

    }

    public void onDisable() {

        tabTask.cancel();
        for (Player player : Bukkit.getOnlinePlayers()) sendHeaderFooter(player, "", "");

    }

    private void changeHeaderFooter() {

        tabTask = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(),
                () -> Bukkit.getScheduler().runTask(getPlugin(), () -> {

                    List headerMessages = getPlugin().getConfig().getStringList("header.messages");
                    List footerMessages = getPlugin().getConfig().getStringList("footer.messages");

                    headerMessageTotal = headerMessages.size() - 1;
                    footerMessageTotal = footerMessages.size() - 1;

                    String headerMessage = ChatColor.translateAlternateColorCodes(
                            '&', (String) headerMessages.get(headerMessageCurrent));
                    String footerMessage = ChatColor.translateAlternateColorCodes(
                            '&', (String) footerMessages.get(footerMessageCurrent));

                    for (Player player : Bukkit.getOnlinePlayers())
                        sendHeaderFooter(player, headerMessage, footerMessage);

                    if (headerMessageCurrent < headerMessageTotal) headerMessageCurrent++;
                    else headerMessageCurrent = 0;

                    if (footerMessageCurrent < footerMessageTotal) footerMessageCurrent++;
                    else footerMessageCurrent = 0;

                }), 0, getPlugin().getConfig().getInt("timer") * 20);

    }


}
