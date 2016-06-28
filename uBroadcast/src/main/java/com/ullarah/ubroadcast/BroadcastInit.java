package com.ullarah.ubroadcast;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.logging.Logger;

public class BroadcastInit extends JavaPlugin {

    private static DatagramSocket serverSocket;
    private static int serverPort;
    private static String serverMessage;
    private static String serverIP;
    private static Logger serverLogger;
    private static boolean running = true;
    private static Plugin plugin;
    private final Charset UTF8_CHARSET = Charset.forName("UTF-8");
    private int failCount = 0;

    private static Plugin getPlugin() {
        return plugin;
    }

    private static void setPlugin(Plugin plugin) {
        BroadcastInit.plugin = plugin;
    }

    private static void setBroadcast(DatagramSocket socket, int port, String message, String configuredIP, Logger logger) {

        BroadcastInit.serverSocket = socket;
        BroadcastInit.serverPort = port;
        BroadcastInit.serverMessage = message;
        BroadcastInit.serverIP = configuredIP;
        BroadcastInit.serverLogger = logger;

    }

    private static DatagramSocket createSocket() {

        DatagramSocket socket = null;

        try {

            socket = new DatagramSocket();
            socket.setSoTimeout(3000);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return socket;

    }

    private boolean getRunning() {
        return running;
    }

    private static void setRunning(boolean running) {
        BroadcastInit.running = running;
    }

    private void broadcastServer(final DatagramSocket socket, final DatagramPacket packet) {

        try {

            while (getRunning()) {

                try {

                    socket.send(packet);
                    failCount = 0;

                } catch (Throwable e) {

                    broadcastFail(e);

                }

                Thread.sleep(1500);

            }

        } catch (InterruptedException ignored) {
        }

    }

    private void broadcastFail(Throwable e) throws InterruptedException {

        if (failCount++ == 0) e.printStackTrace();

        if (failCount < 5)
            serverLogger.info(ChatColor.RED + "Failed to broadcast, will try again in 10 seconds...");
        else if (failCount == 5)
            serverLogger.info(ChatColor.YELLOW + "Broadcasting will not work until the network is fixed");

        Thread.sleep(8500);

    }

    private byte[] getAddress() {

        String serverAddress = getLanIP() + ':' + serverPort;
        String serverAdvert = serverAddress.replaceFirst(getLanIP() + ":", "");

        serverLogger.info("Broadcasting " + serverAddress + " over LAN");

        return ("[MOTD]" + serverMessage + "[/MOTD][AD]" + serverAdvert + "[/AD]").getBytes(UTF8_CHARSET);

    }

    private String getLanIP() {

        if (!serverIP.equals("")) return serverIP;

        try {

            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {

                NetworkInterface networkInterface = interfaces.nextElement();
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();

                while (addresses.hasMoreElements()) {

                    InetAddress address = addresses.nextElement();

                    if (address instanceof Inet4Address && !address.isLoopbackAddress())
                        return address.getHostAddress();

                }

            }

            throw new Exception("No usable IPv4 non-loopback address found");

        } catch (Exception e) {

            e.printStackTrace();
            serverLogger.severe("Could not automatically detect LAN IP, please set server-ip in server.properties");

            try {

                return InetAddress.getLocalHost().getHostAddress();

            } catch (UnknownHostException ex) {

                ex.printStackTrace();
                serverLogger.severe("No network interfaces found");
                return null;

            }

        }

    }

    @Override
    public void onEnable() {

        setPlugin(this);
        setRunning(true);

        setBroadcast(BroadcastInit.createSocket(), getServer().getPort(), getServer().getMotd(), getServer().getIp(), getLogger());

        getPlugin().getServer().getScheduler().runTaskAsynchronously(getPlugin(), () -> {

            try {

                String[] UDPPacket = ("224.0.2.60:4445").split(":");
                byte[] serverAddress = getAddress();

                broadcastServer(serverSocket, new DatagramPacket(serverAddress, serverAddress.length,
                        InetAddress.getByName(UDPPacket[0]), Integer.parseInt(UDPPacket[1])));

            } catch (Exception e) {

                e.printStackTrace();

            }

            serverSocket.close();

        });

    }

    @Override
    public void onDisable() {

        setRunning(false);

    }

}
