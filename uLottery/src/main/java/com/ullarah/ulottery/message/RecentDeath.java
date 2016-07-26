package com.ullarah.ulottery.message;

import org.bukkit.ChatColor;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class RecentDeath {

    private final ConcurrentHashMap<UUID, Integer> map;
    private String name;
    private String reason;

    public RecentDeath() {
        this.map = new ConcurrentHashMap<>();
        this.name = "";
        this.reason = "Unknown";
    }

    public String getMessage() {
        return this.message();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String s) {
        this.name = s;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String s) {
        this.reason = s;
    }

    public ConcurrentHashMap<UUID, Integer> getMap() {
        return this.map;
    }

    private String message() {

        String heading = ChatColor.YELLOW + "Recent Death: " + ChatColor.RED;

        return name.length() == 0
                ? heading + "Nobody"
                : heading;

    }

}
