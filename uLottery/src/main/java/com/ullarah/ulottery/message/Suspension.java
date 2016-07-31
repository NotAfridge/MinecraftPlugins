package com.ullarah.ulottery.message;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Suspension {

    private ConcurrentHashMap<UUID, Integer> map;
    private Integer time;

    public Suspension() {
        setMap(new ConcurrentHashMap<>());
        setTime(30);
    }

    public String getMessage(Player player) {
        return this.message(player);
    }

    public Integer getTime() {
        return this.time;
    }

    public void setTime(Integer i) {
        this.time = i;
    }

    public ConcurrentHashMap<UUID, Integer> getMap() {
        return this.map;
    }

    private void setMap(ConcurrentHashMap<UUID, Integer> h) {
        this.map = h;
    }

    private String message(Player player) {

        if (getMap().containsKey(player.getUniqueId())) {

            Integer playerTimeout = getMap().get(player.getUniqueId());

            String time = (playerTimeout > 1 ? " Minutes" : " Minute");

            return ChatColor.YELLOW + "  Suspension: " + ChatColor.RED + playerTimeout + time;

        } else return "";

    }

    public void reset() {

        getMap().clear();

    }

}
