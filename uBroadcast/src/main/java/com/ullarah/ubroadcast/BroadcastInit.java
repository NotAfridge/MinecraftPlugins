package com.ullarah.ubroadcast;

import org.bukkit.plugin.java.JavaPlugin;

public class BroadcastInit extends JavaPlugin {

    private BroadcastFunctions broadcastFunctions = new BroadcastFunctions();

    private BroadcastFunctions getBroadcastFunctions() {
        return broadcastFunctions;
    }

    @Override
    public void onEnable() {

        getBroadcastFunctions().setBroadcast(
                getBroadcastFunctions().createSocket(), getServer().getPort(),
                getServer().getMotd(), getServer().getIp(), getLogger());

        new BroadcastTask(this, getBroadcastFunctions());

        getBroadcastFunctions().setRunning(true);

    }

    @Override
    public void onDisable() {

        getBroadcastFunctions().setRunning(false);

    }

}
