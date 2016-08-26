package com.ullarah.ulottery.message;

import com.ullarah.ulottery.LotteryInit;
import org.bukkit.ChatColor;

public class Block {

    private Integer amount;
    private Integer total;
    private Integer limit;

    public Block() {
        setAmount(0);
        setTotal(0);
        setLimit(1000);
    }

    public String getMessage() {
        return message();
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(int i) {
        amount = i;
        LotteryInit.getPlugin().getConfig().set("current.blocks", getAmount());
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

    public void reset() {

        setAmount(0);
        setTotal(0);

    }

}
