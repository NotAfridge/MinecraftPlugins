package com.ullarah.ulottery.message;

import org.bukkit.ChatColor;

public class Pause {

    private Boolean paused = false;
    private Integer total = 0;

    public Pause() {
        this.paused = false;
        this.total = 0;
    }

    public String getMessage() {
        return this.message();
    }

    public Boolean getPaused() {
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

        return "Less than " + ChatColor.YELLOW + total + ChatColor.RESET + " players. Lottery is paused.";

    }

}
