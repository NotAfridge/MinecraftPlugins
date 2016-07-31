package com.ullarah.ulottery.message;

import com.ullarah.ulottery.LotteryInit;
import org.bukkit.ChatColor;

public class RecentWinner extends Bank {

    private String winName;
    private Integer winAmount;
    private Integer vaultAmount;
    private Integer itemAmount;

    public RecentWinner() {
        setWinName("");
        setWinAmount(0);
        setVaultAmount(5);
        setItemAmount(2);
    }

    public String getMessage() {
        return this.message();
    }

    public String getWinName() {
        return this.winName;
    }

    public void setWinName(String s) {
        this.winName = s;
    }

    public Integer getWinAmount() {
        return this.winAmount;
    }

    public void setWinAmount(Integer i) {
        this.winAmount = i;
    }

    public Integer getVaultAmount() {
        return this.vaultAmount;
    }

    public void setVaultAmount(Integer i) {
        this.vaultAmount = i;
    }

    public Integer getItemAmount() {
        return this.itemAmount;
    }

    public void setItemAmount(Integer i) {
        this.itemAmount = i;
    }

    private String message() {

        String sep = ChatColor.RESET + " - ";

        String heading = ChatColor.YELLOW + "  Recent Winner: " + ChatColor.RED + getWinName() + sep;
        String item = " " + getItemMaterial().name().replace("_", " ").toLowerCase();

        return LotteryInit.getEconomy() != null
                ? heading + ChatColor.GREEN + "$" + getWinAmount()
                : heading + ChatColor.GREEN + getWinAmount() + item + (getWinAmount() > 1 ? "s" : "");

    }

}
