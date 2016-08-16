package com.ullarah.ubroadcast;

import org.bukkit.ChatColor;

import java.net.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.logging.Logger;

class BroadcastFunctions {

    private final Charset UTF8_CHARSET = Charset.forName("UTF-8");
    private DatagramSocket serverSocket;
    private int serverPort, failCount = 0;
    private String serverMessage, serverIP;
    private Logger serverLogger;
    private boolean running = true;

    DatagramSocket getServerSocket() {
        return serverSocket;
    }

    void setBroadcast(DatagramSocket socket, int port, String message, String configuredIP, Logger logger) {

        serverSocket = socket;
        serverPort = port;
        serverMessage = message;
        serverIP = configuredIP;
        serverLogger = logger;

    }

    DatagramSocket createSocket() {

        DatagramSocket socket = null;

        try {

            socket = new DatagramSocket();
            socket.setSoTimeout(3000);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return socket;

    }

    boolean getRunning() {
        return running;
    }

    void setRunning(boolean run) {
        running = run;
    }

    void broadcastServer(final DatagramSocket socket, final DatagramPacket packet) {

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

    byte[] getAddress() {

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

}
