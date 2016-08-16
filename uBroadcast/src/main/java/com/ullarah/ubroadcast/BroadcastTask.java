package com.ullarah.ubroadcast;

import org.bukkit.plugin.Plugin;

import java.net.DatagramPacket;
import java.net.InetAddress;

class BroadcastTask {

    private final Plugin plugin;
    private final BroadcastFunctions broadcastFunctions;

    BroadcastTask(Plugin instance, BroadcastFunctions functions) {
        plugin = instance;
        broadcastFunctions = functions;
        startBroadcast();
    }

    private Plugin getPlugin() {
        return plugin;
    }

    private BroadcastFunctions getBroadcastFunctions() {
        return broadcastFunctions;
    }

    private void startBroadcast() {

        getPlugin().getServer().getScheduler().runTaskAsynchronously(getPlugin(), () -> {

            if (getBroadcastFunctions().getRunning()) {

                try {

                    String[] UDPPacket = ("224.0.2.60:4445").split(":");
                    byte[] serverAddress = getBroadcastFunctions().getAddress();

                    getBroadcastFunctions().broadcastServer(getBroadcastFunctions().getServerSocket(),
                            new DatagramPacket(serverAddress, serverAddress.length,
                                    InetAddress.getByName(UDPPacket[0]), Integer.parseInt(UDPPacket[1])));

                } catch (Exception e) {

                    e.printStackTrace();

                }

                getBroadcastFunctions().getServerSocket().close();

            }

        });

    }

}
