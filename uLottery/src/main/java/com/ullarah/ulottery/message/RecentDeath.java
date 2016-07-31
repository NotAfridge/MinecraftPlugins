package com.ullarah.ulottery.message;

import org.bukkit.ChatColor;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class RecentDeath {

    private ConcurrentHashMap<UUID, Integer> map;
    private String name;
    private String reason;

    public RecentDeath() {
        setMap(new ConcurrentHashMap<>());
        setName("");
        setReason("Unknown");
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

    private void setMap(ConcurrentHashMap<UUID, Integer> h) {
        this.map = h;
    }

    private String message() {

        String heading = ChatColor.YELLOW + "Recent Death: " + ChatColor.RED;

        return getName().length() == 0
                ? heading + "Nobody"
                : heading;

    }

    public void reset() {

        setName("");
        setReason("");
        getMap().clear();

    }

}
