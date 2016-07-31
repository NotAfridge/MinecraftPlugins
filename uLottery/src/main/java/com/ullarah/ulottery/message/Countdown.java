package com.ullarah.ulottery.message;

import org.bukkit.ChatColor;

public class Countdown {

    private Integer count;
    private Integer original;

    public Countdown() {
        setCount(60);
        setOriginal(getCount());
    }

    public String getMessage() {
        return this.message();
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(int i) {
        this.count = i;
    }

    public Integer getOriginal() {
        return this.original;
    }

    public void setOriginal(int i) {
        this.original = i;
    }

    private String message() {
        return ChatColor.YELLOW + "Countdown: " + ChatColor.RED + getCount()
                + (getCount() > 1 ? " Minutes" : " Minute");
    }

    public void reset() {

        setCount(getOriginal());

    }

}
