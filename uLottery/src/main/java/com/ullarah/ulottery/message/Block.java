package com.ullarah.ulottery.message;

import org.bukkit.ChatColor;

public class Block {

    private Integer amount;
    private Integer total;
    private Integer limit;

    public Block() {
        this.amount = 0;
        this.total = 0;
        this.limit = 1000;
    }

    public String getMessage() {
        return message();
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(int i) {
        amount = i;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(int i) {
        total = i;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(int i) {
        limit = i;
    }

    private String message() {
        return ChatColor.YELLOW + "  Blocks Broken: ";
    }

}
