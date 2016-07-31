package com.ullarah.ulottery.message;

import com.ullarah.ulottery.LotteryInit;
import com.ullarah.ulottery.LotteryMessageInit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class Bank extends LotteryMessageInit {

    private Integer amount;
    private Material itemMaterial;

    public Bank() {
        setAmount(0);
        setItemMaterial(Material.EMERALD);
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

    public void setItemMaterial(String m) {
        itemMaterial = Material.getMaterial(m);
    }

    @SuppressWarnings("SameParameterValue")
    private void setItemMaterial(Material m) {
        itemMaterial = m;
    }

    private String message() {

        String heading = ChatColor.YELLOW + "  Lottery Bank Balance: " + ChatColor.GREEN;
        String item = " " + getItemMaterial().name().replace("_", " ").toLowerCase();

        if (amount > 0) {

            return LotteryInit.getEconomy() != null
                    ? heading + "$" + getAmount()
                    : heading + getAmount() + item + (getAmount() > 1 ? "s" : "");

        } else {

            return LotteryInit.getEconomy() != null
                    ? heading + "$" + "0"
                    : heading + "0" + item + "s";

        }

    }

    public void reset() {

        setAmount(0);

    }

}
