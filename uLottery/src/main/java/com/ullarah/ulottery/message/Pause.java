package com.ullarah.ulottery.message;

import org.bukkit.ChatColor;

public class Pause {

    private Boolean paused;
    private Integer total;

    public Pause() {
        setPaused(false);
        setTotal(0);
    }

    public String getMessage() {
        return this.message();
    }

    public Boolean isPaused() {
        return this.paused;
    }

    public void setPaused(boolean b) {
        this.paused = b;
    }

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(int i) {
        this.total = i;
    }

    private String message() {

        return "Less than " + ChatColor.YELLOW + getTotal() + ChatColor.RESET + " players. Lottery is paused.";

    }

}
