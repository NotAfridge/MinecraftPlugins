package com.ullarah.ulottery.message;

import org.bukkit.ChatColor;

public class Duration {

    private Integer count;

    public Duration() {
        setCount(0);
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

    private String message() {

        if (getCount() > 0) {

            String heading = ChatColor.YELLOW + "  Duration: " + ChatColor.RED;

            String hour = "Hour";
            String minute = "Minute";

            int durationHour = getCount() / 60;
            int durationMinute = getCount() % 60;

            if (durationHour > 1) hour += "s";
            if (durationMinute > 1) minute += "s";

            return durationHour < 1
                    ? heading + getCount() + " " + minute
                    : heading + String.format("%d " + hour + " %02d " + minute, durationHour, durationMinute);

        } else return "";

    }

    public void reset() {

        setCount(0);

    }

}
