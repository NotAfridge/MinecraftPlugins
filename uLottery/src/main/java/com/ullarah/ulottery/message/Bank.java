package com.ullarah.ulottery.message;

import com.ullarah.ulottery.LotteryInit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class Bank {

    private Integer amount;
    private Material itemMaterial;

    public Bank() {
        this.amount = 0;
        this.itemMaterial = Material.EMERALD;
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

    public Material getItemMaterial() {
        return itemMaterial;
    }

    public void setItemMaterial(Material m) {
        itemMaterial = m;
    }

    private String message() {

        String heading = ChatColor.YELLOW + "  Bank: " + ChatColor.GREEN;

        if (amount > 0) {

            String item = " " + getItemMaterial().name().replace("_", " ").toLowerCase();

            return LotteryInit.economy != null
                    ? heading + "$" + amount
                    : heading + amount + item;

        } else return heading + ChatColor.AQUA + "Nothing Yet...";

    }

}
